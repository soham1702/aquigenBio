package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TranferRecievedDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TranferRecievedDTO.class);
        TranferRecievedDTO tranferRecievedDTO1 = new TranferRecievedDTO();
        tranferRecievedDTO1.setId(1L);
        TranferRecievedDTO tranferRecievedDTO2 = new TranferRecievedDTO();
        assertThat(tranferRecievedDTO1).isNotEqualTo(tranferRecievedDTO2);
        tranferRecievedDTO2.setId(tranferRecievedDTO1.getId());
        assertThat(tranferRecievedDTO1).isEqualTo(tranferRecievedDTO2);
        tranferRecievedDTO2.setId(2L);
        assertThat(tranferRecievedDTO1).isNotEqualTo(tranferRecievedDTO2);
        tranferRecievedDTO1.setId(null);
        assertThat(tranferRecievedDTO1).isNotEqualTo(tranferRecievedDTO2);
    }
}
