package victor.training.jpa.app.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;
import victor.training.jpa.app.domain.entity.Teacher;

public interface TeacherRepo extends Repository<Teacher, Long>, TeacherRepoCustom,
    JpaSpecificationExecutor<Teacher> {

   Teacher save(Teacher teacher);

   Teacher findById(Long id);
//	@Query("SELECT DISTINCT a.day FROM Teacher t JOIN t.activities a WHERE t.id=?1")
//	public Set<DayOfWeek> getBusyDaysOfTeacher(long teacherId);
//
//	@Query("SELECT DISTINCT a.subject FROM Teacher t JOIN t.activities a WHERE t.id=?1")
//	public Set<Subject> getSubjectsKnownByTeacher(long teacherId);
//
//	@Query("SELECT DISTINCT t FROM TeachingActivity a JOIN a.teachers t "
//			+ "WHERE a.group.id =?1 or a.year.id=(select g.year.id from StudentsGroup g where g.id=?1)")
//	public Set<Teacher> getTeachersKnownByGroup(long groupId);
//
//	@Query("SELECT DISTINCT a.subject FROM TeachingActivity a WHERE a.roomId=?1")
//	public Set<Subject> getSubjectsInRoom(String roomId);
//
//	// TODO make return null!
//	Optional<Teacher> findByName(String name);


}
