package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.practicum.shareit.item.dto.ItemDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ItemDtoJsonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSerializeItemDto() throws Exception {
        ItemDto dto = ItemDto.builder()
                .id(1L)
                .name("Laptop")
                .description("A powerful laptop for work")
                .available(true)
                .requestId(100L)
                .build();
        String jsonString = objectMapper.writeValueAsString(dto);
        assertThat(jsonString)
                .contains("\"id\":1")
                .contains("\"name\":\"Laptop\"")
                .contains("\"description\":\"A powerful laptop for work\"")
                .contains("\"available\":true")
                .contains("\"requestId\":100");
        assertThat(jsonString).isEqualTo(
                "{\"id\":1,\"name\":\"Laptop\",\"description\":\"A powerful laptop for work\",\"available\":true," +
                        "\"requestId\":100}"
        );
    }
}
