package victor.training.jpa.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.*;
import victor.training.jpa.app.repo.TeacherRepo;
import victor.training.jpa.app.repo.TeachingActivityRepo;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;

@Service
public class Playground {
    public static final Logger log = LoggerFactory.getLogger(Playground.class);

    @Autowired
    private EntityManager em;
    private Long teacherId;


    @Transactional
    public void firstTransaction() {
        log.debug("Halo!");

//        Teacher tdejaAcolo = new Teacher();
//        tdejaAcolo.setId(2L);
//        em.merge(tdejaAcolo);

        TeacherDetails details = new TeacherDetails();
        details.setCv("pimped");
//        em.persist(details); // putem sa-l scoatem pe asta ?

        Teacher teacher = new Teacher();
        teacher.setName("Giumale");
        teacher.setDetails(details);
        System.out.println("Teacher.id inainte = " + teacher.getId());
        em.persist(teacher);
        System.out.println("Teacher.id dupa = " + teacher.getId());
        teacherId = teacher.getId();


        //        em.persist(subject); // discutabil, deoarece viata cursului de FP nu depinde de existenta lui Giumale.
        teacher.addSubject(new Subject("ML"));
        teacher.addSubject(new Subject("FP"));
        teacher.addSubject(new Subject("MAS"));

//        teacher.addActivity(new LabActivity());
//        teacher.addActivity(new LabActivity());

        teacher.getChannels().add(new ContactChannel(ContactChannel.Type.TWITTER, "the_real_sn"));
        teacher.getChannels().add(new ContactChannel(ContactChannel.Type.FACEBOOK, "brexit"));

        em.persist(new Teacher("Vlad"));
        em.persist(new Country2(13L,"Romania"));

        // teacher.getHeldSubjects().add(subject) // crapa la runtime, fraere
        // subject.setHolderTeacher(teacher); nu compileaza, fraere
        // Unde, fraere = tu peste 6 luni
    }

    @Transactional
    public void secondTransaction() {
        log.debug("Halo2!");

        Teacher teacher = em.find(Teacher.class, teacherId);
        teacher.setName("Cristian");
        System.out.println(teacher.getName());
        System.out.println(teacher.getDetails().getCv());

        System.out.println(teacher.getHeldSubjects());
        Teacher teacherDeDeparte = altaDeparte.damiTeacher(teacherId);

        System.out.println("Oare teacherul local este == cu al de departe ?" +
                (teacherDeDeparte == teacher));

        System.out.println("Contact channels: " + teacher.getChannels());

        teacher.removeSubject(teacher.getHeldSubjects().iterator().next());
        System.out.println("******************* Tu cel intri, lasa in urma orice sperata... Urmeaza search-ul");


        ActivitySearchCriteria criteria  = new ActivitySearchCriteria();
//        criteria.hour = 7;
        criteria.room="eC";
        List<TeachingActivity> results = teachingActivityRepo.search(criteria);
        System.out.println(results);

        // iata vine un json de Frigider {
        long countryId = 13L;
        System.out.println("Iata vine-un frigider");
        Frigider frigider = new Frigider();
//        frigider.setCountry2(em.find(Country2.class, countryId)); // face un SELECT
        Country2 proxyLaCountry = em.getReference(Country2.class, countryId);
        System.out.println("Country = "  + proxyLaCountry.getClass());
        frigider.setCountry2(proxyLaCountry); // face un SELECT
        em.persist(frigider);
    }
    @Autowired
    private TeachingActivityRepo teachingActivityRepo;

    @Autowired
    AltaClasaOverTheHillsAndFarAway altaDeparte;

    @Autowired
    private TeacherRepo teacherRepository;

    @Transactional
    public void nPlus1() {
        Set<Teacher> teachers = teacherRepository.findAllFetchingSubjects();

        System.out.println("I-am adus pe inculpati");
        for (Teacher teacher : teachers) {
//            em.detach(teacher);
            System.out.println("Subiectele lui " + teacher.getName() + " : " + teacher.getHeldSubjects());
        }
    }
}

@Service
class AltaClasaOverTheHillsAndFarAway {
    @Autowired
    private TeacherRepo repo;

    public Teacher damiTeacher(Long teacherId) {
        return repo.findById(teacherId).get();
    }
}
