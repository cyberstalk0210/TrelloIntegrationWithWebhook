package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CheckItemTestSamples.*;
import static com.mycompany.myapp.domain.CheckListTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CheckListTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckList.class);
        CheckList checkList1 = getCheckListSample1();
        CheckList checkList2 = new CheckList();
        assertThat(checkList1).isNotEqualTo(checkList2);

        checkList2.setId(checkList1.getId());
        assertThat(checkList1).isEqualTo(checkList2);

        checkList2 = getCheckListSample2();
        assertThat(checkList1).isNotEqualTo(checkList2);
    }
    //    @Test
    //    void checkItemTest() {
    //        CheckList checkList = getCheckListRandomSampleGenerator();
    //        CheckItem checkItemBack = getCheckItemRandomSampleGenerator();
    //
    //        checkList.addCheckItem(checkItemBack);
    //        assertThat(checkList.getCheckItems()).containsOnly(checkItemBack);
    //        assertThat(checkItemBack.getCheckList()).isEqualTo(checkList);
    //
    //        checkList.removeCheckItem(checkItemBack);
    //        assertThat(checkList.getCheckItems()).doesNotContain(checkItemBack);
    //        assertThat(checkItemBack.getCheckList()).isNull();
    //
    //        checkList.checkItems(new HashSet<>(Set.of(checkItemBack)));
    //        assertThat(checkList.getCheckItems()).containsOnly(checkItemBack);
    //        assertThat(checkItemBack.getCheckList()).isEqualTo(checkList);
    //
    //        checkList.setCheckItems(new HashSet<>());
    //        assertThat(checkList.getCheckItems()).doesNotContain(checkItemBack);
    //        assertThat(checkItemBack.getCheckList()).isNull();
    //    }
}
