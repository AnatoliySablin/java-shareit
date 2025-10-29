package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.shareit.Create;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookItemRequestDto {
    private long itemId;
    @FutureOrPresent(groups = Create.class)
    @NotNull(groups = Create.class)
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    @FutureOrPresent(message = "Start date can not be in past")
    private LocalDateTime start;
    @Future(groups = Create.class)
    @NotNull(groups = Create.class)
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    @Future(message = "End date can not be in past")
    private LocalDateTime end;
}