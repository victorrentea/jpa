package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.ErrorLog;
import victor.training.jpa.app.repo.ErrorLogRepo;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.io.IOException;

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
      tr.write();
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
      ErrorLog a = this.a.method();
      ErrorLog b = this.b.method();
      System.out.println(a == b);
      log.info("END 2");
   }
   @Transactional
   public void write() {
      log.info("START 3");
      a.write();
      try {
         b.write();
      } catch (Exception e) {
         //shaworma-style error handling. That's your future job
      }
      a.write();
      em.flush();
      log.info("END 3");
   }
}

@Component
@Slf4j
@RequiredArgsConstructor
class A {
   private final EntityManager entityManager;


   @Transactional
   public void write() {
      entityManager.persist(new ErrorLog("A"));
   }

   @Transactional
   public ErrorLog method() {
      return entityManager.find(ErrorLog.class, 1L);
   }

}

@Component
@Slf4j
@RequiredArgsConstructor
class B {
   private final EntityManager entityManager;
   private final ErrorLogRepo repo;

   @Transactional(rollbackFor = Exception.class)
   public void write() throws IOException {
      entityManager.persist(new ErrorLog("B"));
      throw new IOException();
   }

   @Transactional
   public ErrorLog method() {
//      return entityManager.find(ErrorLog.class, 1L);
      //
//      return repo.findById(1L).get();
      return repo.findLikeAHipster(1L);
   }

}