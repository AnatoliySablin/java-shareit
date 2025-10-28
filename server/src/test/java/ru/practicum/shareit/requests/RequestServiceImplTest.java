package ru.practicum.shareit.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import ru.practicum.shareit.SimpleShareItTests;
import ru.practicum.shareit.request.RequestService;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(value = "classpath:application.properties")
class RequestServiceImplTest extends SimpleShareItTests {

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserRepository userRepository;

    private RequestDto requestDto;
    private User user;
    private User anotherUser;

    @BeforeEach
    void setUp() {
        user = userRepository.save(
                User.builder()
                    .name("user")
                    .email("user@gmail.com")
                    .build()
        );
        anotherUser = userRepository.save(
                User.builder()
                    .name("another user")
                    .email("another@gmail.com")
                    .build()
        );
        requestDto = RequestDto.builder()
                               .description("request")
                               .requestorId(user.getId())
                               .items(List.of())
                               .build();
    }

    @Test
    void addRequest() {
        RequestDto result = requestService.addRequest(requestDto, user.getId());
        requestDto.setId(result.getId());
        assertThat(result, equalTo(requestDto));
    }

    @Test
    void getAllRequestsForRequestor() {
        RequestDto added = requestService.addRequest(requestDto, user.getId());
        requestDto.setId(added.getId());
        List<RequestDto> result = requestService.getAllRequestsForRequestor(user.getId());
        assertThat(result, equalTo(List.of(requestDto)));
    }

    @Test
    void getAllRequests() {
        RequestDto added = requestService.addRequest(requestDto, user.getId());
        requestDto.setId(added.getId());
        List<RequestDto> result = requestService.getAllRequests(anotherUser.getId(), 0, 10);
        assertThat(result, equalTo(List.of(requestDto)));
    }

    @Test
    void getOneRequest() {
        RequestDto added = requestService.addRequest(requestDto, user.getId());
        requestDto.setId(added.getId());
        RequestDto result = requestService.getOneRequest(requestDto.getId(), user.getId());
        assertThat(result, equalTo(requestDto));
    }

    @Test
    void getOneRequestWithModelNotFoundException() {
        RequestDto added = requestService.addRequest(requestDto, user.getId());
        requestDto.setId(added.getId());
        assertThatThrownBy(() -> requestService.getOneRequest(requestDto.getId(), 1000));
    }
}
