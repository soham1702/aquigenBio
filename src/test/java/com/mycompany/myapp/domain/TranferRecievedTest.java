package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TranferRecievedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TranferRecieved.class);
        TranferRecieved tranferRecieved1 = new TranferRecieved();
        tranferRecieved1.setId(1L);
        TranferRecieved tranferRecieved2 = new TranferRecieved();
        tranferRecieved2.setId(tranferRecieved1.getId());
        assertThat(tranferRecieved1).isEqualTo(tranferRecieved2);
        tranferRecieved2.setId(2L);
        assertThat(tranferRecieved1).isNotEqualTo(tranferRecieved2);
        tranferRecieved1.setId(null);
        assertThat(tranferRecieved1).isNotEqualTo(tranferRecieved2);
    }
}
