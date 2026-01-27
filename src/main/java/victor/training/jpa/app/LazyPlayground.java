package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.jpa.app.entity.Subject;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.repo.SubjectRepo;
import victor.training.jpa.app.repo.TeacherRepo;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LazyPlayground {
    private final TeacherRepo teacherRepo;
    private final SubjectRepo subjectRepo;
    private Long teacherId; // DOAMNE FERESTE sa tii stare specifica UNUI request daca e singleton

    public void firstTransaction() {
        Subject subject = subjectRepo.save(new Subject("AI"));
        Teacher teacher = teacherRepo.save(new Teacher().setName("Profu").addHeldSubject(subject));
        teacherId = teacher.getId();
    }

    @Transactional(readOnly = true)
    // NECESAR daca threadul care-ti vine NU e din HTTP,de ex din @Test sau @Scheduler, sau WS handler, @rabbitListener
    public void secondTransaction() {
        Teacher teacher = teacherRepo.findById(teacherId).get();
        System.out.println("Loaded teacher: " + teacher);
    }

    @GetMapping("lazy")
//    @Transactional(readOnly = true) // quick fix
    public String httpEndpoint() {
        // Gafa de arhitectura, dar devii Spring nu-s toti foarte atenti
        //Și cei de la Spring Boot știu asta și vor să facă cât mai ușoară intrarea în
        // framework-ul nostru, așa încât, by default, nu îți vor
        // elibera conexiunea după ce ți-ai făcut treaba cu baza, ci o țin blocată până închizi request-ul HTTP curent.
        // == MARKETING

        // - stack overflow daca ai relatii bidirectionale
        // - data coupling cu clientul => imposibil sa schimbi modelul intern fara sa spargi clientii
        // - privacy risk cand adaugi campuri sensibile in entity maine
        Teacher teacher = teacherRepo.findByIdWithHeldSubjects(teacherId).get();
        System.out.println("Loaded teacher: " + teacher.getHeldSubjects()); // SELECT
        System.out.println("Ies din metoda");
        // ceva ce ia timp fara DB: REST API call 👑, WS:// push notificare, calcule CPU intensive
        return teacher.getName();
    }
}