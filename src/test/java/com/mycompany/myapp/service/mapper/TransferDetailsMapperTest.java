package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransferDetailsMapperTest {

    private TransferDetailsMapper transferDetailsMapper;

    @BeforeEach
    public void setUp() {
        transferDetailsMapper = new TransferDetailsMapperImpl();
    }
}
