package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.BoardListTestSamples.*;
import static com.mycompany.myapp.domain.BoardTestSamples.*;
import static com.mycompany.myapp.domain.CardTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BoardListTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoardList.class);
        BoardList boardList1 = getBoardListSample1();
        BoardList boardList2 = new BoardList();
        assertThat(boardList1).isNotEqualTo(boardList2);

        boardList2.setId(boardList1.getId());
        assertThat(boardList1).isEqualTo(boardList2);

        boardList2 = getBoardListSample2();
        assertThat(boardList1).isNotEqualTo(boardList2);
    }

    @Test
    void cardsTest() {
        BoardList boardList = getBoardListRandomSampleGenerator();
        Card cardBack = getCardRandomSampleGenerator();

        boardList.addCards(cardBack);
        assertThat(boardList.getCards()).containsOnly(cardBack);
        assertThat(cardBack.getBoardList()).isEqualTo(boardList);

        boardList.removeCards(cardBack);
        assertThat(boardList.getCards()).doesNotContain(cardBack);
        assertThat(cardBack.getBoardList()).isNull();

        boardList.cards(new HashSet<>(Set.of(cardBack)));
        assertThat(boardList.getCards()).containsOnly(cardBack);
        assertThat(cardBack.getBoardList()).isEqualTo(boardList);

        boardList.setCards(new HashSet<>());
        assertThat(boardList.getCards()).doesNotContain(cardBack);
        assertThat(cardBack.getBoardList()).isNull();
    }

    @Test
    void boardTest() {
        BoardList boardList = getBoardListRandomSampleGenerator();
        Board boardBack = getBoardRandomSampleGenerator();

        boardList.setBoard(boardBack);
        assertThat(boardList.getBoard()).isEqualTo(boardBack);

        boardList.board(null);
        assertThat(boardList.getBoard()).isNull();
    }
}
