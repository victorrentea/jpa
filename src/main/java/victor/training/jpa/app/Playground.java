package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.CourseActivity;
import victor.training.jpa.app.domain.entity.Subject;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.TeacherDetails;
import victor.training.jpa.app.facade.dto.ActivitySearchCriteria;
import victor.training.jpa.app.repo.SubjectRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class Playground {
    private final TeacherRepo teacherRepo;
    private final SubjectRepo subjectRepo;

    @Transactional
    public void firstTransaction() {
        log.debug("Halo!");
        Teacher teacher = new Teacher("Mishel");
        teacherRepo.save(teacher);
//        teacherRepo.searchActivity(new ActivitySearchCriteria());

//        teacherRepo.save(teacher);
        System.out.println("Dupa save teacher are id= " + teacher.getId());
        TeacherDetails details = new TeacherDetails();
        teacher.setDetails(details);

        Subject subject = new Subject("Cercetari Operationale");
        subject.setHolderTeacher(teacher);
        subject.setName("Masuratori Stiintifice");
        if (true) {
            throw new IllegalArgumentException("intentionat");
        }
        subjectRepo.save(subject);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
        log.debug("Halo2!");
        Teacher teacher = teacherRepo.findById(1L).get();

        System.out.println(teacher.getName());
        System.out.println(teacher.getHeldSubjects());
//        System.out.println(teacher.getDetails().getCv());

        teacherRepo.detach(teacher);

        teacher.setName("Alter Ego"); // in mod normal cauzeaza un UPDATE la finalul tranzactiei :
        // in cazul de fata: la finalul metodei
    }
}


