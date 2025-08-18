package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.BoardTestSamples.*;
import static com.mycompany.myapp.domain.WorkspaceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class WorkspaceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Workspace.class);
        Workspace workspace1 = getWorkspaceSample1();
        Workspace workspace2 = new Workspace();
        assertThat(workspace1).isNotEqualTo(workspace2);

        workspace2.setId(workspace1.getId());
        assertThat(workspace1).isEqualTo(workspace2);

        workspace2 = getWorkspaceSample2();
        assertThat(workspace1).isNotEqualTo(workspace2);
    }

    @Test
    void boardsTest() {
        Workspace workspace = getWorkspaceRandomSampleGenerator();
        Board boardBack = getBoardRandomSampleGenerator();

        workspace.addBoards(boardBack);
        assertThat(workspace.getBoards()).containsOnly(boardBack);
        assertThat(boardBack.getWorkspace()).isEqualTo(workspace);

        workspace.removeBoards(boardBack);
        assertThat(workspace.getBoards()).doesNotContain(boardBack);
        assertThat(boardBack.getWorkspace()).isNull();

        workspace.boards(new HashSet<>(Set.of(boardBack)));
        assertThat(workspace.getBoards()).containsOnly(boardBack);
        assertThat(boardBack.getWorkspace()).isEqualTo(workspace);

        workspace.setBoards(new HashSet<>());
        assertThat(workspace.getBoards()).doesNotContain(boardBack);
        assertThat(boardBack.getWorkspace()).isNull();
    }
}
