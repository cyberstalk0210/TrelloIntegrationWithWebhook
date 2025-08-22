package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CheckListTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CheckList getCheckListSample1() {
        return new CheckList().id(1L).checkListId("checkListId1").name("name1");
    }

    public static CheckList getCheckListSample2() {
        return new CheckList().id(2L).checkListId("checkListId2").name("name2");
    }

    public static CheckList getCheckListRandomSampleGenerator() {
        return new CheckList().id(longCount.incrementAndGet()).checkListId(UUID.randomUUID().toString()).name(UUID.randomUUID().toString());
    }
}
