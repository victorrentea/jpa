package victor.training.jpa.app.domain.entity;

import javax.persistence.*;

@Entity
@SequenceGenerator(name="PeriutaSeq", sequenceName = "PERIUTA_SEQ")
public class Periuta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PeriutaSeq")
    private Long id;
    private String marca;


    public Periuta() {

    }

    public Periuta(String marca) {
        this.marca = marca;
    }

    public String getMarca() {
        return marca;
    }
}
