package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.ErrorLog;
import victor.training.jpa.app.repo.ErrorLogRepo;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionPlay {
   private final TransactionsAllAroundUs tr;

   @PostConstruct
   public void init() {
      System.out.println("Shaking hands with a proxy : " + tr.getClass()); // contains CGLIB
      tr.first();
      tr.second();
   }
}

@Component
@RequiredArgsConstructor
@Slf4j
class TransactionsAllAroundUs {
   private final EntityManager em;
   private final ErrorLogRepo repo;
   private final A a;
   private final B b;

   @Transactional
   public void first() {
      log.info("START 1");
      em.persist(new ErrorLog("ONE"));
   }
   @Transactional
   public void second() {
      log.info("START 2");
      a.method();
      b.method();
      log.info("END 2");
   }
}

@Component
@Slf4j
@RequiredArgsConstructor
class A {
   private final EntityManager entityManager;
   @Transactional
   public void method() {
      entityManager.persist(new ErrorLog("A"));
//      return entityManager.find(ErrorLog.class, 1L);
   }

}

@Component
@Slf4j
@RequiredArgsConstructor
class B {
   private final EntityManager entityManager;
   @Transactional
   public void method() {
      entityManager.persist(new ErrorLog("B"));
//      return entityManager.find(ErrorLog.class, 1L);
   }

}