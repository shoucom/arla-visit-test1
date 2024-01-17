package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EmployeeTestSamples.*;
import static com.mycompany.myapp.domain.OfficeTestSamples.*;
import static com.mycompany.myapp.domain.VisitTestSamples.*;
import static com.mycompany.myapp.domain.VisitorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VisitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Visit.class);
        Visit visit1 = getVisitSample1();
        Visit visit2 = new Visit();
        assertThat(visit1).isNotEqualTo(visit2);

        visit2.setId(visit1.getId());
        assertThat(visit1).isEqualTo(visit2);

        visit2 = getVisitSample2();
        assertThat(visit1).isNotEqualTo(visit2);
    }

    @Test
    void visitorTest() throws Exception {
        Visit visit = getVisitRandomSampleGenerator();
        Visitor visitorBack = getVisitorRandomSampleGenerator();

        visit.setVisitor(visitorBack);
        assertThat(visit.getVisitor()).isEqualTo(visitorBack);

        visit.visitor(null);
        assertThat(visit.getVisitor()).isNull();
    }

    @Test
    void officeTest() throws Exception {
        Visit visit = getVisitRandomSampleGenerator();
        Office officeBack = getOfficeRandomSampleGenerator();

        visit.setOffice(officeBack);
        assertThat(visit.getOffice()).isEqualTo(officeBack);

        visit.office(null);
        assertThat(visit.getOffice()).isNull();
    }

    @Test
    void hostTest() throws Exception {
        Visit visit = getVisitRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        visit.setHost(employeeBack);
        assertThat(visit.getHost()).isEqualTo(employeeBack);

        visit.host(null);
        assertThat(visit.getHost()).isNull();
    }
}
