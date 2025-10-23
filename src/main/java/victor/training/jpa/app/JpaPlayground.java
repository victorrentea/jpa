package victor.training.jpa.app;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JpaPlayground {
   private final EntityManager em;

   @Transactional // in loc de @TransactionAttribute
   public void play() throws Exception {
     Teacher teacher = new Teacher()
         .setName("Newton")
         .setDetails(new TeacherDetails()
             .setCv("pimped"));
     Subject subject = new Subject();
     teacher.addHeldSubject(subject);
     System.out.println("Inainte:"+teacher.getId());
     em.persist(teacher);
     System.out.println("Dupa:"+teacher.getId()); // cf legii dupa persist TRE SA AI ID SETAT
     em.persist(subject);
   }

//     em.getTransaction().begin(); // ANATHEMA!!
//     em.getTransaction().commit();

  @Transactional // === @TransactionAttribute(REQUIRED)
  public void writeBehind() {
    em.persist(new StudentsYear("CA"/*.repeat(255)*/) // va cauza EROARE LA INSERT
        .add(new StudentsGroup("CA321")));
//    em.flush(); // 🚽 explit ⚠NU COMITE!
//    em.createQuery("FROM Teacher ", Teacher.class).getResultList(); // nu face flus
//    em.createQuery("FROM StudentsYear ", StudentsYear.class).getResultList(); // nu face flus
    //rabbit.send(mesaj);// cum s-a ars Victor ⚔️. Fix: vibeaz-o p'asta: public void onTeacherSaved(@Observes(during = TransactionPhase.AFTER_SUCCESS) TeacherCreatedEvent event) {
    log.info("--- End of method");
  }

  // "Write Behind": JPA AMANA SCRIERILE ("flush") pana inainte de COMMIT
  // INSERT/UPDATE/DELETE->DB: SIZE, NOT NULL, FK ~ BufferedWriter:
  //  Scop: sa nu trimit UPDATE la orice entity.setField(
  //  Scop: pt a le BATCH insert/update-a dupa eg 500😍
  //  Scop: ca poate nu-i nevoie, ca crapa tranzactia ROLLBACK
  // COMMIT->DB: UK


  @Transactional
  public void autoSave() {
    StudentsYear entity = em.find(StudentsYear.class,1L); // hib a facut o copie
    entity.setCode("TWO");
    entity.setCode("CA2");

    log.info("---- "); // lazy loading face Un SELECT dupa linie ⚠️MERGE DOAR IN TX
    StudentsGroup group = entity.getGroups().get(0);
    //em.clear(); // elimina din context toate entitatile -> "detasate", ne-urmarite
    group.setCode("ZZ");// auto-saved

    StudentsYear entity2 = em.find(StudentsYear.class,1L);
    log.info("1st level cache (tx-scoped) merge: " + (entity == entity2));
  }
  // hib face o copie a starii persistenta la orice @ENtity iti da intr-o tranzactie
  // la final face equals

//    Session session = em.unwrap(Session.class);
  @Transactional
  public void updateFaraAutoSave() {
    StudentsYear entity = em.find(StudentsYear.class, 1L);
    entity.setCode("MANUAL");
//    em.merge(entity); // fara TX
  }
  // la ce mai trebuie .merge( daca oricum face autosave la tot ce ti-a dat??
  // => construiesti cu new @Entity din ceva venit de la client/bro'
}
