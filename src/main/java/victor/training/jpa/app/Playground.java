package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.*;
import victor.training.jpa.app.facade.dto.ActivitySearchCriteria;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.SubjectRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class Playground implements IPlayground {
    private final TeacherRepo teacherRepo;
    private final SubjectRepo subjectRepo;
    private final ErrorLogRepo errorLogRepo;

    @Override
    @Transactional(/*propagation = Propagation.REQUIRES_NEW,*/ readOnly = true)
    public void secondTransaction() {
        log.debug("Am gasit {} erori", errorLogRepo.count());
        log.debug("Halo2!");
        Teacher teacher = teacherRepo.findById(1L).get();
        x.altaMetoda(teacher);


        System.out.println(teacher.getName());
        System.out.println(teacher.getHeldSubjects());
//        System.out.println(teacher.getDetails().getCv());

        teacherRepo.detach(teacher);

        teacher.setName("Alter Ego"); // in mod normal cauzeaza un UPDATE la finalul tranzactiei :
        // in cazul de fata: la finalul metodei

    }

    @Override
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

        try {
            x.salveazaMaterie(teacher);
        } catch (Exception e) {
            log.error("Eroarea: " + e, e); //shaworma
            System.out.println(x.getClass());
            x.persistError(e.getMessage());
        }
    }


    @Autowired
    X x;

}
@RequiredArgsConstructor
@Component
@Slf4j
@Transactional
class X {
    private final TeacherRepo teacherRepo;
    private final SubjectRepo subjectRepo;
    private final ErrorLogRepo errorLogRepo;
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persistError(String error) {
        errorLogRepo.save(new ErrorLog(error));
    }
    public void altaMetoda(Teacher teacher) {
        log.debug("Si-nco-data mai flacai");
        Teacher teacher2 = teacherRepo.findById(1L).get();
        log.debug("Dolly " + (teacher == teacher2));
    }

    public /*final*/ void salveazaMaterie(Teacher teacher) throws Exception {
        // Checked exceptions cand trec prin proxy nu omoara Tranzactia
        Subject subject = new Subject("Cercetari Operationale");
        subject.setHolderTeacher(teacher);
        subject.setName("Masuratori Stiintifice");
        subjectRepo.save(subject);
        if (true) {
            throw new Exception("intentionat");
        }
    }

}


