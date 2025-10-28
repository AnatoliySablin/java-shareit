package ru.practicum.shareit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureWebMvc
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public abstract class MVCShareItTests {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper mapper;
}
