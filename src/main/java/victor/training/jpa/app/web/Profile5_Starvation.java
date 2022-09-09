package victor.training.jpa.app.web;

import io.micrometer.core.annotation.Timed;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("profile/sheep")
@RequiredArgsConstructor
class SheepController {
    private final SheepService service;

    @GetMapping("create")
    public Long createSheep(@RequestParam(required = false) String name) {
        if (name == null) {
            name = "Bisisica " + LocalDateTime.now();
        }
        log.debug("create " + name);
        return service.create(name);
    }

    @GetMapping("search")
    public List<Sheep> searchSheep(@RequestParam(defaultValue = "Bisisica%") String name) {
        log.debug("search for " + name);
        return service.search(name);
    }
}

@Slf4j
@Service
@RequiredArgsConstructor
class SheepService {
    private final SheepRepo repo;
    private final ShepardService shepard;

//    @Transactional nenecesar caci repo.save() are @Trn el .
    public Long create(String name) {
//        System.out.println(repo.findAll());
        // DRAMA: apel de HTTP intr-o met @Transactional: blochezi conn pentru prea MULT timp
        String sn = shepard.registerSheep(name); // Takes 1 second (HTTP call)
        Sheep sheep = repo.save(new Sheep(name, sn));
        return sheep.getId();
    }
    public List<Sheep> search(String name) {
        return repo.getByNameLike(name); // are nevoie sa ia conn din pool
    }
}
@Slf4j
@Service
@RequiredArgsConstructor
class ShepardService {
    @SneakyThrows
    public String registerSheep(String name) {
//        SheepRegistrationResponse response = new RestTemplate()
//            .getForObject("http://localhost:9999/api/register-sheep", SheepRegistrationResponse.class);

        // or, using Feign client
        // SheepRegistrationResponse response = client.registerSheep();
//        return response.getSn();

        Thread.sleep(100);

        return UUID.randomUUID().toString();
    }
}

@Data
class SheepRegistrationResponse {
    private String sn;
}

interface SheepRepo extends JpaRepository<Sheep, Long> {
    List<Sheep> getByNameLike(String name);
}


@Entity
@Data
class Sheep {
    @GeneratedValue
    @Id
    private Long id;

    private String name;
    private String sn;

    public Sheep() {}
    public Sheep(String name, String sn) {
        this.name = name;
        this.sn = sn;
    }
}
