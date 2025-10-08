package ru.practicum.shareit.booking;

import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.Create;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;


@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
	private final BookingClient bookingClient;

	@PostMapping
	public ResponseEntity<Object> addBooking(@Validated(Create.class) @RequestBody BookItemRequestDto bookItemRequestDto,
											 @RequestHeader("X-Sharer-User-Id") long userId) {
		log.info("Booking was created by user {}", userId);
		return bookingClient.addBooking(bookItemRequestDto, userId);
	}

	@PatchMapping("{bookingId}")
	public ResponseEntity<Object> approveBooking(@PathVariable long bookingId,
												 @RequestParam(value = "approved") Boolean approved,
												 @RequestHeader("X-Sharer-User-Id") long userId) {
		log.info("Booking {} was approved", bookingId);
		return bookingClient.approveBooking(bookingId, approved, userId);
	}

	@GetMapping("{bookingId}")
	public ResponseEntity<Object> getBooking(@PathVariable long bookingId,
											 @RequestHeader("X-Sharer-User-Id") long userId) {
		log.info("Get booking {}", bookingId);
		return bookingClient.getBookingByIdIfOwnerOrBooker(bookingId, userId);
	}

	@GetMapping()
	public ResponseEntity<Object> getBookingByUserSorted(
			@RequestParam(name = "state", required = false, defaultValue = "ALL") State state,
			@RequestHeader("X-Sharer-User-Id") long bookerId,
			@RequestParam(value = "from", required = false, defaultValue = "0") @Min(0) int from,
			@RequestParam(value = "size", required = false, defaultValue = "20") @Min(1) int size) {
		log.info("Get all bookings for booker {}", bookerId);
		return bookingClient.getBookingByUserSorted(bookerId, state, from, size);
	}

	@GetMapping("/owner")
	public ResponseEntity<Object> getBookingsForItemOwner(
			@RequestParam(name = "state", required = false, defaultValue = "ALL") State state,
			@RequestHeader("X-Sharer-User-Id") long ownerId,
			@RequestParam(value = "from", required = false, defaultValue = "0") @Min(0) int from,
			@RequestParam(value = "size", required = false, defaultValue = "20") @Min(1) int size) {
		log.info("Get all bookings for item owner {}", ownerId);
		return bookingClient.getBookingByItemOwner(ownerId, state, from, size);
	}
}
