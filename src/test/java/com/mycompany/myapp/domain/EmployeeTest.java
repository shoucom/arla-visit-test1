package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EmployeeTestSamples.*;
import static com.mycompany.myapp.domain.OfficeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EmployeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employee.class);
        Employee employee1 = getEmployeeSample1();
        Employee employee2 = new Employee();
        assertThat(employee1).isNotEqualTo(employee2);

        employee2.setId(employee1.getId());
        assertThat(employee1).isEqualTo(employee2);

        employee2 = getEmployeeSample2();
        assertThat(employee1).isNotEqualTo(employee2);
    }

    @Test
    void officeTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Office officeBack = getOfficeRandomSampleGenerator();

        employee.addOffice(officeBack);
        assertThat(employee.getOffices()).containsOnly(officeBack);

        employee.removeOffice(officeBack);
        assertThat(employee.getOffices()).doesNotContain(officeBack);

        employee.offices(new HashSet<>(Set.of(officeBack)));
        assertThat(employee.getOffices()).containsOnly(officeBack);

        employee.setOffices(new HashSet<>());
        assertThat(employee.getOffices()).doesNotContain(officeBack);
    }
}
