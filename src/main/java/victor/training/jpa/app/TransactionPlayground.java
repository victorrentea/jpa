package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.ErrorLog;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionPlayground {
    private final JdbcTemplate jdbc;
    private final TeacherRepo teacherRepo;
    private final ErrorLogRepo repo;
    private final DataSource ds;


    public void firstTransaction() {

//        Connection conn = ds.getConnection();
//        conn.prepareStatement("SQL NATIV")


        log.debug("Function Begin");

        // natural key:
        //        String id = "CUI RO1412515";
        //        String id = "<fileName>-<retryNo>-<lineNo>";
        // PRO: cheile inseamna ceva. Au meaning.
        // PRO: poti genera PK FARA sa intrebi baza. > mai rapid un pic la inserturi masive (cica)
        // CONS: mai dai si PK violation
        // CONS: performanta mai slaba in DB INDECSII merg mai prost

        String id = UUID.randomUUID().toString(); // urat dar unic
        // PRO: poate fi generat de oricine, oriunde.

        ErrorLog entity = new ErrorLog("Halo!");
        repo.save(entity); // daca generezi ID-uri cu strategy=IDENTITY => pentru a obtine acest ID nou, hib trebuie sa TRIMITA INSERTUL in DB. ATUNCI PE LOC. >> nu poate face batching,
        System.out.println("Intotdeauna dupa SAVE, chiar DACA" +
                           " NU VEZI INSERTUL DUCANDU_SE IN BAZA, entitatii tale i s-a setat ID = <seq>" + entity.getId());

        repo.save(new ErrorLog(null));

        log.debug("Function End");
        jdbc.update("INSERT INTO TEACHER(ID) VALUES (HIBERNATE_SEQUENCE.nextval)");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void secondTransaction() {
    }
}
