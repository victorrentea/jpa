package victor.training.jpa.app;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DeCeSaNUImplemHashCodeEqualsPeEntitati_darTotusiSaFolosestiHashSet_de_Copii {

    public static void main(String[] args) {

        Set<Copil> puiiMei = new HashSet<>();
        Copil copil1 = new Copil("Emma");
        System.out.println("hash(copil) = " + copil1.hashCode());
        puiiMei.add(copil1);

        System.out.println("Adolescenta!!💀");
        copil1.setName("Emma-Simona");

//        childRepo.save(copil1); // pe copil1.id tocmai s-a pus un nou ID care i-a schimat hashul pentruca @Data


        puiiMei.add(copil1);
        System.out.println(puiiMei);

        System.out.println("hash(copil) = " + copil1.hashCode());
        System.out.println("Oare mai e Emma in copii mei ? " + puiiMei.contains(copil1));
    }
}
@Data
@Entity
class Copil {
    @Id
    @GeneratedValue
    private Long id;
    private String name; // REGULA! datele pe care calculezi hash/equals tre sa NU se modifice pe durata vietii ob

    public Copil(String name) {
        this.name = name;
    }

//    public void setName(String name) {
//        this.name = name;
//    }

//        @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Copil copil = (Copil) o;
//        return Objects.equals(name, copil.name);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name);
//    }

    @Override
    public String toString() {
        return "Copil{" +
               "name='" + name + '\'' +
               '}';
    }
}