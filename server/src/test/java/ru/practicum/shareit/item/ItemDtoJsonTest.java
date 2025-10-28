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
    void testSerializeAndDeserializeItemDto() throws Exception {
        ItemDto originalDto = ItemDto.builder()
                .id(1L)
                .name("Laptop")
                .description("A powerful laptop for work")
                .available(true)
                .requestId(100L)
                .build();

        String jsonString = objectMapper.writeValueAsString(originalDto);
        ItemDto parsedDto = objectMapper.readValue(jsonString, ItemDto.class);
        assertThat(parsedDto.getId()).isEqualTo(originalDto.getId());
        assertThat(parsedDto.getName()).isEqualTo(originalDto.getName());
        assertThat(parsedDto.getDescription()).isEqualTo(originalDto.getDescription());
        assertThat(parsedDto.getAvailable()).isEqualTo(originalDto.getAvailable());
        assertThat(jsonString)
                .contains("\"id\":1")
                .contains("\"name\":\"Laptop\"");
    }
}
