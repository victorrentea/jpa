package victor.training.jpa.app.facade.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import victor.training.jpa.app.entity.Subject;
import victor.training.jpa.app.entity.TeachingActivity;
@Data
@NoArgsConstructor
public class SubjectWithActivitiesDto {

	private Long id;
	private String name;
	private Long holderTeacherId;
	private List<ActivityDto> activities = new ArrayList<>();

	public SubjectWithActivitiesDto(Subject subject) {
		id = subject.getId();
		name = subject.getName();
		if (subject.getHolderTeacher() != null) {
			holderTeacherId = subject.getHolderTeacher().getId();
		}
		for (TeachingActivity activity : subject.getActivities()) {
			activities.add(new ActivityDto(activity));
		}
	}
}
