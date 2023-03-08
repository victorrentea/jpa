package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.Subject;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.entity.Teacher.Status;
import victor.training.jpa.app.repo.SubjectRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class RichEntity {
private final TeacherRepo teacherRepo;
private final SubjectRepo subjectRepo;
  @Transactional
  @EventListener(ApplicationStartedEvent.class)
  public void insertData() {
    log.info("START");
    Teacher teacher = new Teacher().setName("A")
            .approve("jdoe"); // Domain Driven ubiquitout language < human words
//            .setStatus(Status.APPROVED)
//            .setApprovalUser("jdoe")
//            .setApprovalDate(LocalDate.now());
    Subject subject = new Subject().setName("S");


//    subject.setHolderTeacher(teacher);

    teacher.addSubject(subject); // does not get persisted. but if you leave it out, later in a complex flow

//     your (JAVA OBJECTS) model remains INVALID
//    subject.setHolderTeacher(teacher); // [THE OWNER side] only one of the ends is saved to DB

    teacherRepo.save(teacher); // inserted
    subjectRepo.save(subject);
    log.info("END");
  }
}
