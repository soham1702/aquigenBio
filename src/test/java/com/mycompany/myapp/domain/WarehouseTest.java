package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WarehouseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Warehouse.class);
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setId(1L);
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setId(warehouse1.getId());
        assertThat(warehouse1).isEqualTo(warehouse2);
        warehouse2.setId(2L);
        assertThat(warehouse1).isNotEqualTo(warehouse2);
        warehouse1.setId(null);
        assertThat(warehouse1).isNotEqualTo(warehouse2);
    }
}
