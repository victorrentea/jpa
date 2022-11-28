package victor.training.jpa.perf;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UberEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String firstName, lastName, ibanCode, cnp, ssn, passportNumber;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
            return lastName;
    }
    //    @ManyToOne // cause +1 SELECT if SELECTing fro UberEntiyy, or +1 JOIN if uberRepo.findById(id)
//    private Country originCountry;

    private Long originCountryId; //!! Please leave the FK ON!!!!
    // Aggregates should not keep object referenes to eachother. only numbers.

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

    @Enumerated(EnumType.STRING)
    private Status status = Status.CREATED;

    enum Status {
        CREATED,DRAFT, SUBMITTED
    }

    public Status getStatus() {
        return status;
    }

    public UberEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UberEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Country getFiscalCountry() {
        return fiscalCountry;
    }

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

    public UberEntity setOriginCountryId(Long originCountryId) {
        this.originCountryId = originCountryId;
        return this;
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
//    @ManyToOne
//    private Continent continent;
    private Country() {
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
    private Scope() {
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
    private User() {
    }
    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}