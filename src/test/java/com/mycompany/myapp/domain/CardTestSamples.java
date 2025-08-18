package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CardTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Card getCardSample1() {
        return new Card().id(1L).title("title1").position(1).trelloId("trelloId1");
    }

    public static Card getCardSample2() {
        return new Card().id(2L).title("title2").position(2).trelloId("trelloId2");
    }

    public static Card getCardRandomSampleGenerator() {
        return new Card()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .position(intCount.incrementAndGet())
            .trelloId(UUID.randomUUID().toString());
    }
}
