package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.Create;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {
    private long id;
    @NotBlank(groups = Create.class, message = "Item name can not be empty")
    private String name;
    @NotBlank(groups = Create.class, message = "Item description can not be empty")
    private String description;
    @NotNull(groups = Create.class, message = "Item Available can not be empty")
    private Boolean available;
    private Long requestId;
}