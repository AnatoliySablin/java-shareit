package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.Create;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private long id;

    @NotBlank(groups = Create.class, message = "Text can not be empty")
    @Size(max = 2000, groups = Create.class, message = "Text must not exceed 2000 characters")
    private String text;

    private String authorName;

    private LocalDateTime created;
}
