package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CardTestSamples.*;
import static com.mycompany.myapp.domain.LabelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class LabelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Label.class);
        Label label1 = getLabelSample1();
        Label label2 = new Label();
        assertThat(label1).isNotEqualTo(label2);

        label2.setId(label1.getId());
        assertThat(label1).isEqualTo(label2);

        label2 = getLabelSample2();
        assertThat(label1).isNotEqualTo(label2);
    }

    @Test
    void cardsTest() {
        Label label = getLabelRandomSampleGenerator();
        Card cardBack = getCardRandomSampleGenerator();

        label.addCards(cardBack);
        assertThat(label.getCards()).containsOnly(cardBack);
        assertThat(cardBack.getLabels()).containsOnly(label);

        label.removeCards(cardBack);
        assertThat(label.getCards()).doesNotContain(cardBack);
        assertThat(cardBack.getLabels()).doesNotContain(label);

        label.cards(new HashSet<>(Set.of(cardBack)));
        assertThat(label.getCards()).containsOnly(cardBack);
        assertThat(cardBack.getLabels()).containsOnly(label);

        label.setCards(new HashSet<>());
        assertThat(label.getCards()).doesNotContain(cardBack);
        assertThat(cardBack.getLabels()).doesNotContain(label);
    }
}
