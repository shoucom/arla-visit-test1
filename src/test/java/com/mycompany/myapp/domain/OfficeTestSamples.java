package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OfficeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Office getOfficeSample1() {
        return new Office().id(1L).name("name1").address("address1").phone(1L).timeZone("timeZone1").wifiPassword("wifiPassword1");
    }

    public static Office getOfficeSample2() {
        return new Office().id(2L).name("name2").address("address2").phone(2L).timeZone("timeZone2").wifiPassword("wifiPassword2");
    }

    public static Office getOfficeRandomSampleGenerator() {
        return new Office()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .phone(longCount.incrementAndGet())
            .timeZone(UUID.randomUUID().toString())
            .wifiPassword(UUID.randomUUID().toString());
    }
}
