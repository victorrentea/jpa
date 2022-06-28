package victor.training.jpa.perf;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.TimeSlot;

import javax.persistence.*;
import java.time.DayOfWeek;

@SpringBootTest
public class ImmutableEmbeddableTest {
   @Autowired
   private MutableEntityService service;
   @Test
   public void test() {
      Long id = service.create();
      service.setDuration(id, 4);
      service.moveToRoom(id, "new Room");
      System.out.println(service.get(id).getTimeSlot());
   }
}

@Service
@Transactional
@RequiredArgsConstructor
class MutableEntityService {
   private final MutableEntityRepo repo;
   public Long create() {
      MutableEntity entity = new MutableEntity();
      entity.setTimeSlot(new TimeSlot(DayOfWeek.MONDAY, 8,2,"EC105"));
      return repo.save(entity).getId();
   }

   public void setDuration(long id, int newDuration) {
      MutableEntity mutableEntity = repo.findById(id).get();
      mutableEntity.setTimeSlot(mutableEntity.getTimeSlot().withDurationInHours(newDuration));
   }

   public void moveToRoom(long id, String newRoom) {
      // + some more biz logic
      MutableEntity mutableEntity = repo.findById(id).get();
      mutableEntity.setTimeSlot(mutableEntity.getTimeSlot().withRoomId(newRoom));
   }

   public MutableEntity get(long id) {
      return repo.findById(id).get();
   }
}

interface MutableEntityRepo extends JpaRepository<MutableEntity, Long> {}

@Data
@Entity
class MutableEntity {
   @Id
   @Setter(AccessLevel.NONE)
   @GeneratedValue
   private Long id;

   @Embedded
   private TimeSlot timeSlot;

}