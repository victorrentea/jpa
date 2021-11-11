package victor.training.jpa.app.repo;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import victor.training.jpa.app.common.data.CustomJpaRepository;
import victor.training.jpa.app.domain.entity.Subject;
import victor.training.jpa.app.domain.entity.Teacher;

public interface TeacherRepo extends CustomJpaRepository<Teacher, Long>, TeacherRepoCustom, JpaSpecificationExecutor<Teacher> {

	@Query("SELECT DISTINCT a.day FROM Teacher t JOIN t.activities a WHERE t.id=?1")
	public Set<DayOfWeek> getBusyDaysOfTeacher(long teacherId);
	
	@Query("SELECT DISTINCT a.subject FROM Teacher t JOIN t.activities a WHERE t.id=?1")
	public Set<Subject> getSubjectsKnownByTeacher(long teacherId);

	@Query("SELECT DISTINCT t FROM TeachingActivity a JOIN a.teachers t "
			+ "WHERE a.group.id =?1 or a.year.id=(select g.year.id from StudentsGroup g where g.id=?1)")
	public Set<Teacher> getTeachersKnownByGroup(long groupId);
	
	@Query("SELECT DISTINCT a.subject FROM TeachingActivity a WHERE a.roomId=?1")
	public Set<Subject> getSubjectsInRoom(String roomId);


	// TODO make return null!
	Optional<Teacher> findByName(String name);

	@Query("SELECT t FROM TeachingActivity a JOIN a.teachers t WHERE "
			 + "a.id IN (SELECT c.id FROM StudentsYear y JOIN y.courses c WHERE y.id = ?1) "
			 + "OR a.id IN (SELECT lab.id FROM StudentsYear y JOIN y.groups g JOIN g.labs lab WHERE y.id = ?1)")
	List<Teacher> getAllTeachersForYear(long yearId);
}
