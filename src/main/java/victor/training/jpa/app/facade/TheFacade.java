package victor.training.jpa.app.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.*;
import victor.training.jpa.app.facade.dto.*;
import victor.training.jpa.app.repo.LabRepo;
import victor.training.jpa.app.repo.SubjectRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class TheFacade {
    private final EntityManager em;
    private final AnotherService anotherService;
    private final TeacherRepo teacherRepo;
    private final SubjectRepo subjectRepo;
    private final LabRepo labRepo;


    // TODO when are IDs assigned ?
    // TODO Exception: object references an unsaved transient instance: a) save details separately or b) cascade
    public Long createTeacher(TeacherDetailsDto teacherDto) {
        Teacher teacher = new Teacher()
                .setName(teacherDto.getName())
                .setGrade(teacherDto.getGrade())
                .setMoreDetails(teacherDto.getMoreDetails())
                .setCounselingDay(teacherDto.getCounselingInterval().getDay())
                .setCounselingDurationInHours(teacherDto.getCounselingInterval().getDurationInHours())
                .setCounselingStartHour(teacherDto.getCounselingInterval().getStartHour())
                .setCounselingRoomId(teacherDto.getCounselingInterval().getRoomId())
                .setDetails(new TeacherDetails()
                        .setCv(teacherDto.getCv()));
        log.debug("ID before persist: " + teacher.getId());
        teacherRepo.save(teacher);
        log.debug("ID after persist: " + teacher.getId());
        Long id = teacher.getId(); // SOLUTION: get id after save()
        return id;
    }

    public Long createSubject(SubjectDto subjectDto) {
        Subject subject = new Subject()
                .setName(subjectDto.getName())
                // TODO link existing entity from DB: a) Repo.getReference, b) new Teacher().setId()
//                .setHolderTeacher(teacherRepo.findOneById(subjectDto.getHolderTeacherId()))
                .setHolderTeacher(new Teacher().setId(subjectDto.getHolderTeacherId())) // SOLUTION: how to link to existing entity
                ;
        return subjectRepo.save(subject).getId();
    }

    public SubjectDto getSubjectById(Long subjectId) {
        return new SubjectDto(subjectRepo.findOneById(subjectId));
    }

    @Transactional // SOLUTION
    public void updateSubject(SubjectDto subjectDto) {
        anotherService.checkPermissionsOnSubject(subjectDto.getId());
        Subject subject = subjectRepo.findOneById(subjectDto.getId());
        subject.setName(subjectDto.getName())
                .setHolderTeacher(new Teacher().setId(subjectDto.getHolderTeacherId()));
        // TODO 1 subjectRepo.save, OR (exclusive):
        // TODO 2 @Transactional on the method ==> "Auto-Flush" dirty Entities at Tx COMMIT, after "Exit method". >> remove repo.save!
        // TODO experiment @Transactional(readonly=true)
        System.out.println("Exit method");
    }

    public long addLabToSubject(long subjectId, TimeSlotDto timeSlotDto) {
        LabActivity lab = new LabActivity();

        // TODO Refactor: introduce @Embeddable in the TeachingActivity
        lab.setDayOfWeek(timeSlotDto.getDay());
        lab.setDurationInHours(timeSlotDto.getDurationInHours());
        lab.setStartHour(timeSlotDto.getStartHour());
        lab.setRoomId(timeSlotDto.getRoomId());

        // TODO link lab to subject
        lab.setSubject(new Subject().setId(subjectId)); // SOLUTION
        return labRepo.save(lab).getId();
    }

    //    @Transactional // SOLUTION
    public SubjectWithActivitiesDto getSubjectWithActivities(Long subjectId) {
//        Subject subject = subjectRepo.findOneById(subjectId);
        Subject subject = subjectRepo.findByIdWithActivities(subjectId); // SOLUTION
        log.debug("Got Subject from Database");
        // TODO fix "failed to lazily initialize a collection of role" by
        //   1) allowing lazy-loading via @Transactional, OR (exclusive)
        //   2) pre-fetching the children with @Query("... Subject s LEFT JOIN FETCH s.activities ...")
        return new SubjectWithActivitiesDto(subject);
    }

    @Transactional // SOLUTION to auto-flush
    public void assignTeacherToLab(long teacherId, long labId) {
        LabActivity lab = labRepo.findOneById(labId);
        Teacher teacher = teacherRepo.findOneById(teacherId);
        teacher.getActivities().add(lab); // SOLUTION
        // TODO Remember to update OWNER side (not mappedBy side) of a relation!
        lab.getTeachers().add(teacher); // SOLUTION
    }

    @Transactional // for lazy load... (guilty)
    public List<LabDto> getAllLabs() {
        return labRepo.findAll().stream().map(LabDto::new).collect(toList());
    }

    public TeacherDto getTeacherById(Long teacherId) {
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow();
        return new TeacherDto(teacher);
    }

// TODO Force the Lazy load of the @..ToOne link
//    public String getTeacherCV(Long teacherId) {
//        Teacher teacher = teacherRepo.findOneById(teacherId); // ==> Observe 2 queries are launched here
//        return new TeacherDetailsDto(teacher);
//    }


    // =============== MERGE start ===============



    // TODO read:

    public void updateYearWithGroups_cascadingMerge(long yearId, YearWithGroupsDto yearDto) {
        // to work, year should cascade to group
        StudentsYear year = new StudentsYear();
        year.setCode(yearDto.code);
        year.setId(yearDto.id);

        for (StudentsGroupDto groupDto : yearDto.groups) {
            StudentsGroup group = new StudentsGroup();
            group.setId(groupDto.id);
            group.setCode(groupDto.code);
            group.setEmails(groupDto.emails);
            group.setYear(year);
            year.getGroups().add(group);
        }
        StudentsYear yearFoundBefore = em.find(StudentsYear.class, yearId);
        yearFoundBefore.setCode("x");
        StudentsYear returnedByMerge = em.merge(year);

        log.debug("First instance was attached? {}; Or another one was loaded from db, updated and returned? {}"
                , em.contains(year)
                , em.contains(returnedByMerge));
        log.debug("The entity already attached before merge is == the entity returned by merge? {}", returnedByMerge == yearFoundBefore);
        log.debug("The newly created entity is == the entity returned by merge? {}", returnedByMerge == year);
    }

    // =========================== end of merge =======================
    public List<ContactChannelDto> getTeacherChannels(long teacherId) {
        Teacher teacher = em.find(Teacher.class, teacherId);
        log.debug("Teacher got from DB");
        return teacher.getChannels().stream().map(ContactChannelDto::new).collect(toList());
    }

    @Transactional
    public void setTeacherChannels(long teacherId, List<ContactChannelDto> channelDtos) {
        List<ContactChannel> channels = new ArrayList<>();
        for (ContactChannelDto dto : channelDtos) {
            channels.add(new ContactChannel(dto.getType(), dto.getValue()));
        }
        Teacher teacher = teacherRepo.findOneById(teacherId);
        teacher.setChannels(channels); // TODO observe what happens
    }

    public Set<TeachingActivity> getAllActivities(long yearId) {
        StudentsYear year = em.find(StudentsYear.class, yearId); // TODO optimize
        Set<TeachingActivity> set = new HashSet<>();
        for (CourseActivity course : year.getCourses()) {
            set.add(course);
            for (StudentsGroup group : year.getGroups()) {
                set.addAll(group.getLabs());
            }
        }
        return set;
    }


    // TODO [HARD-CORE]
    public SubjectDto openUpdate(long subjectId) {
        anotherService.checkPermissionsOnSubject(subjectId);
        Subject subject = em.find(Subject.class, subjectId); // SELECT FOR UPDATE

        // TODO insert a line into a new entity SubjectLock storing the user id acquiring the edit lock + an expiration time = now+30 minutes
        // Tip: how to get current username: victor.training.jpa.magic.SpringSecurityAuditorAware.getCurrentAuditor
        // TODO on #updateSubject check the SubjectLock to be active and remove it after merging the incoming changes.
        // TODO in #openUpdate check to see whether there is no existing ACTIVE lock taken by any other user (ok to take it again by myself)
        // TODO remove @Version (optimistic locking) as it's not needed anymore
        // TODO protect against concurrent INSERT into SUBJECT_LOCKS by finding the subject with   at em.find(,, LockModeType.PESSIMISTIC_WRITE);
        return new SubjectDto(subject);
    }

    public void removeTeacherFromLab(long teacherId, long labId) {
        // TODO
    }
}
