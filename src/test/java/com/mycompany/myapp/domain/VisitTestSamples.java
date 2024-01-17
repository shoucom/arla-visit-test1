package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VisitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Visit getVisitSample1() {
        return new Visit()
            .id(1L)
            .carRegistrationNumber("carRegistrationNumber1")
            .carParkingNumber("carParkingNumber1")
            .messageToHost("messageToHost1");
    }

    public static Visit getVisitSample2() {
        return new Visit()
            .id(2L)
            .carRegistrationNumber("carRegistrationNumber2")
            .carParkingNumber("carParkingNumber2")
            .messageToHost("messageToHost2");
    }

    public static Visit getVisitRandomSampleGenerator() {
        return new Visit()
            .id(longCount.incrementAndGet())
            .carRegistrationNumber(UUID.randomUUID().toString())
            .carParkingNumber(UUID.randomUUID().toString())
            .messageToHost(UUID.randomUUID().toString());
    }
}
