package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  @Transactional
  public void play() throws Exception {
    Teacher teacher = new Teacher()
        .setName("John Doe")
        .setDetails(new TeacherDetails()
            .setCv("BETON"));
    log.info("Before save: {}", teacher.getId());
    teacherRepo.save(teacher); // (A)

    System.out.println("E? " + teacherRepo.findById(teacher.getId()));
    log.info("After save: {}", teacher.getId());
  }
  // (B)

  // 2 x INSERT intr-o tx❤️ pleaca in DB la .save (A) = asteptat

  // 2 x INSERT intr-o tx❤️ pleaca in DB dupa iesirea din metoda @Transactional(B) 😱😱
  // daca metoda e @Transactional = WRITE-BEHIND
  // 😊 performance pt ca inserturi catre aceeasi entitate pot fi batch-euite impreuna sa TCP/IP mai putin
  // 🙁
}
