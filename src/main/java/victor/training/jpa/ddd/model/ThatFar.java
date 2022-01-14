package victor.training.jpa.ddd.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ThatFar {
   public static void main(String[] args) {
      Child childPoc = new Child("Emma");

      Set<Child> children = new HashSet<>();

      children.add(childPoc);

      System.out.println(children.contains(childPoc));

      childPoc.setName("Emma-Simona");

      System.out.println(children.contains(childPoc));

   }
}

class Child {
private String name;

   public Child(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }

   public Child setName(String name) {
      this.name = name;
      return this;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Child child = (Child) o;
      return Objects.equals(name, child.name);
   }

   @Override
   public int hashCode() {
      return Objects.hash(name);
   }
}
