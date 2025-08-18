package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BoardListDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoardListDTO.class);
        BoardListDTO boardListDTO1 = new BoardListDTO();
        boardListDTO1.setId(1L);
        BoardListDTO boardListDTO2 = new BoardListDTO();
        assertThat(boardListDTO1).isNotEqualTo(boardListDTO2);
        boardListDTO2.setId(boardListDTO1.getId());
        assertThat(boardListDTO1).isEqualTo(boardListDTO2);
        boardListDTO2.setId(2L);
        assertThat(boardListDTO1).isNotEqualTo(boardListDTO2);
        boardListDTO1.setId(null);
        assertThat(boardListDTO1).isNotEqualTo(boardListDTO2);
    }
}
