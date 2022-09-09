package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.*;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.SubjectRepo;
import victor.training.jpa.app.repo.TeacherDetailsRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionJPAPlayground {
    private final JdbcTemplate jdbc;
    private final TeacherRepo teacherRepo;
    private final EntityManager em;
    private final ErrorLogRepo repo;
    private final DataSource ds; // o tempora, o mores....
    private Long subjectId;

    @Transactional
    public void firstTransaction() {
        log.debug("Function Begin");
        ErrorLog entity = new ErrorLog("-Bani din cont");
        repo.save(entity);
        System.out.println("Intotdeauna dupa SAVE id = " + entity.getId());
        System.out.println("chiar DACA NU VEZI INSERTUL DUCANDU_SE IN BAZA!! Huh!?!");

        repo.save(new ErrorLog("null"));

        // daca Hibernate trebuie sa ruleze vreun JPQL: (toate de mai jos sunt JPQL de fapt)
//        System.out.println(repo.findAll());
//        System.out.println(repo.findByMessage("null"));
//        System.out.println(repo.finderumeuCasSmecher("null"));
        // query native trecut prin hibernate ~ entittyManager.createNativeQuery
//        System.out.println("@Query native: " + repo.findByNativ("null"));

        // daca lovesc direct cu SQL pe SUB hibernate (nu pring Sprign Data): Hibernate nu face flush >> BUG!
//        System.out.println("n="+ jdbc.queryForObject("SELECT COUNT(*) FROM ERROR_LOG", Long.class));

        //        repo.save(new ErrorLog(null));


        Subject subject = new Subject();
        CourseActivity course = new CourseActivity();

//        subject.getActivities().add(course); // fix capatul care NU CONTEAZA la INSERT, invers(mappedBy=)
        subject.addActivity(course);

        subjectId = subjectRepo.save(subject).getId();

        log.debug("Function End");
    }

    private final SubjectRepo subjectRepo;

        //        jdbc.update("INSERT INTO TEACHER(ID) VALUES (HIBERNATE_SEQUENCE.nextval)");

    @Transactional
    public void secondTransaction() {
        ErrorLog errorLog = repo.findById(1L).orElseThrow();
        errorLog.setMessage("Unu nou!");

//        ErrorLog dinNou = repo.findById(1L).orElseThrow(); // nu merge in DB
        ErrorLog dinNou = repo.findByMessageLikeIgnoreCase("%nou%"); // merge in DB cu select? DA
        log.debug("OMG, acelasi obiect din heap" + (dinNou == errorLog));
        log.debug(errorLog.getMessage());

        Teacher tavi = new Teacher("Tavi");
        tavi.setDetails(new TeacherDetails().setCv("MIT"));
        teacherRepo.save(tavi);

        Subject subject = subjectRepo.findOneById(subjectId);
        Long activityId = subject.getActivities().get(0).getId();
        subject.removeActivity(activityId);


    }
    @Transactional
    public void thirdTransaction() {
        Subject subject = subjectRepo.findOneById(subjectId);
//        System.out.println(subject);
    }
    private final TeacherDetailsRepo teacherDetailsRepo;

}
