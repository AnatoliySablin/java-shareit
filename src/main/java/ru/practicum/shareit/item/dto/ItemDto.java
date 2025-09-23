package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.Create;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private long id;

    @NotBlank(groups = Create.class, message = "Name should not be empty")
    private String name;

    @NotBlank(groups = Create.class, message = "Item description should not be empty")
    private String description;

    @NotNull(groups = Create.class, message = "Item available should not be empty")
    private Boolean available;

    private Long requestId;
}