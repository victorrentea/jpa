package victor.training.jpa.perf;

import victor.training.jpa.perf.entity.Post;

public class PostDto {
   public Long id;
   public String title;
   public String author;
//   public int commentCount;

   public Long getId() {
      return id;
   }

   public PostDto(Post entity) {
      title = entity.getTitle();
      author = entity.getAuthor().getUsername();
//      commentCount = entity.getComments().size();
   }

   public PostDto(Long id, String title, String author) {
      this.id = id;
      this.title = title;
      this.author = author;
   }
}
