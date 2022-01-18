package victor.training.jpa.perf.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class UberEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String firstName;
    private String lastName;
    private String ibanCode;
    private String cnp;
    private String ssn;
    private String passportNumber;

    // naive OOP  : wake up we are not in highschool anymore. ANY @ManyToOne costs: JOIN (findById) or +1 select if your do SELECT FROM UberEntity
//    @ManyToOne
//    private Country originCountry; // static referential data

    private Long originCountryId; // + leave the FK in place.


    @ManyToOne(fetch = LAZY) // easy gain: -1 JOIN or -1 Query
    private Country nationality;
    @ManyToOne
    private Country fiscalCountry;
    @ManyToOne
    private Country invoicingCountry;
    @ManyToOne
    private Scope scope;
    @ManyToOne
    private User createdBy;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UberEntity setName(String name) {
        this.name = name;
        return this;
    }

    public UberEntity setNationality(Country nationality) {
        this.nationality = nationality;
        return this;
    }

    public UberEntity setFiscalCountry(Country fiscalCountry) {
        this.fiscalCountry = fiscalCountry;
        return this;
    }

    public UberEntity setInvoicingCountry(Country invoicingCountry) {
        this.invoicingCountry = invoicingCountry;
        return this;
    }

    public UberEntity setScope(Scope scope) {
        this.scope = scope;
        return this;
    }

    public UberEntity setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Long getOriginCountryId() {
        return originCountryId;
    }

    public UberEntity setOriginCountryId(Long originCountryId) {
        this.originCountryId = originCountryId;
        return this;
    }
}

