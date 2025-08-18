package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TrelloWebhookTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TrelloWebhook getTrelloWebhookSample1() {
        return new TrelloWebhook()
            .id(1L)
            .trelloWebhookId("trelloWebhookId1")
            .idModel("idModel1")
            .callbackUrl("callbackUrl1")
            .secret("secret1");
    }

    public static TrelloWebhook getTrelloWebhookSample2() {
        return new TrelloWebhook()
            .id(2L)
            .trelloWebhookId("trelloWebhookId2")
            .idModel("idModel2")
            .callbackUrl("callbackUrl2")
            .secret("secret2");
    }

    public static TrelloWebhook getTrelloWebhookRandomSampleGenerator() {
        return new TrelloWebhook()
            .id(longCount.incrementAndGet())
            .trelloWebhookId(UUID.randomUUID().toString())
            .idModel(UUID.randomUUID().toString())
            .callbackUrl(UUID.randomUUID().toString())
            .secret(UUID.randomUUID().toString());
    }
}
