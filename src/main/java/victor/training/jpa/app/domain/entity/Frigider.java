package victor.training.jpa.app.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Frigider extends AbstractEntity {

    @ManyToOne
    private MarcaFrigider marca;

    @ManyToOne
    private Country2 country2;

    public void setCountry2(Country2 country2) {
        this.country2 = country2;
    }

    public Country2 getCountry2() {
        return country2;
    }
}
