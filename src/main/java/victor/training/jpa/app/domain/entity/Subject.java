package victor.training.jpa.app.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import victor.training.jpa.app.util.MyTrackingEntityListener;
import victor.training.jpa.app.util.MyTrackingEntityListener.Trackable;


@Getter
@Setter
@Entity
@EntityListeners(MyTrackingEntityListener.class) // SOLUTION
//public class Subject { // INITIAL
public class Subject implements Trackable { // SOLUTION
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

	public Subject(String name) {
		this.name = name;
	}
}
