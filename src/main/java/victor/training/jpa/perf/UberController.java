//package victor.training.jpa.perf;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import victor.training.jpa.perf.entity.Country;
//import victor.training.jpa.perf.entity.Scope;
//import victor.training.jpa.perf.entity.UberEntity;
//import victor.training.jpa.perf.entity.User;
//import victor.training.jpa.perf.repo.UberRepo;
//
//import javax.persistence.EntityManager;
//
//@RequiredArgsConstructor
//@RestController
//public class UberController implements CommandLineRunner {
//   @Autowired
//   private EntityManager entityManager;
//   @Autowired
//   private UberRepo uberRepo;
//
//   private Country romania = new Country(1L, "Romania");
//   private User testUser = new User("test");
//   private Scope globalScope = new Scope(1L, "Global");
//
//   private Long uberId;
//
//   @Override
//   public void run(String... args) throws Exception {
//      entityManager.persist(romania);
//      entityManager.persist(testUser);
//      entityManager.persist(globalScope);
//
//      UberEntity uber = new UberEntity()
//          .setFiscalCountry(romania)
//          .setOriginCountryId(romania.getId()) // One less JOIN or -1 SELECT
//          .setInvoicingCountry(romania)
//          .setCreatedBy(testUser)
//          .setNationality(romania)
//          .setScope(globalScope);
//      entityManager.persist(uber);
//      uberId = uber.getId();
//   }
//
//   @GetMapping("/uber")
//   public UberDto method() {
//      return uberRepo.loadForUI(uberId);
//   }
//}
