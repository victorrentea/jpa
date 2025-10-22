package victor.training.jpa.app;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

public class CumSaTiPierziCopilulLaMall {


  public static void main(String[] args) {
    Set<Copil> puiiMei = new HashSet<>();
    Copil poc = new Copil().setName("Emma");
    puiiMei.add(poc);
    System.out.println("Inainte de adolescenta: " + puiiMei.contains(poc));
//    poc.setName("Emma-Simona");
    poc.setId(42L);
    System.out.println(puiiMei.contains(poc));
  }
}

@Data
class Copil {
  private Long id;
  private String name;

}
