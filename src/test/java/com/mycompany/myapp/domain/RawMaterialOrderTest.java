package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RawMaterialOrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RawMaterialOrder.class);
        RawMaterialOrder rawMaterialOrder1 = new RawMaterialOrder();
        rawMaterialOrder1.setId(1L);
        RawMaterialOrder rawMaterialOrder2 = new RawMaterialOrder();
        rawMaterialOrder2.setId(rawMaterialOrder1.getId());
        assertThat(rawMaterialOrder1).isEqualTo(rawMaterialOrder2);
        rawMaterialOrder2.setId(2L);
        assertThat(rawMaterialOrder1).isNotEqualTo(rawMaterialOrder2);
        rawMaterialOrder1.setId(null);
        assertThat(rawMaterialOrder1).isNotEqualTo(rawMaterialOrder2);
    }
}
