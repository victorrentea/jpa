//package victor.training.jpa.app.facade;
//
//import static java.util.stream.Collectors.toList;
//import static java.util.stream.Collectors.toSet;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import javax.persistence.EntityManager;
//import javax.persistence.LockModeType;
//import javax.persistence.PersistenceContext;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import victor.training.jpa.app.domain.entity.*;
//import victor.training.jpa.app.facade.dto.ContactChannelDto;
//import victor.training.jpa.app.facade.dto.StudentsGroupDto;
//import victor.training.jpa.app.facade.dto.SubjectDto;
//import victor.training.jpa.app.facade.dto.SubjectWithActivitiesDto;
//import victor.training.jpa.app.facade.dto.TeacherDetailsDto;
//import victor.training.jpa.app.facade.dto.TimeSlotDto;
//import victor.training.jpa.app.facade.dto.YearWithGroupsDto;
//import victor.training.jpa.app.util.MyUtil;
//
//@Service
//@Transactional
//public class TheFacade {
//	private final static Logger log = LoggerFactory.getLogger(TheFacade.class);
//
//	@PersistenceContext
//	private EntityManager em;
//
//	@Autowired
//	private AnotherService anotherService;
//
//	@Autowired
//	private NonTransactedService nonTransactedService;
//
//
//	// 1. persist. when IDs are assigned?
//	// 2. link existing entity from DB. check != null
//	// 3. getReference
//	public Long createSubject(SubjectDto subjectDto) {
//		Subject subject = new Subject();
//		subject.setName(subjectDto.name);
//		subject.setHolderTeacher(em.find(Teacher.class, subjectDto.holderTeacherId));
//		log.debug("ID before persist: " + subject.getId());
//		em.persist(subject);
//		return subject.getId();
//	}
//
//	// Object references a transient object.  Cascade or .persist ?
//	public Long createTeacher(TeacherDetailsDto teacherDto) {
//		Teacher teacher = new Teacher();
//		teacher.setName(teacherDto.name);
//		teacher.setGrade(teacherDto.grade);
//		TeacherDetails details = new TeacherDetails();
//		details.setCv(teacherDto.cv);
////		teacher.setDetails(details);
//		em.persist(teacher);
//		return teacher.getId();
//	}
//
//	// 1. TODO observe: auto flush at Tx end
//	// 2. TODO EntityManager = 1st level cache (see logged SQLs). return the Subject from checkPermissions and compare them with ==
//	// 3. TODO If not Transaction -> no save. Use propagation=NOT_SUPPORTED or readOnly=true
//	// 4. lock PESSIMISTIC_WRITE ==>  SELECT FOR UPDATE
//	public void updateSubject(SubjectDto subjectDto) {
//		anotherService.checkPermissionsOnSubject(subjectDto.id);
//		Subject subject = em.find(Subject.class, subjectDto.id, LockModeType.PESSIMISTIC_WRITE); // SELECT FOR UPDATE
//		subject.setName(subjectDto.name);
//		subject.setHolderTeacher(em.find(Teacher.class, subjectDto.holderTeacherId));
//	}
//
//	// Remember to set OWNER side (not mappedBy side) of a relation!
//
//	public long addLab(long subjectId, TimeSlotDto timeSlotDto) {
//		LabActivity lab = new LabActivity();
//		// TODO introduce @Embeddable in the TeachingActivity. Refactor here
//		lab.setDay(timeSlotDto.day);
//		lab.setDurationInHours(timeSlotDto.durationInHours);
//		lab.setStartHour(timeSlotDto.startHour);
//		lab.setRoomId(timeSlotDto.roomId);
//
//		// TODO save the new lab for the given subject
//		// TODO try em.getReference / repo.getOne
//		return lab.getId();
//	}
//	public void deleteLab(long labId) {
//		// TODO using 1) EntityManager 2) Spring Data Repo 3) @Query("DELETE WHERE") - jpql
//	}
//
//	public void assignTeacherToLab(long teacherId, long labId) {
//		LabActivity lab = em.find(LabActivity.class, labId); // TODO replace with Repos
//		Teacher teacher = em.find(Teacher.class, teacherId);
//		// TODO
//		// Remember to update OWNER side (not mappedBy side) of a relation!
//	}
//
//	public void removeTeacherFromLab(long teacherId, long labId) {
//		// TODO
//	}
//
//	// 1. auto Rollback Tx on exception
//	// 2. on exception thrown by an invoked method (anotherService.throwException())
//	// 3. by ANY invoked method ? try with a) static method call b) method in non-transacted bean
//
//	public void updateSubjectFailing(SubjectDto subjectDto) {
//		anotherService.checkPermissionsOnSubject(subjectDto.id);
//		Subject subject = em.find(Subject.class, subjectDto.id);
//		subject.setName(subjectDto.name);
//		subject.setHolderTeacher(em.find(Teacher.class, subjectDto.holderTeacherId));
//		em.flush(); // just here to demonstrate that an INSERT printed in the log can be ROLLEDBACK afterwards
//		try{
//			anotherService.throwException();  // TODO change such that this ex causes ROLLBACK
//			nonTransactedService.throwException(); // TODO what if ...
//			MyUtil.staticMethodThrowingException();
//		} catch (Exception e) {
//			log.debug(e.getMessage(), e);
//		}
//	}
//	// Trying to do something in a dead transaction
//
//	public void updateSubjectFailingLogged(SubjectDto subjectDto) {
//		anotherService.checkPermissionsOnSubject(subjectDto.id);
//		Subject subject = em.find(Subject.class, subjectDto.id);
//		subject.setName(subjectDto.name);
//		subject.setHolderTeacher(em.find(Teacher.class, subjectDto.holderTeacherId));
//		try{
//			anotherService.throwException(); // My Tx dies before returning from this call
//		} catch (Exception e) {
//			log.debug(e.getMessage(), e);
//			// I am walking with a zombie Transaction
//			em.persist(new ErrorLog(e.getMessage())); // TODO FIX
//		}
//	}
//	// Lazy Load collections
//	// TODO 0) use Repo
//	// TODO 1) EAGER - bad
//	// TODO 2) FETCH JOIN
//
//	public SubjectWithActivitiesDto getSubjectWithActivities(Long subjectId) {
//		Subject subject = em.find(Subject.class, subjectId);
//		log.debug("Got Subject from Database");
//		return new SubjectWithActivitiesDto(subject);
//	}
//	// TODO Force the Lazy load of the @..ToOne link
//
//	public TeacherDetailsDto getTeacher(Long teacherId) {
//		Teacher teacher = em.find(Teacher.class, teacherId); // ==> Observe 2 queries are launched here
//		log.debug("Got Teacher from JPA");
//		return new TeacherDetailsDto(teacher);
//	}
//
//	// =============== MERGE start ===============
//	public void updateYearWithGroups_manuallyEditingChildren(long yearId, YearWithGroupsDto dto) {
//		StudentsYear year = em.find(StudentsYear.class, yearId);
//		year.setCode(dto.code);
//
//		// remove children no longer there
//		Set<Long> preservedIds = dto.groups.stream().map(StudentsGroupDto::getId).collect(toSet());
//		Set<StudentsGroup> toRemove = new HashSet<>();
//		for (StudentsGroup oldGroup : year.getGroups()) {
//			if (!preservedIds.contains(oldGroup.getId())) {
//				toRemove.add(oldGroup);
//			}
//		}
//		year.getGroups().removeAll(toRemove);
//
//		// add and update
//		for (StudentsGroupDto groupDto : dto.groups) {
//			if (groupDto.id == null) {
//				StudentsGroup newGroup = new StudentsGroup();
//				newGroup.setCode(groupDto.code);
//				newGroup.setEmails(groupDto.emails);
//				em.persist(newGroup);
//				year.getGroups().add(newGroup);
//				newGroup.setYear(year);
//			} else {
//				StudentsGroup oldGroup = year.getGroups().stream().filter(g -> g.getId().equals(groupDto.id)).findFirst().get();
//				oldGroup.setCode(groupDto.code);
//			}
//		}
//	}
//
//
//	// TODO read:
//
//	public void updateYearWithGroups_cascadingMerge(long yearId, YearWithGroupsDto yearDto) {
//		// to work, year should cascade to group
//		StudentsYear year = new StudentsYear();
//		year.setCode(yearDto.code);
//		year.setId(yearDto.id);
//
//		for (StudentsGroupDto groupDto : yearDto.groups) {
//			StudentsGroup group = new StudentsGroup();
//			group.setId(groupDto.id);
//			group.setCode(groupDto.code);
//			group.setEmails(groupDto.emails);
//			group.setYear(year);
//			year.getGroups().add(group);
//		}
//		StudentsYear yearFoundBefore = em.find(StudentsYear.class, yearId);
//		yearFoundBefore.setCode("x");
//		StudentsYear returnedByMerge = em.merge(year);
//
//		log.debug("First instance was attached? {}; Or another one was loaded from db, updated and returned? {}"
//				,em.contains(year)
//				,em.contains(returnedByMerge));
//		log.debug("The entity already attached before merge is == the entity returned by merge? {}", returnedByMerge == yearFoundBefore );
//		log.debug("The newly created entity is == the entity returned by merge? {}", returnedByMerge == year );
//	}
//
//	// =========================== gata merge =======================
//	public List<ContactChannelDto> getTeacherChannels(long teacherId) {
//		Teacher teacher = em.find(Teacher.class, teacherId);
//		log.debug("Teacher got from DB");
//		return teacher.getChannels().stream().map(ContactChannelDto::new).collect(toList());
//	}
//
//	public void setTeacherChannels(long teacherId, List<ContactChannelDto> channelDtos) {
//		List<ContactChannel> channels = new ArrayList<>();
//		for (ContactChannelDto dto : channelDtos) {
//			channels.add(new ContactChannel(dto.type, dto.value));
//		}
//		Teacher teacher = em.find(Teacher.class, teacherId);
//		teacher.setChannels(channels); // TODO observe what happens
//	}
//
//	public Set<TeachingActivity> getAllActivities(long yearId) {
//		StudentsYear year = em.find(StudentsYear.class, yearId); // TODO optimize
//		Set<TeachingActivity> set = new HashSet<>();
//		for (CourseActivity course : year.getCourses()) {
//			set.add(course);
//			for (StudentsGroup group: year.getGroups()) {
//				set.addAll(group.getLabs());
//			}
//		}
//		return set;
//	}
//
//
//
//	// TODO [HARD-CORE]
//	public SubjectDto openUpdate(long subjectId) {
//		anotherService.checkPermissionsOnSubject(subjectId);
//		Subject subject = em.find(Subject.class, subjectId); // SELECT FOR UPDATE
//
//		// TODO insert a line into a new entity SubjectLock storing the user id acquiring the edit lock + an expiration time = now+30 minutes
//		// Tip: how to get current username: victor.training.jpa.magic.SpringSecurityAuditorAware.getCurrentAuditor
//		// TODO on #updateSubject check the SubjectLock to be active and remove it after merging the incoming changes.
//		// TODO in #openUpdate check to see whether there is no existing ACTIVE lock taken by any other user (ok to take it again by myself)
//		// TODO remove @Version (optimistic locking) as it's not needed anymore
//		// TODO protect against concurrent INSERT into SUBJECT_LOCKS by finding the subject with   at em.find(,, LockModeType.PESSIMISTIC_WRITE);
//		return new SubjectDto(subject);
//	}
//}
