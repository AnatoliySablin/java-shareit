package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import ru.practicum.shareit.SimpleShareItTests;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@TestPropertySource(value = "classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookingServiceImplTest extends SimpleShareItTests {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;

    private BookItemRequestDto input;
    private BookingDto output;
    private User owner;
    private User booker;
    private static final LocalDateTime START = LocalDateTime.now().plusDays(1);
    private static final LocalDateTime END = START.plusDays(2);

    @BeforeEach
    void setUp() {
        owner = userRepository.save(
                User.builder()
                    .name("owner")
                    .email("owner@gmail.com")
                    .build()
        );
        booker = userRepository.save(
                User.builder()
                     .name("booker")
                     .email("booker@gmail.com")
                     .build()
        );
        final Item item = itemRepository.save(
                Item.builder()
                   .name("item")
                   .available(true)
                   .description("item 1")
                   .owner(owner)
                   .build()
        );
        input = BookItemRequestDto.builder()
                                  .itemId(item.getId())
                                  .start(START)
                                  .end(END)
                                  .build();
        output = BookingDto.builder()
                           .start(START)
                           .end(END)
                           .item(new BookingDto.ItemBooking(item.getId(), item.getName()))
                           .booker(new BookingDto.Booker(booker.getId(), booker.getName()))
                           .status(Status.WAITING)
                           .build();
    }

    @Test
    void addBooking() {
        BookingDto result = bookingService.addBooking(input, booker.getId());
        output.setId(result.getId());
        assertThat(result, equalTo(output));
    }

    @Test
    void approveBooking() {
        BookingDto added = bookingService.addBooking(input, booker.getId());
        BookingDto result = bookingService.approveBooking(added.getId(), true, owner.getId());
        assertThat(result.getStatus(), is(Status.APPROVED));
    }

    @Test
    void getBookingByIdIfOwnerOrBooker() {
        BookingDto added = bookingService.addBooking(input, booker.getId());
        BookingDto result = bookingService.getBookingByIdIfOwnerOrBooker(added.getId(), owner.getId());
        output.setId(result.getId());
        assertThat(result, equalTo(output));
    }

    @Test
    void getBookingByIdIfOwnerOrBookerWithModelNotFoundException() {
        assertThatThrownBy(() -> bookingService.getBookingByIdIfOwnerOrBooker(output.getId(), booker.getId()));
    }

    @Test
    void getBookingByUserSorted() {
        bookingService.addBooking(input, booker.getId());
        List<BookingDto> result = bookingService.getBookingByUserSorted(booker.getId(), State.ALL, 0, 2);
        assertThat(result, hasSize(1));
    }

    @Test
    void getBookingByItemOwner() {
        bookingService.addBooking(input, booker.getId());
        List<BookingDto> result = bookingService.getBookingByItemOwner(owner.getId(), State.ALL, 0, 2);
        assertThat(result, hasSize(1));
    }
}
