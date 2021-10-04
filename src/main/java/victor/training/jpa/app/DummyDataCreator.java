package victor.training.jpa.app;

import static java.util.Arrays.asList;

import java.time.DayOfWeek;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import victor.training.jpa.app.domain.entity.*;

@Component
public class DummyDataCreator {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void persistDummyData() {
		Teacher victor = new Teacher("Victor");
		victor.setGrade(Teacher.Grade.ASSISTANT);
		TeacherDetails teacherDetails = new TeacherDetails().setCv("A pimped CV");
//		victor.setDetails(teacherDetails);
//		victor.setCounselingDay(DayOfWeek.MONDAY);
//		victor.setCounselingDurationInHours(1);
//		victor.setCounselingRoomId("EF403");
//		victor.setCounselingStartHour(8);
		em.persist(victor);
		
		
		Teacher ionut = new Teacher("Ionut");
		ionut.setGrade(Teacher.Grade.ASSISTANT);
		em.persist(ionut);

		Teacher bianca = new Teacher("Bianca");
		bianca.setGrade(Teacher.Grade.ASSISTANT);
		em.persist(bianca);
		
		Subject subject = new Subject("OOP");
		subject.setHolderTeacher(victor);
		CourseActivity course = new CourseActivity();
		course.setSubject(subject);
		course.setTimeSlot(new TimeSlot(DayOfWeek.MONDAY, 8, 3, "EC105"));
		course.getTeachers().add(victor);
		
		LabActivity lab1 = new LabActivity();
		lab1.setSubject(subject);
		lab1.setTimeSlot(new TimeSlot(DayOfWeek.MONDAY, 11, 2, "EC202"));
		lab1.getTeachers().add(bianca);
		lab1.getTeachers().add(ionut);
		
		LabActivity lab2 = new LabActivity();
		lab2.setSubject(subject);
		lab2.setTimeSlot(new TimeSlot(DayOfWeek.TUESDAY, 11, 2, "EC203"));
		lab2.getTeachers().add(ionut);
		
		StudentsYear year = new StudentsYear("3CA");
		
		StudentsGroup group1 = new StudentsGroup("321");
		group1.setEmails(asList("a@b.com", "c@d.com"));
		StudentsGroup group2 = new StudentsGroup("322");
		group1.setYear(year);
		year.getGroups().add(group1);
		group2.setYear(year);
		year.getGroups().add(group2);
		
		group1.getLabs().add(lab1);
		lab1.setGroup(group1);
		group2.getLabs().add(lab2);
		lab2.setGroup(group2);
		course.setYear(year);
		
		em.persist(year);
		em.persist(group1);
		em.persist(group2);
		em.persist(course);
		em.persist(lab1);
		em.persist(lab2);
		em.persist(subject);
	}
}
