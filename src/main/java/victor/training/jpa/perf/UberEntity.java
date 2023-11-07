package victor.training.jpa.perf;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class UberEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String address;
    private String city;
    private String ibanCode;
    private String cnp;
    private String ssn;
    private String passportNumber;
    @ManyToOne // naive OO modelling
    private Country originCountry;
    @ManyToOne
    private Country nationality;
    @ManyToOne
    private Country fiscalCountry;
    @ManyToOne
    private Country invoicingCountry;
    @ManyToOne
    private Scope scope;
    @ManyToOne
    private User createdBy;
}

