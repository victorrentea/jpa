package victor.training.jpa.app.facade.dto;

import java.time.format.DateTimeFormatter;

import lombok.Data;
import lombok.NoArgsConstructor;
import victor.training.jpa.app.entity.Subject;

@Data
@NoArgsConstructor
public class SubjectDto {
	private String holderTeacherName;
	private Long id;
	private String name;
	private Long holderTeacherId;
	private String lastModifiedByUsername;
	private String lastModifiedDate;
	
	public SubjectDto(Subject subject) {
		id = subject.getId();
		name = subject.getName();
		if (subject.getHolderTeacher() != null) {
			holderTeacherId = subject.getHolderTeacher().getId();
			holderTeacherName = subject.getHolderTeacher().getName();
		}
		lastModifiedByUsername = subject.getLastModifiedBy();
		if (subject.getLastModifiedDate()!= null) {
			lastModifiedDate = subject.getLastModifiedDate().format(DateTimeFormatter.ISO_DATE_TIME);
		}
	}
}
