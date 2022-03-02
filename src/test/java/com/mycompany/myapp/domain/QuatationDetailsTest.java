package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuatationDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuatationDetails.class);
        QuatationDetails quatationDetails1 = new QuatationDetails();
        quatationDetails1.setId(1L);
        QuatationDetails quatationDetails2 = new QuatationDetails();
        quatationDetails2.setId(quatationDetails1.getId());
        assertThat(quatationDetails1).isEqualTo(quatationDetails2);
        quatationDetails2.setId(2L);
        assertThat(quatationDetails1).isNotEqualTo(quatationDetails2);
        quatationDetails1.setId(null);
        assertThat(quatationDetails1).isNotEqualTo(quatationDetails2);
    }
}
