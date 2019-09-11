package victor.training.jpa.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.*;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.DayOfWeek;

import static java.util.Arrays.asList;

@Component
public class DummyDataCreator {

	private final static Logger log = LoggerFactory.getLogger(DummyDataCreator.class);

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private TeacherRepo teacherRepo;

	@Transactional
	public void persistDummyData() {
		if (teacherRepo.findByName("Victor").isPresent()) {
			log.info("Skipping: Dummy data already in DB.YY");
			return;
		}
		Teacher victor = new Teacher("Victor");
		victor.setGrade(Teacher.Grade.ASSISTENT);
		TeacherDetails teacherDetails = new TeacherDetails().setCv("A pimped CV");
		victor.setDetails(teacherDetails);
		TimeSlot timeSlot4 = new TimeSlot();
		timeSlot4.setDay(DayOfWeek.MONDAY);
		timeSlot4.setDurationInHours(1);
		timeSlot4.setRoomId("EF403");
		timeSlot4.setStartHour(8);
		victor.setTimeSlot(timeSlot4);
		em.persist(victor);


		Teacher ionut = new Teacher("Ionut");
		ionut.setGrade(Teacher.Grade.ASSISTENT);
		em.persist(ionut);

		Teacher bianca = new Teacher("Bianca");
		bianca.setGrade(Teacher.Grade.ASSISTENT);
		em.persist(bianca);

		Subject subject = new Subject("OOP");
		CourseActivity course = new CourseActivity();
		course.setSubject(subject);
		TimeSlot timeSlot = new TimeSlot();
		timeSlot.setDay(DayOfWeek.MONDAY);
		timeSlot.setStartHour(8);
		timeSlot.setDurationInHours(3);
		timeSlot.setRoomId("EC105");
		course.setTimeSlot(timeSlot);
		victor.addSubject(subject);

		LabActivity lab1 = new LabActivity();
		lab1.setSubject(subject);
		TimeSlot timeSlot1 = new TimeSlot();
		timeSlot1.setDay(DayOfWeek.MONDAY);
		timeSlot1.setStartHour(11);
		timeSlot1.setDurationInHours(2);
		timeSlot1.setRoomId("EC202");
        lab1.setTimeSlot(timeSlot1);
		lab1.getTeachers().add(bianca);
		lab1.getTeachers().add(ionut);

		LabActivity lab2 = new LabActivity();
		lab2.setSubject(subject);
        TimeSlot timeSlot2 = new TimeSlot();
		timeSlot2.setDay(DayOfWeek.TUESDAY);
		timeSlot2.setStartHour(11);
		timeSlot2.setDurationInHours(2);
		timeSlot2.setRoomId("EC203");
		lab2.setTimeSlot(timeSlot2);
		lab2.getTeachers().add(ionut);

		StudentsYear year = new StudentsYear("3CA");

		StudentsGroup group1 = new StudentsGroup("321");
		group1.setEmails(asList("a@b.com", "c@d.com"));
		StudentsGroup group2 = new StudentsGroup("322");
		year.getGroups().add(group1);
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
