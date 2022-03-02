package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PurchaseOrderDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrderDetails.class);
        PurchaseOrderDetails purchaseOrderDetails1 = new PurchaseOrderDetails();
        purchaseOrderDetails1.setId(1L);
        PurchaseOrderDetails purchaseOrderDetails2 = new PurchaseOrderDetails();
        purchaseOrderDetails2.setId(purchaseOrderDetails1.getId());
        assertThat(purchaseOrderDetails1).isEqualTo(purchaseOrderDetails2);
        purchaseOrderDetails2.setId(2L);
        assertThat(purchaseOrderDetails1).isNotEqualTo(purchaseOrderDetails2);
        purchaseOrderDetails1.setId(null);
        assertThat(purchaseOrderDetails1).isNotEqualTo(purchaseOrderDetails2);
    }
}
