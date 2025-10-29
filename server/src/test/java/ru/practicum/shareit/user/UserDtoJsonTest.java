package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.user.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class UserDtoJsonTest {

    @Autowired
    private JacksonTester<UserDto> json;


    @Test
    void testSerializeUserDto() throws Exception {
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setName("Test User");
        dto.setEmail("test.user@example.com");
        String jsonString = json.write(dto).getJson();
        assertThat(jsonString)
                .contains("\"id\":1")
                .contains("\"name\":\"Test User\"")
                .contains("\"email\":\"test.user@example.com\"");
        assertThat(jsonString).isEqualTo(
                "{\"id\":1,\"name\":\"Test User\",\"email\":\"test.user@example.com\"}"
        );
    }
}
