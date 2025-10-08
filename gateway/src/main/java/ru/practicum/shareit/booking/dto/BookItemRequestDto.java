package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.Create;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemRequestDto {
	private long itemId;
	@FutureOrPresent(groups = Create.class)
	@NotNull(groups = Create.class)
	private LocalDateTime start;
	@Future(groups = Create.class)
	@NotNull(groups = Create.class)
	private LocalDateTime end;
}