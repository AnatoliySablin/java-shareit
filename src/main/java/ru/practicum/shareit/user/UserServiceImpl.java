package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EntityAlreadyExistException;
import ru.practicum.shareit.exception.ModelNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto addUser(UserDto userDto) {
        if (!isValid(userDto)) {
            throw new EntityAlreadyExistException("Email уже существует");
        }

        User user = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(userRepository.addUser(user));
    }


    @Override
    public UserDto updateUser(Long userId, UserDto patchUser) {
        UserDto existingUserDto = getUser(userId);
        if (existingUserDto == null) {
            throw new ModelNotFoundException(String.format("Пользователь с ID %d не найден", userId));
        }

        User oldUser = UserMapper.toUser(existingUserDto);
        User updatedUser = UserMapper.toUser(patchUser);
        User result = patch(oldUser, updatedUser);
        UserDto userDto = UserMapper.toUserDto(result);

        if (Objects.equals(oldUser.getEmail(), updatedUser.getEmail())) {
            if (!isValid(userDto)) {
                throw new EntityAlreadyExistException("Email уже существует");
            }
        }

        userRepository.updateUser(result, userId);
        return userDto;
    }


    private User patch(User user, User patchUser) {
        if (patchUser.getName() != null) {
            user.setName(patchUser.getName());
        }
        if (patchUser.getEmail() != null) {
            user.setEmail(patchUser.getEmail());
        }
        return user;
    }

    @Override
    public UserDto getUser(Long userId) {
        User user = userRepository.getUser(userId);
        if (user == null) {
            throw new ModelNotFoundException(String.format("User with id %s not found", userId));
        }
        return UserMapper.toUserDto(user);
    }


    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteUser(userId);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    private boolean isValid(UserDto user) {
        return userRepository.checkEmail(user.getEmail());
    }
}
