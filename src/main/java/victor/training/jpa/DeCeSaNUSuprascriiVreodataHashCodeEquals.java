package victor.training.jpa;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DeCeSaNUSuprascriiVreodataHashCodeEquals {
    public static void main(String[] args) {

        Set<Copil> puiiMei = new HashSet<>();
        Copil childOne = new Copil(null);
        puiiMei.add(childOne);

        System.out.println("hash(copil) = " + childOne.hashCode());
        System.out.println(puiiMei.contains(childOne));

        childOne.setId(1L);
        System.out.println("hash(copil) = " + childOne.hashCode());
        System.out.println(puiiMei.contains(childOne));


    }
}


class Copil {
    private Long id;

    Copil(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Copil copil = (Copil) o;
        return Objects.equals(id, copil.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}