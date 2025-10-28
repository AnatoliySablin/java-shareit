package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import ru.practicum.shareit.SimpleShareItTests;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestPropertySource(value = "classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserServiceImplTest extends SimpleShareItTests {

    @Autowired
    private UserServiceImpl userService;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                         .name("user")
                         .email("user@gmail.com")
                         .build();
    }

    @Test
    void addUser() {
        UserDto added = userService.addUser(userDto);
        userDto.setId(added.getId());
        UserDto result = userService.getUser(userDto.getId());
        assertThat(result.getId(), notNullValue());
        assertThat(result, equalTo(userDto));
    }

    @Test
    void addUserWithException() {
        UserDto added = userService.addUser(userDto);
        userDto.setId(added.getId());
        assertThatThrownBy(() -> userService.addUser(userDto)).hasMessage("User with email already exist");
    }

    @Test
    void updateUser() {
        UserDto added = userService.addUser(userDto);
        userDto.setId(added.getId());
        userDto.setName("new");
        UserDto result = userService.updateUser(userDto.getId(), userDto);
        assertThat(result, equalTo(userDto));
    }

    @Test
    void updateUserWithException() {
        UserDto secondUser = UserDto.builder()
                                    .name("second")
                                    .email("second@gmail.com")
                                    .build();
        UserDto added = userService.addUser(userDto);
        userDto.setId(added.getId());
        userService.addUser(secondUser);
        assertThatThrownBy(
                () -> userService.updateUser(userDto.getId(), secondUser)).hasMessage("User with email already exist");
    }

    @Test
    void getUser() {
        UserDto added = userService.addUser(userDto);
        userDto.setId(added.getId());
        UserDto result = userService.getUser(userDto.getId());
        assertThat(result, equalTo(userDto));
    }

    @Test
    void getUserWithException() {
        userDto.setId(1L);
        assertThatThrownBy(() -> userService.getUser(userDto.getId())).hasMessage(
                String.format("User %d not found", userDto.getId())
        );
    }

    @Test
    void deleteUser() {
        UserDto added = userService.addUser(userDto);
        userDto.setId(added.getId());
        userService.deleteUser(userDto.getId());
        assertThat(userService.getAllUsers().size(), equalTo(0));
    }

    @Test
    void getAllUsers() {
        UserDto added = userService.addUser(userDto);
        userDto.setId(added.getId());
        List<UserDto> result = userService.getAllUsers();
        assertThat(result, equalTo(List.of(userDto)));
    }
}
