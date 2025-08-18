package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LabelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Label getLabelSample1() {
        return new Label().id(1L).name("name1").color("color1").trelloId("trelloId1");
    }

    public static Label getLabelSample2() {
        return new Label().id(2L).name("name2").color("color2").trelloId("trelloId2");
    }

    public static Label getLabelRandomSampleGenerator() {
        return new Label()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .color(UUID.randomUUID().toString())
            .trelloId(UUID.randomUUID().toString());
    }
}
