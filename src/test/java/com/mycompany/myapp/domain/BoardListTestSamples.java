package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class BoardListTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static BoardList getBoardListSample1() {
        return new BoardList().id(1L).name("name1").position(1).trelloId("trelloId1");
    }

    public static BoardList getBoardListSample2() {
        return new BoardList().id(2L).name("name2").position(2).trelloId("trelloId2");
    }

    public static BoardList getBoardListRandomSampleGenerator() {
        return new BoardList()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .position(intCount.incrementAndGet())
            .trelloId(UUID.randomUUID().toString());
    }
}
