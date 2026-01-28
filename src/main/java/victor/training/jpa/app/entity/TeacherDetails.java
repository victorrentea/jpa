package victor.training.jpa.app.entity;

import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import victor.training.jpa.app.entity.converter.MoreTeacherDetailsConverter;

import jakarta.persistence.*;

import java.sql.Blob;
import java.util.List;

@Getter
@Setter
@Entity
public class TeacherDetails {
	
	@Id
	@GeneratedValue
	private Long id;

	@Lob
	@Basic(fetch = FetchType.LAZY) // DOES NOT work anymore with spring boot
	private String cv;

//	@Lob
//	private byte[] cvBytes;

//	private Blob cvBlob; permite scriere/citire cu input/output stream fara sa ai tot in memorie
	// PreparedStatement ps;
	// ps.setParam(4, blob);

	@PreDestroy
	public void method() {
		// in loc de trigger in DB
		// cum ajung in spring via o metoda static 🤢🤢

//		vreau sa trimit notificari prin websockets clientilor ca am sters copilul
		// si abia apoi sa sterg parintele =>
		// tine logica asta intr-un @Service in loc de @PreDestroy
	}
}

