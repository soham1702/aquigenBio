package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RawMaterialOrderDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RawMaterialOrderDTO.class);
        RawMaterialOrderDTO rawMaterialOrderDTO1 = new RawMaterialOrderDTO();
        rawMaterialOrderDTO1.setId(1L);
        RawMaterialOrderDTO rawMaterialOrderDTO2 = new RawMaterialOrderDTO();
        assertThat(rawMaterialOrderDTO1).isNotEqualTo(rawMaterialOrderDTO2);
        rawMaterialOrderDTO2.setId(rawMaterialOrderDTO1.getId());
        assertThat(rawMaterialOrderDTO1).isEqualTo(rawMaterialOrderDTO2);
        rawMaterialOrderDTO2.setId(2L);
        assertThat(rawMaterialOrderDTO1).isNotEqualTo(rawMaterialOrderDTO2);
        rawMaterialOrderDTO1.setId(null);
        assertThat(rawMaterialOrderDTO1).isNotEqualTo(rawMaterialOrderDTO2);
    }
}
