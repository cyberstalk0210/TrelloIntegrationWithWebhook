package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.BoardListAsserts.*;
import static com.mycompany.myapp.domain.BoardListTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardListMapperTest {

    private BoardListMapper boardListMapper;

    @BeforeEach
    void setUp() {
        boardListMapper = new BoardListMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBoardListSample1();
        var actual = boardListMapper.toEntity(boardListMapper.toDto(expected));
        assertBoardListAllPropertiesEquals(expected, actual);
    }
}
