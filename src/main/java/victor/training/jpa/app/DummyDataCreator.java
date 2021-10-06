package victor.training.jpa.app;

import static java.util.Arrays.asList;

import java.time.DayOfWeek;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import victor.training.jpa.app.domain.entity.*;

@Component
public class DummyDataCreator {

	@PersistenceContext
	private EntityManager em;

	@EventListener(ApplicationStartedEvent.class)
	@Transactional
	public void persistDummyData() {
		Teacher victor = new Teacher("Victor");
		victor.setGrade(Teacher.Grade.ASSISTANT);
		TeacherDetails teacherDetails = new TeacherDetails().setCv("A pimped CV");
		victor.setDetails(teacherDetails);
		TimeSlot timeSlot = new TimeSlot(DayOfWeek.MONDAY, 1, 8, "EF403");
		victor.setCounselingSlot(timeSlot);
		em.persist(victor);


		Teacher ionut = new Teacher("Ionut");
		ionut.setCounselingSlot(timeSlot);
		ionut.setGrade(Teacher.Grade.ASSISTANT);
		em.persist(ionut);

		Teacher bianca = new Teacher("Bianca");
		bianca.setGrade(Teacher.Grade.ASSISTANT);
		bianca.setCounselingSlot(timeSlot);
		em.persist(bianca);

		
		Subject subject = new Subject("OOP");
		subject.setHolderTeacher(victor);
		CourseActivity course = new CourseActivity();
		course.setSubject(subject);
		course.setTimeSlot(timeSlot);
		course.getTeachers().add(victor);

		LabActivity lab1 = new LabActivity();
		lab1.setSubject(subject);

		lab1.setTimeSlot(timeSlot);
		lab1.getTeachers().add(bianca);
		lab1.getTeachers().add(ionut);

		LabActivity lab2 = new LabActivity();
		lab2.setSubject(subject);
		lab2.setTimeSlot(timeSlot);

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
