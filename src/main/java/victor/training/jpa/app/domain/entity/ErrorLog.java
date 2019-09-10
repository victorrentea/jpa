package victor.training.jpa.app.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ErrorLog extends AbstractEntity {

	@Column(name = "ERROR")
	private String message;

	public ErrorLog() {
	}
	
	public ErrorLog(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
