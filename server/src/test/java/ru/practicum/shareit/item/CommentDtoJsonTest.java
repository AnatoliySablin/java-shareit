package ru.practicum.shareit.item;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.item.dto.CommentDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CommentDtoJsonTest {

    @Autowired
    private JacksonTester<CommentDto> json;

    @Test
    void testSerializeCommentDto() throws Exception {
        CommentDto dto = new CommentDto(1,
                "Great item!",
                "Alice",
                LocalDateTime.of(2025, 10, 28, 12, 0, 0)
        );
        dto.setId(1L);
        String jsonString = json.write(dto).getJson();
        assertThat(jsonString)
                .contains("\"id\":1")
                .contains("\"text\":\"Great item!\"")
                .contains("\"authorName\":\"Alice\"")
                .contains("\"created\":\"2025-10-28T12:00:00\"");
        assertThat(jsonString).isEqualTo(
                "{\"id\":1,\"text\":\"Great item!\",\"authorName\":\"Alice\",\"created\":\"2025-10-28T12:00:00\"}"
        );
    }
}
