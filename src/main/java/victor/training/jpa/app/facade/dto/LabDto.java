package victor.training.jpa.app.facade.dto;

import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.TreeSet;

import lombok.Data;
import lombok.NoArgsConstructor;
import victor.training.jpa.app.entity.LabActivity;
import victor.training.jpa.app.entity.Teacher;

@NoArgsConstructor
@Data
public class LabDto {

	private Long id;
	private String subjectName;
	private TimeSlotDto timeSlot;
	private String groupCode;
	private Set<String> teacherNames = new TreeSet<>();
	private String lastModifiedByUsername;
	private String lastModifiedDate;
	
	public LabDto(LabActivity lab) {
		id = lab.getId();
		subjectName = lab.getSubject().getName();
		timeSlot =  new TimeSlotDto(lab.getDayOfWeek(), lab.getDurationInHours(), lab.getDurationInHours(), lab.getRoomId());
		if (lab.getGroup() != null) {
			groupCode = lab.getGroup().getCode();
		}
		for (Teacher teacher : lab.getTeachers()) {
			teacherNames.add(teacher.getName());
		}
		lastModifiedByUsername = lab.getLastModifiedBy();
		if (lab.getLastModifiedDate()!= null) {
			lastModifiedDate = lab.getLastModifiedDate().format(DateTimeFormatter.ISO_DATE_TIME);
		}
	}
}
