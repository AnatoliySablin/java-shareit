package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.Create;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;
    @NotBlank(groups = Create.class, message = "Name should not be empty")
    private String name;
    @NotBlank(groups = Create.class, message = "Incorrect email")
    @Pattern(regexp = "^(.+)@(\\S+)$", groups = Create.class, message = "Incorrect email")
    private String email;

    public UserDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}