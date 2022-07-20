package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import ru.practicum.shareit.Create;

@Data
public class UserDto {

    private long id;

    @NotBlank(groups = Create.class, message = "Name should not be empty")
    private String name;

    @NotBlank(groups = Create.class, message = "Incorrect email")
    @Pattern(regexp = "^(.+)@(\\S+)$", groups = Create.class, message = "Incorrect email")
    private String email;

    public UserDto() {
    }
}