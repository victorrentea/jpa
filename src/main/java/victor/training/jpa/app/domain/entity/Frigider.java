package victor.training.jpa.app.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Frigider extends AbstractEntity {

    @ManyToOne
    private MarcaFrigider marca;
}
