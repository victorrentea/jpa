package victor.training.jpa.perf.entity;

import java.util.List;
import java.util.Set;

public interface Post1 {
   String getTitle();
   Set<? extends Comment1> getComments();
}
