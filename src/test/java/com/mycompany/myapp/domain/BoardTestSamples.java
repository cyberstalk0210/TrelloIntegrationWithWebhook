package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BoardTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Board getBoardSample1() {
        return new Board().id(1L).title("title1").trelloId("trelloId1").createdBy("createdBy1");
    }

    public static Board getBoardSample2() {
        return new Board().id(2L).title("title2").trelloId("trelloId2").createdBy("createdBy2");
    }

    public static Board getBoardRandomSampleGenerator() {
        return new Board()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .trelloId(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString());
    }
}
