package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.BoardListTestSamples.*;
import static com.mycompany.myapp.domain.BoardTestSamples.*;
import static com.mycompany.myapp.domain.CardTestSamples.*;
import static com.mycompany.myapp.domain.WorkspaceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Board.class);
        Board board1 = getBoardSample1();
        Board board2 = new Board();
        assertThat(board1).isNotEqualTo(board2);

        board2.setId(board1.getId());
        assertThat(board1).isEqualTo(board2);

        board2 = getBoardSample2();
        assertThat(board1).isNotEqualTo(board2);
    }

    @Test
    void listsTest() {
        Board board = getBoardRandomSampleGenerator();
        BoardList boardListBack = getBoardListRandomSampleGenerator();

        board.addLists(boardListBack);
        assertThat(board.getLists()).containsOnly(boardListBack);
        assertThat(boardListBack.getBoard()).isEqualTo(board);

        board.removeLists(boardListBack);
        assertThat(board.getLists()).doesNotContain(boardListBack);
        assertThat(boardListBack.getBoard()).isNull();

        board.lists(new HashSet<>(Set.of(boardListBack)));
        assertThat(board.getLists()).containsOnly(boardListBack);
        assertThat(boardListBack.getBoard()).isEqualTo(board);

        board.setLists(new HashSet<>());
        assertThat(board.getLists()).doesNotContain(boardListBack);
        assertThat(boardListBack.getBoard()).isNull();
    }

    @Test
    void cardsTest() {
        Board board = getBoardRandomSampleGenerator();
        Card cardBack = getCardRandomSampleGenerator();

        board.addCards(cardBack);
        assertThat(board.getCards()).containsOnly(cardBack);
        assertThat(cardBack.getBoard()).isEqualTo(board);

        board.removeCards(cardBack);
        assertThat(board.getCards()).doesNotContain(cardBack);
        assertThat(cardBack.getBoard()).isNull();

        board.cards(new HashSet<>(Set.of(cardBack)));
        assertThat(board.getCards()).containsOnly(cardBack);
        assertThat(cardBack.getBoard()).isEqualTo(board);

        board.setCards(new HashSet<>());
        assertThat(board.getCards()).doesNotContain(cardBack);
        assertThat(cardBack.getBoard()).isNull();
    }

    @Test
    void workspaceTest() {
        Board board = getBoardRandomSampleGenerator();
        Workspace workspaceBack = getWorkspaceRandomSampleGenerator();

        board.setWorkspace(workspaceBack);
        assertThat(board.getWorkspace()).isEqualTo(workspaceBack);

        board.workspace(null);
        assertThat(board.getWorkspace()).isNull();
    }
}
