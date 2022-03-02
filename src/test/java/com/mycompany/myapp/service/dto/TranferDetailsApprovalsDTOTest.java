package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TranferDetailsApprovalsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TranferDetailsApprovalsDTO.class);
        TranferDetailsApprovalsDTO tranferDetailsApprovalsDTO1 = new TranferDetailsApprovalsDTO();
        tranferDetailsApprovalsDTO1.setId(1L);
        TranferDetailsApprovalsDTO tranferDetailsApprovalsDTO2 = new TranferDetailsApprovalsDTO();
        assertThat(tranferDetailsApprovalsDTO1).isNotEqualTo(tranferDetailsApprovalsDTO2);
        tranferDetailsApprovalsDTO2.setId(tranferDetailsApprovalsDTO1.getId());
        assertThat(tranferDetailsApprovalsDTO1).isEqualTo(tranferDetailsApprovalsDTO2);
        tranferDetailsApprovalsDTO2.setId(2L);
        assertThat(tranferDetailsApprovalsDTO1).isNotEqualTo(tranferDetailsApprovalsDTO2);
        tranferDetailsApprovalsDTO1.setId(null);
        assertThat(tranferDetailsApprovalsDTO1).isNotEqualTo(tranferDetailsApprovalsDTO2);
    }
}
