package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.LabelAsserts.*;
import static com.mycompany.myapp.domain.LabelTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LabelMapperTest {

    private LabelMapper labelMapper;

    @BeforeEach
    void setUp() {
        labelMapper = new LabelMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getLabelSample1();
        var actual = labelMapper.toEntity(labelMapper.toDto(expected));
        assertLabelAllPropertiesEquals(expected, actual);
    }
}
