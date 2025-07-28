package ru.practicum.shareit.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.Create;

@Data
@NoArgsConstructor
public class UserDto {

    private long id;

    @NotBlank(groups = Create.class)
    private String name;

    @NotBlank(groups = Create.class)
    @Pattern(regexp = "^(.+)@(\\S+)$", groups = Create.class)
    private String email;
}
