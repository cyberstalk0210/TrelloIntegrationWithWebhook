package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CheckItemTestSamples.getCheckItemSample1;
import static com.mycompany.myapp.domain.CheckItemTestSamples.getCheckItemSample2;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckItem.class);
        CheckItem checkItem1 = getCheckItemSample1();
        CheckItem checkItem2 = new CheckItem();
        assertThat(checkItem1).isNotEqualTo(checkItem2);

        checkItem2.setId(checkItem1.getId());
        assertThat(checkItem1).isEqualTo(checkItem2);

        checkItem2 = getCheckItemSample2();
        assertThat(checkItem1).isNotEqualTo(checkItem2);
    }
    //    @Test
    //    void dataTest() {
    //        CheckItem checkItem = getCheckItemRandomSampleGenerator();
    //        TextData textDataBack = getTextDataRandomSampleGenerator();
    //
    //        checkItem.setData(textDataBack);
    //        assertThat(checkItem.getData()).isEqualTo(textDataBack);
    //
    //        checkItem.data(null);
    //        assertThat(checkItem.getData()).isNull();
    //    }

    //    @Test
    //    void checkListTest() {
    //        CheckItem checkItem = getCheckItemRandomSampleGenerator();
    //        CheckList checkListBack = getCheckListRandomSampleGenerator();
    //
    //        checkItem.setCheckList(checkListBack);
    //        assertThat(checkItem.getCheckList()).isEqualTo(checkListBack);
    //
    //        checkItem.checkList(null);
    //        assertThat(checkItem.getCheckList()).isNull();
    //    }
}
