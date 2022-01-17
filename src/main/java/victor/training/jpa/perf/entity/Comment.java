package victor.training.jpa.perf.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "COMMENTS")
@Entity
@Getter
@Setter
public class Comment implements Comment1 {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String text;
    @ManyToOne
    private User user;

    private Comment() {
    }

    public Comment(String title) {
        this.title = title;
    }
}
