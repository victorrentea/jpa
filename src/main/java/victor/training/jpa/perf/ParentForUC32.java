package victor.training.jpa.perf;

import java.util.Set;

public interface ParentForUC32 {
  String getName();

  Long getId();

  Set<? extends ChildForUC32> getChildren();
}
