package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Visit.
 */
@Entity
@Table(name = "visit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "in_time")
    private Instant inTime;

    @Column(name = "out_time")
    private Instant outTime;

    @Column(name = "car_registration_number")
    private String carRegistrationNumber;

    @Column(name = "car_parking_number")
    private String carParkingNumber;

    @Column(name = "message_to_host")
    private String messageToHost;

    @ManyToOne(optional = false)
    @NotNull
    private Visitor visitor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "employees" }, allowSetters = true)
    private Office office;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "offices" }, allowSetters = true)
    private Employee host;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Visit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInTime() {
        return this.inTime;
    }

    public Visit inTime(Instant inTime) {
        this.setInTime(inTime);
        return this;
    }

    public void setInTime(Instant inTime) {
        this.inTime = inTime;
    }

    public Instant getOutTime() {
        return this.outTime;
    }

    public Visit outTime(Instant outTime) {
        this.setOutTime(outTime);
        return this;
    }

    public void setOutTime(Instant outTime) {
        this.outTime = outTime;
    }

    public String getCarRegistrationNumber() {
        return this.carRegistrationNumber;
    }

    public Visit carRegistrationNumber(String carRegistrationNumber) {
        this.setCarRegistrationNumber(carRegistrationNumber);
        return this;
    }

    public void setCarRegistrationNumber(String carRegistrationNumber) {
        this.carRegistrationNumber = carRegistrationNumber;
    }

    public String getCarParkingNumber() {
        return this.carParkingNumber;
    }

    public Visit carParkingNumber(String carParkingNumber) {
        this.setCarParkingNumber(carParkingNumber);
        return this;
    }

    public void setCarParkingNumber(String carParkingNumber) {
        this.carParkingNumber = carParkingNumber;
    }

    public String getMessageToHost() {
        return this.messageToHost;
    }

    public Visit messageToHost(String messageToHost) {
        this.setMessageToHost(messageToHost);
        return this;
    }

    public void setMessageToHost(String messageToHost) {
        this.messageToHost = messageToHost;
    }

    public Visitor getVisitor() {
        return this.visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Visit visitor(Visitor visitor) {
        this.setVisitor(visitor);
        return this;
    }

    public Office getOffice() {
        return this.office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Visit office(Office office) {
        this.setOffice(office);
        return this;
    }

    public Employee getHost() {
        return this.host;
    }

    public void setHost(Employee employee) {
        this.host = employee;
    }

    public Visit host(Employee employee) {
        this.setHost(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Visit)) {
            return false;
        }
        return getId() != null && getId().equals(((Visit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Visit{" +
            "id=" + getId() +
            ", inTime='" + getInTime() + "'" +
            ", outTime='" + getOutTime() + "'" +
            ", carRegistrationNumber='" + getCarRegistrationNumber() + "'" +
            ", carParkingNumber='" + getCarParkingNumber() + "'" +
            ", messageToHost='" + getMessageToHost() + "'" +
            "}";
    }
}
