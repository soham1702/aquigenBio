package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductQuatationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductQuatation.class);
        ProductQuatation productQuatation1 = new ProductQuatation();
        productQuatation1.setId(1L);
        ProductQuatation productQuatation2 = new ProductQuatation();
        productQuatation2.setId(productQuatation1.getId());
        assertThat(productQuatation1).isEqualTo(productQuatation2);
        productQuatation2.setId(2L);
        assertThat(productQuatation1).isNotEqualTo(productQuatation2);
        productQuatation1.setId(null);
        assertThat(productQuatation1).isNotEqualTo(productQuatation2);
    }
}
