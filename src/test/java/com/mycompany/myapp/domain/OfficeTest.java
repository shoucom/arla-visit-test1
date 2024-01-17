package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EmployeeTestSamples.*;
import static com.mycompany.myapp.domain.OfficeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OfficeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Office.class);
        Office office1 = getOfficeSample1();
        Office office2 = new Office();
        assertThat(office1).isNotEqualTo(office2);

        office2.setId(office1.getId());
        assertThat(office1).isEqualTo(office2);

        office2 = getOfficeSample2();
        assertThat(office1).isNotEqualTo(office2);
    }

    @Test
    void employeeTest() throws Exception {
        Office office = getOfficeRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        office.addEmployee(employeeBack);
        assertThat(office.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getOffices()).containsOnly(office);

        office.removeEmployee(employeeBack);
        assertThat(office.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getOffices()).doesNotContain(office);

        office.employees(new HashSet<>(Set.of(employeeBack)));
        assertThat(office.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getOffices()).containsOnly(office);

        office.setEmployees(new HashSet<>());
        assertThat(office.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getOffices()).doesNotContain(office);
    }
}
