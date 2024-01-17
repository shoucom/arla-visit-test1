package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VisitorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Visitor getVisitorSample1() {
        return new Visitor().id(1L).firstName("firstName1").lastName("lastName1").email("email1").mobileNumber(1L).company("company1");
    }

    public static Visitor getVisitorSample2() {
        return new Visitor().id(2L).firstName("firstName2").lastName("lastName2").email("email2").mobileNumber(2L).company("company2");
    }

    public static Visitor getVisitorRandomSampleGenerator() {
        return new Visitor()
            .id(longCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .mobileNumber(longCount.incrementAndGet())
            .company(UUID.randomUUID().toString());
    }
}
