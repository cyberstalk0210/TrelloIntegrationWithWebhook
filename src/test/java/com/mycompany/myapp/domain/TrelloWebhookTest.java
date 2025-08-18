package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.TrelloWebhookTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrelloWebhookTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrelloWebhook.class);
        TrelloWebhook trelloWebhook1 = getTrelloWebhookSample1();
        TrelloWebhook trelloWebhook2 = new TrelloWebhook();
        assertThat(trelloWebhook1).isNotEqualTo(trelloWebhook2);

        trelloWebhook2.setId(trelloWebhook1.getId());
        assertThat(trelloWebhook1).isEqualTo(trelloWebhook2);

        trelloWebhook2 = getTrelloWebhookSample2();
        assertThat(trelloWebhook1).isNotEqualTo(trelloWebhook2);
    }
}
