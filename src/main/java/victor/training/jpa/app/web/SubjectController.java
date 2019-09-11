package victor.training.jpa.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import victor.training.jpa.app.domain.entity.ErrorLog;
import victor.training.jpa.app.domain.entity.Subject;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@RestController
public class SubjectController {

    @Autowired
    private EntityManager em;

    @Transactional
    @GetMapping("subject/{id}")
    public void setActive(@PathVariable long id,
                          @RequestParam boolean active) {
        Subject subject = em.find(Subject.class, id);
        subject.setActive(active);

        // modu manual .CPP
//        subject.setLastModifiedDate(LocalDateTime.now());
//        subject.setLastModifiedBy("test");
//        System.out.println("User = " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        em.persist(new ErrorLog("Test"));
    }
}
