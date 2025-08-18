package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AttachmentTestSamples.*;
import static com.mycompany.myapp.domain.BoardListTestSamples.*;
import static com.mycompany.myapp.domain.BoardTestSamples.*;
import static com.mycompany.myapp.domain.CardTestSamples.*;
import static com.mycompany.myapp.domain.CommentTestSamples.*;
import static com.mycompany.myapp.domain.LabelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Card.class);
        Card card1 = getCardSample1();
        Card card2 = new Card();
        assertThat(card1).isNotEqualTo(card2);

        card2.setId(card1.getId());
        assertThat(card1).isEqualTo(card2);

        card2 = getCardSample2();
        assertThat(card1).isNotEqualTo(card2);
    }

    @Test
    void commentsTest() {
        Card card = getCardRandomSampleGenerator();
        Comment commentBack = getCommentRandomSampleGenerator();

        card.addComments(commentBack);
        assertThat(card.getComments()).containsOnly(commentBack);
        assertThat(commentBack.getCard()).isEqualTo(card);

        card.removeComments(commentBack);
        assertThat(card.getComments()).doesNotContain(commentBack);
        assertThat(commentBack.getCard()).isNull();

        card.comments(new HashSet<>(Set.of(commentBack)));
        assertThat(card.getComments()).containsOnly(commentBack);
        assertThat(commentBack.getCard()).isEqualTo(card);

        card.setComments(new HashSet<>());
        assertThat(card.getComments()).doesNotContain(commentBack);
        assertThat(commentBack.getCard()).isNull();
    }

    @Test
    void attachmentsTest() {
        Card card = getCardRandomSampleGenerator();
        Attachment attachmentBack = getAttachmentRandomSampleGenerator();

        card.addAttachments(attachmentBack);
        assertThat(card.getAttachments()).containsOnly(attachmentBack);
        assertThat(attachmentBack.getCard()).isEqualTo(card);

        card.removeAttachments(attachmentBack);
        assertThat(card.getAttachments()).doesNotContain(attachmentBack);
        assertThat(attachmentBack.getCard()).isNull();

        card.attachments(new HashSet<>(Set.of(attachmentBack)));
        assertThat(card.getAttachments()).containsOnly(attachmentBack);
        assertThat(attachmentBack.getCard()).isEqualTo(card);

        card.setAttachments(new HashSet<>());
        assertThat(card.getAttachments()).doesNotContain(attachmentBack);
        assertThat(attachmentBack.getCard()).isNull();
    }

    @Test
    void labelsTest() {
        Card card = getCardRandomSampleGenerator();
        Label labelBack = getLabelRandomSampleGenerator();

        card.addLabels(labelBack);
        assertThat(card.getLabels()).containsOnly(labelBack);

        card.removeLabels(labelBack);
        assertThat(card.getLabels()).doesNotContain(labelBack);

        card.labels(new HashSet<>(Set.of(labelBack)));
        assertThat(card.getLabels()).containsOnly(labelBack);

        card.setLabels(new HashSet<>());
        assertThat(card.getLabels()).doesNotContain(labelBack);
    }

    @Test
    void boardTest() {
        Card card = getCardRandomSampleGenerator();
        Board boardBack = getBoardRandomSampleGenerator();

        card.setBoard(boardBack);
        assertThat(card.getBoard()).isEqualTo(boardBack);

        card.board(null);
        assertThat(card.getBoard()).isNull();
    }

    @Test
    void boardListTest() {
        Card card = getCardRandomSampleGenerator();
        BoardList boardListBack = getBoardListRandomSampleGenerator();

        card.setBoardList(boardListBack);
        assertThat(card.getBoardList()).isEqualTo(boardListBack);

        card.boardList(null);
        assertThat(card.getBoardList()).isNull();
    }
}
