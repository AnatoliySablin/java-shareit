package ru.practicum.shareit.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.practicum.shareit.request.dto.RequestDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class RequestDtoJsonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSerializeRequestDto() throws Exception {
        RequestDto dto = RequestDto.builder()
                .id(1L)
                .description("Need a laptop for work")
                .created(LocalDateTime.of(2025, 10, 28, 12, 0, 0))
                .requestorId(10L)
                .items(List.of())
                .build();
        String jsonString = objectMapper.writeValueAsString(dto);
        assertThat(jsonString)
                .contains("\"id\":1")
                .contains("\"description\":\"Need a laptop for work\"")
                .contains("\"created\":\"2025-10-28T12:00:00\"")
                .contains("\"requestorId\":10")
                .contains("\"items\":[]");
        assertThat(jsonString).isEqualTo(
                "{\"id\":1,\"description\":\"Need a laptop for work\",\"created\":\"2025-10-28T12:00:00\"," +
                        "\"requestorId\":10,\"items\":[]}"
        );
    }
}
