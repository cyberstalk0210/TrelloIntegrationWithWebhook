package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CheckItemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CheckItem getCheckItemSample1() {
        return new CheckItem().id(1L).checkItemId("checkItemId1").name("name1");
    }

    public static CheckItem getCheckItemSample2() {
        return new CheckItem().id(2L).checkItemId("checkItemId2").name("name2");
    }

    public static CheckItem getCheckItemRandomSampleGenerator() {
        return new CheckItem().id(longCount.incrementAndGet()).checkItemId(UUID.randomUUID().toString()).name(UUID.randomUUID().toString());
    }
}
