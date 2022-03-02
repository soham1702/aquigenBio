package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RawMaterialOrderMapperTest {

    private RawMaterialOrderMapper rawMaterialOrderMapper;

    @BeforeEach
    public void setUp() {
        rawMaterialOrderMapper = new RawMaterialOrderMapperImpl();
    }
}
