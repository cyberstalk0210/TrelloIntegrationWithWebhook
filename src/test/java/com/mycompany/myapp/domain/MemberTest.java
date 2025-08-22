package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CardTestSamples.*;
import static com.mycompany.myapp.domain.MemberTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Member.class);
        Member member1 = getMemberSample1();
        Member member2 = new Member();
        assertThat(member1).isNotEqualTo(member2);

        member2.setId(member1.getId());
        assertThat(member1).isEqualTo(member2);

        member2 = getMemberSample2();
        assertThat(member1).isNotEqualTo(member2);
    }

    @Test
    void cardsTest() {
        Member member = getMemberRandomSampleGenerator();
        Card cardBack = getCardRandomSampleGenerator();

        member.addCards(cardBack);
        assertThat(member.getCards()).containsOnly(cardBack);
        assertThat(cardBack.getMembers()).containsOnly(member);

        member.removeCards(cardBack);
        assertThat(member.getCards()).doesNotContain(cardBack);
        assertThat(cardBack.getMembers()).doesNotContain(member);

        member.cards(new HashSet<>(Set.of(cardBack)));
        assertThat(member.getCards()).containsOnly(cardBack);
        assertThat(cardBack.getMembers()).containsOnly(member);

        member.setCards(new HashSet<>());
        assertThat(member.getCards()).doesNotContain(cardBack);
        assertThat(cardBack.getMembers()).doesNotContain(member);
    }
}
