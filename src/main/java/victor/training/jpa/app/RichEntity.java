package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.Subject;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.repo.SubjectRepo;
import victor.training.jpa.app.repo.TeacherRepo;

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
    Teacher teacher = teacherRepo.save(new Teacher().setName("A")); // inserted
    Subject subject = subjectRepo.save(new Subject().setName("S"));

    teacher.getHeldSubjects().add(subject);
//    subject.setHolderTeacher(teacher); // [THE OWNER side] only one of the ends is saved to DB

    log.info("END");
  }
}
