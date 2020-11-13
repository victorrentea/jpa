package victor.training.jpa.app.repo;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import victor.training.jpa.app.common.data.EntityRepository;
import victor.training.jpa.app.domain.entity.Subject;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;

public interface TeacherRepo extends EntityRepository<Teacher, Long>, TeacherRepoCustom {

	@Query("SELECT DISTINCT a.timeSlot.day FROM Teacher t JOIN t.activities a WHERE t.id=?1")
	public Set<DayOfWeek> getBusyDaysOfTeacher(long teacherId);

	@Query("SELECT DISTINCT a.subject FROM Teacher t JOIN t.activities a WHERE t.id=?1")
	public Set<Subject> getSubjectsKnownByTeacher(long teacherId);

	@Query("SELECT DISTINCT a.subject FROM TeachingActivity a WHERE a.timeSlot.roomId=?1")
	public Set<Subject> getSubjectsInRoom(String roomId);

   @Query(value = "INSERT INTO TEACHER(ID) VALUES (?)", nativeQuery = true)
   void insertStuff(long id);
      // TODO make return null!
   Optional<Teacher> findByName(String name);

   @Query("SELECT t FROM Teacher t left join fetch t.heldSubjects s left join fetch s.y")
	List<Teacher> findAllForExport();


}
