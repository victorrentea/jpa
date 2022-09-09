package victor.training.jpa.perf;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UberEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String firstName, lastName, ibanCode, cnp, ssn, passportNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    private Country originCountry;
    @ManyToOne(fetch = FetchType.LAZY)
    private Country nationality;
    @ManyToOne(fetch = FetchType.LAZY)
    private Country fiscalCountry;
    @ManyToOne(fetch = FetchType.LAZY)
    private Country invoicingCountry;
    @ManyToOne(fetch = FetchType.LAZY)
    private Scope scope;
    @ManyToOne(fetch = FetchType.LAZY)
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

    public UberEntity setOriginCountry(Country originCountry) {
        this.originCountry = originCountry;
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

    public Country getOriginCountry() {
        return originCountry;
    }
}

@Entity
class Country {
    @Id
    private Long id;
    private String name;
    private String region;
    private String continent;
    private int population;
    protected Country() {
    }
    public Country(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
@Entity
class Scope {
    @Id
    private Long id;
    private String name;
    protected Scope() {
    }
    public Scope(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

@Entity
@Table(name = "USERS")
class User {
    @Id
    private Long id;
    private String name;
    protected User() {
    }
    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}