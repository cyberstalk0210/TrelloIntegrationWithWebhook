package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AttachmentTestSamples.*;
import static com.mycompany.myapp.domain.CardTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttachmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attachment.class);
        Attachment attachment1 = getAttachmentSample1();
        Attachment attachment2 = new Attachment();
        assertThat(attachment1).isNotEqualTo(attachment2);

        attachment2.setId(attachment1.getId());
        assertThat(attachment1).isEqualTo(attachment2);

        attachment2 = getAttachmentSample2();
        assertThat(attachment1).isNotEqualTo(attachment2);
    }

    @Test
    void cardTest() {
        Attachment attachment = getAttachmentRandomSampleGenerator();
        Card cardBack = getCardRandomSampleGenerator();

        attachment.setCard(cardBack);
        assertThat(attachment.getCard()).isEqualTo(cardBack);

        attachment.card(null);
        assertThat(attachment.getCard()).isNull();
    }
}
