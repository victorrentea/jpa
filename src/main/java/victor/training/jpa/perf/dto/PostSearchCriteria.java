package victor.training.jpa.perf.dto;

import victor.training.jpa.perf.entity.Post.PostType;

public class PostSearchCriteria {
   public String title;
   public PostType postType;
   public boolean havingComments;
}
