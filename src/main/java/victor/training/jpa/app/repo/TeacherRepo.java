package victor.training.jpa.app.repo;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import victor.training.jpa.app.domain.entity.CalendarEntry;
import victor.training.jpa.app.domain.entity.Subject;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.Teacher.Grade;
import victor.training.jpa.app.repo.common.CustomJpaRepository;

public interface TeacherRepo extends CustomJpaRepository<Teacher, Long>, TeacherRepoCustom, JpaSpecificationExecutor<Teacher> {

   Optional<Teacher> findByNameLikeAndGrade(String namePart, Grade grade);

   @Query("FROM Teacher")
   Stream<Teacher> totiCasMulti();



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


   List<Teacher> findByCounselingCalendarEntry(CalendarEntry entry); // a mers
    @Query("SELECT t.counselingCalendarEntry FROM Teacher t")
//    @Query("SELECT t.counselingCalendarEntry FROM Teacher t")
   List<CalendarEntry> findByCounselingCalendarEntryRoomId(String roomId);

}
