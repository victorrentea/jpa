package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.*;
import victor.training.jpa.app.domain.entity.ContactChannel.Type;
import victor.training.jpa.app.domain.entity.Teacher.Grade;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.SubjectRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
   private final EntityManager entityManager; // JPA
   private final ErrorLogRepo errorLogRepo;
   private final TeacherRepo teacherRepo; // Spring Data JPA
   private final SubjectRepo subjectRepo;

   @Transactional
   public void firstTransaction() {
      log.debug("Halo!");
      ErrorLog logError = errorLogRepo.save(new ErrorLog("Spring Data"));
      log.debug(logError.getId().toString());


      /// ------------
      Teacher teacher = new Teacher("Tavi").setGrade(Grade.PROFESSOR);
      ContactChannel contactChannel = new ContactChannel(Type.PERSONAL_PHONE, "10321938193");

      teacher.addChannel(contactChannel);
      teacherRepo.save(teacher);

      Subject subject = new Subject();
      subject.setY(new Y());
      teacher.addHeldSubject(subject);

      IntStream.range(1,100).mapToObj(i -> new Teacher().addHeldSubject(new Subject().setY(new Y()))).forEach(teacherRepo::save);

//      subjectRepo.save(subject);
   }


   @Transactional
   public void secondTransaction() {
      log.debug("Halo2!");
      ErrorLog errorLog = errorLogRepo.findById(1L).get();
      log.debug("Before the change");

      errorLog.setMessage("CHANGE");
      log.debug(errorLogRepo.findByMessageLike("%HANG%").toString());
      errorLog.setMessage("BACK");
      log.debug("Is the update sent ABOVE this line ?");
   }
   @Transactional
   public void thirdTransaction() {
      // -------
      // for this particular use case you know that you'll need both teachers and their subjects
      List<Teacher> teachers = teacherRepo.findAllForExport();
      log.debug("I got the teachers================");
      for (Teacher teacher : teachers) {
//         log.debug("the list of subjects : " + teacher.getHeldSubjects().getClass()); // PesistentSet and PersistentBag(List)
         log.debug("Teacher " + teacher.getName() +  " teaches "  + teacher.getHeldSubjects().size());
         for (Subject subject : teacher.getHeldSubjects()) {

            System.out.println("Y"+ subject.getY());
         }
      }

      // getting someting out with SELECT JPQL does not automaticlly fetc the @..>ToOne links
//      Teacher teacher = entityManager.createQuery("SELECT t FROM Teacher t WHERE t.id = :id", Teacher.class)
//          .setParameter("id", 2L)
//          .getSingleResult();



//      Teacher teacher = teacherRepo.getOne(2L);
      // returns a proxy to what you believe it's in the database. On first access, it really loads the data from DB.
      // but it's supposed NOT to be used to get attributes from it.
      // USEFUL when INSERTing Customer referencing a Country by ID : you don;t technically need to SELECT that country. But your Entity model says Customer.country:Country



//      Teacher teacher = teacherRepo.findById(2L).get();
//
//      System.out.println("What did I get, again ?" + teacher.getClass());
//
//      log.debug("Teacher " + teacher +  " teaches "  + teacher.getHeldSubjects().size());
   }

   @Transactional
   public void four() {
      System.out.println(teacherRepo.findByName("Tavi"));
   }

   @Transactional(readOnly = true)
   public void iterateOver10Mrows () {
      teacherRepo.findAllAsStream().forEach(System.out::println);
   }
}
