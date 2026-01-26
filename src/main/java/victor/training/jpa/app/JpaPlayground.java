package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.entity.TeacherDetails;
import victor.training.jpa.app.repo.TeacherRepo;

@Component
@RequiredArgsConstructor
@Slf4j
public class JpaPlayground {
  private final TeacherRepo teacherRepo;
  private final JdbcTemplate jdbcTemplate;

  @Transactional // inutil daca tot ce modific e un singur repo.save
  public void play() throws Exception {
    Teacher teacher = new Teacher()
        .setName("John Doe")
        .setDetails(new TeacherDetails()
            .setCv("BETON"));
    teacherRepo.save(teacher); // (A)

    altaMetoda10CalluriMaiJos();

    // websocket.push/rabbit/kafka notification ca am sters racheta

    // nu face select ci-ti da din 1st level cacheul hibernate (atasat tranzactiei)
    System.out.println("E? " + teacherRepo.findById(teacher.getId())); // 0 SELECT, ca-mi da din 1st level cache

    // dar daca caut nu cu .findById
//    System.out.println(teacherRepo.findByName("John Doe")); // ✅ face auto-flush inainte
//    System.out.println(teacherRepo.findByNameNativ("John Doe")); // ✅ tot prin Hibernate treci -> isi da flush automat
  // jdbcTemplate nu va cauza un auto-flush❌
    // daca chemi vreo procedura/functie care da select❌
//    pt ❌ =>
//    teacherRepo.flush(); // manual

    log.info("After save: {}", teacher.getId());
  }

  private void altaMetoda10CalluriMaiJos() {
    teacherRepo.save(new Teacher("John Doe"));
  }
  // (B) inainte de un commit, se face automat .flush

  // 2 x INSERT intr-o tx❤️ pleaca in DB la .save (A) = asteptat

  // 2 x INSERT intr-o tx❤️ pleaca in DB dupa iesirea din metoda @Transactional(B) 😱😱
  // daca metoda e @Transactional = WRITE-BEHIND
  // 😊 performance pt ca inserturi catre aceeasi entitate pot fi batch-euite impreuna sa TCP/IP mai putin
  // 🙁
}
