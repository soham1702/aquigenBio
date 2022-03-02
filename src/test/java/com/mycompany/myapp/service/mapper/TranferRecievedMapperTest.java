package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TranferRecievedMapperTest {

    private TranferRecievedMapper tranferRecievedMapper;

    @BeforeEach
    public void setUp() {
        tranferRecievedMapper = new TranferRecievedMapperImpl();
    }
}
