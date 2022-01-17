package victor.training.jpa.perf.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;

@Setter
@Getter
@Entity
@NamedEntityGraphs({

@NamedEntityGraph(name = "Post.forCommenters",
    attributeNodes = @NamedAttributeNode(value = "comments", subgraph = "comment"),
    subgraphs = {@NamedSubgraph(name = "comment", attributeNodes = @NamedAttributeNode("user"))}),
})
public class Post{
    public enum PostType {
        THOUGHT,
        QUICK_TIP,
        BEST_PRACTICES,
        PHILOSOPHY;
    }
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String body;

    @Enumerated(STRING)
    private PostType postType;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "POST_ID")
    private Set<Comment> comments = new HashSet<>();

    private LocalDate publishDate;

    @ManyToOne
    private User author;

    @ManyToMany
    private Set<Tag> tags = new HashSet<>();

    public Post() {
    }

    public Post(String title) {
        this.title = title;
    }

    public Post addComment(Comment comment) {
        comments.add(comment);
        return this;
    }

    public boolean isHeavilyCommented() {
        return comments.size() > 10;
    }

    public Set<Comment> getComments() {
        return comments;
    }
}