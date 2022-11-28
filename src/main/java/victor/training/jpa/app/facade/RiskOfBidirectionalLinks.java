package victor.training.jpa.app.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.Subject;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.repo.SubjectRepo;
import victor.training.jpa.app.repo.TeacherRepo;

public class RiskOfBidirectionalLinks {
  @Autowired
  private TeacherRepo teacherRepo;
  @Autowired
  private SubjectRepo subjectRepo;

  @Transactional
  public void method(Long teacherId, Long subjectId) {
    Teacher teacher = teacherRepo.findOneById(teacherId);
    Subject subject = subjectRepo.findOneById(subjectId);

    // I guard the consistency of the bidirectional JPA link.
    teacher.addSubject(subject);

//    subject.setHolderTeacher(teacher);
//    teacher.getHeldSubjects().add(subject);
  }
}
