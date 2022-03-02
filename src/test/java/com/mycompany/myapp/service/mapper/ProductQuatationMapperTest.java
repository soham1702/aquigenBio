package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductQuatationMapperTest {

    private ProductQuatationMapper productQuatationMapper;

    @BeforeEach
    public void setUp() {
        productQuatationMapper = new ProductQuatationMapperImpl();
    }
}
