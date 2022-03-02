package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductQuatationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductQuatationDTO.class);
        ProductQuatationDTO productQuatationDTO1 = new ProductQuatationDTO();
        productQuatationDTO1.setId(1L);
        ProductQuatationDTO productQuatationDTO2 = new ProductQuatationDTO();
        assertThat(productQuatationDTO1).isNotEqualTo(productQuatationDTO2);
        productQuatationDTO2.setId(productQuatationDTO1.getId());
        assertThat(productQuatationDTO1).isEqualTo(productQuatationDTO2);
        productQuatationDTO2.setId(2L);
        assertThat(productQuatationDTO1).isNotEqualTo(productQuatationDTO2);
        productQuatationDTO1.setId(null);
        assertThat(productQuatationDTO1).isNotEqualTo(productQuatationDTO2);
    }
}
