package victor.training.jpa.app.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;


@Getter
@Setter
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class Subject  {
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private boolean active;
	
	@ManyToOne
	private Teacher holderTeacher;
	
	@OneToMany(mappedBy="subject")
	private List<TeachingActivity> activities = new ArrayList<>();
	
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;
	
	@LastModifiedBy
	private String lastModifiedBy;

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Subject() {
	}

	void setHolderTeacher(Teacher holderTeacher) {
		this.holderTeacher = holderTeacher;
	}

	public Subject(String name) {
		this.name = name;
	}
}
