package victor.training.jpa.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.ErrorLog;

import javax.persistence.EntityManager;

@Service
public class TransactionPlayground {

    @Autowired
    private EntityManager em;
    @Autowired
    private AltaClasa altaClasa;

    @Transactional
    public void tx1() {
        em.persist(new ErrorLog("Eroare Fatala"));
        em.flush();
        try {
            altaClasa.altaMetoda();
        } catch (Exception e) {
            //shaorma
        }
    }
}

@Service
class AltaClasa {
    @Autowired
    private EntityManager em;
    @Transactional
    public void altaMetoda() {
        em.persist(new ErrorLog("Alta Eroare"));
        em.flush();
        throw new RuntimeException("Poc");
    }
}
