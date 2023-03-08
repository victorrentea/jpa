package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
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
    Teacher teacher = new Teacher().setName("Meeee")
            .approve("jdoe"); // Domain Driven ubiquitout language < human words
    Subject subject = new Subject().setName("S");

//    rabbitOrKafka_Send(teacher);
    // incorrect data sent to remote system in a non-transactable way

//    subject.setHolderTeacher(teacher);

    teacher.addSubject(subject); // does not get persisted. but if you leave it out, later in a complex flow

    eventPublisher.publishEvent(new TeacherCreatedEvent(teacher));
//     your (JAVA OBJECTS) model remains INVALID
//    subject.setHolderTeacher(teacher); // [THE OWNER side] only one of the ends is saved to DB

    teacherRepo.save(teacher); // inserted
    subjectRepo.save(subject);
    log.info("END");
  }

  private final ApplicationEventPublisher eventPublisher;
  @Value
  public static class TeacherCreatedEvent {
    Teacher teacher;
  }

//  @EventListener
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void rabbitOrKafka_Send(TeacherCreatedEvent event) {
    System.out.println("SEND " + event.teacher);
  }
}
