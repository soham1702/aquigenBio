package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuatationDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuatationDetailsDTO.class);
        QuatationDetailsDTO quatationDetailsDTO1 = new QuatationDetailsDTO();
        quatationDetailsDTO1.setId(1L);
        QuatationDetailsDTO quatationDetailsDTO2 = new QuatationDetailsDTO();
        assertThat(quatationDetailsDTO1).isNotEqualTo(quatationDetailsDTO2);
        quatationDetailsDTO2.setId(quatationDetailsDTO1.getId());
        assertThat(quatationDetailsDTO1).isEqualTo(quatationDetailsDTO2);
        quatationDetailsDTO2.setId(2L);
        assertThat(quatationDetailsDTO1).isNotEqualTo(quatationDetailsDTO2);
        quatationDetailsDTO1.setId(null);
        assertThat(quatationDetailsDTO1).isNotEqualTo(quatationDetailsDTO2);
    }
}
