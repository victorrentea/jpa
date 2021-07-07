package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.*;
import victor.training.jpa.app.domain.entity.Teacher.Grade;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.SubjectRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;

@Slf4j
@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class TransactionPlayground {
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final SubjectRepo subjectRepo;
    private final TeacherRepo teacherRepo;
    private final Other other;

    private final ErrorLogRepo errorLogRepo;

    @Transactional // proxyul de aici vede ca vii la fct fara tx din caller, creeaza tx, o salveaza pe ThreadLocal
    public void firstTransaction() {
        log.debug("Halo!");

         errorLogRepo.save(new ErrorLog("Salut!")); // proxy-ul din fata lui save @Transactional. vede pe threadlocal tx si o ia
         errorLogRepo.save(new ErrorLog("Pa!"));
         jdbc.update("INSERT INTO ERROR_LOG(id, message) VALUES ( -1, 'din jdbc curat' )");
//         errorLogRepo.flush(); // 1
//         System.out.println(errorLogRepo.findMy()); 2 JPQL
//        System.out.println(errorLogRepo.findAll()); // 3. api de SpringDATA
//        System.out.println(errorLogRepo.cheamaProcedura()); 4. // query nativ
        log.debug("Se termina metoda");

        errorLogRepo.flush();
        errorLogRepo.callProcedureViaJpa();
        log.debug("Dupa Procedura");
//        System.out.println(errorLogRepo.callFunc());

        Subject chimie = new Subject("Chimie")
            .addActivities(new LabActivity(), new LabActivity());

        Teacher teacher = new Teacher()
            .setName("Einstein")
            .setGrade(Grade.PROFESSOR)
            .addSubject(chimie);

        teacherRepo.save(teacher);
    }

    @Transactional
    public void secondTransaction() { // modifica
        log.debug("Halo2!");


//        Connection c;
//        ResultSet rs = c.createStatement().executeQuery("SELECT COALESCE(e.MESSAGE, ',') FROM ERROR_LOG e");
//        if (!rs.next()) throw new IllegalArgumentException("No rows");
//        return rs.getInt(1);

        ErrorLog errorLog1 = errorLogRepo.findById(1L).get();
        ErrorLog errorLog2 = other.met();
        System.out.println(errorLog1 == errorLog2);
        errorLogRepo.save(errorLog1);

//        Subject subject = subjectRepo.findById(4L).get();

        Subject subject = subjectRepo.findWithActivities(4L);


        log.debug("Aici am luat subject din DB cu o lista: ");
        for (TeachingActivity activity : subject.getActivities()) {
            System.out.println("Activity : " + activity);
        }


    }
}

@RequiredArgsConstructor
@Component
class Other {
    private final ErrorLogRepo errorLogRepo;
    public ErrorLog met() {
        System.out.println("Alta clasa");
        ErrorLog errorLog = errorLogRepo.findById(1L).get();
        errorLog.setMessage("alt mesaj");
        errorLogRepo.flush();
        System.out.println(errorLogRepo.findAll());
        return errorLog;
    }
}