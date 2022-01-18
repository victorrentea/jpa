package victor.training.jpa.perf.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "COMMENTS")
@Entity
@Getter
@Setter
@ToString
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String text;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "POST_ID")
//    Post post;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();

    private Comment() {
    }

    public Comment addTag(Tag tag) {
        tags.add(tag);
        return this;
    }

    public Comment(String title) {
        this.title = title;
    }
}
