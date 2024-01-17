package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Language;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Office.
 */
@Entity
@Table(name = "office")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Office implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private Long phone;

    @Column(name = "time_zone")
    private String timeZone;

    @Column(name = "wifi_password")
    private String wifiPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "offices")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "offices" }, allowSetters = true)
    private Set<Employee> employees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Office id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Office name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public Office address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPhone() {
        return this.phone;
    }

    public Office phone(Long phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public Office timeZone(String timeZone) {
        this.setTimeZone(timeZone);
        return this;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getWifiPassword() {
        return this.wifiPassword;
    }

    public Office wifiPassword(String wifiPassword) {
        this.setWifiPassword(wifiPassword);
        return this;
    }

    public void setWifiPassword(String wifiPassword) {
        this.wifiPassword = wifiPassword;
    }

    public Language getLanguage() {
        return this.language;
    }

    public Office language(Language language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.removeOffice(this));
        }
        if (employees != null) {
            employees.forEach(i -> i.addOffice(this));
        }
        this.employees = employees;
    }

    public Office employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public Office addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getOffices().add(this);
        return this;
    }

    public Office removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.getOffices().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Office)) {
            return false;
        }
        return getId() != null && getId().equals(((Office) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Office{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", phone=" + getPhone() +
            ", timeZone='" + getTimeZone() + "'" +
            ", wifiPassword='" + getWifiPassword() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
