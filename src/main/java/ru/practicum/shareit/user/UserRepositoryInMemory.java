package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class UserRepositoryInMemory implements UserRepository {
    private final Map<Long, User> allUsers = new HashMap<>();
    private final Set<String> emailUniqSet = new HashSet<>();

    @Override
    public User addUser(User user) {
        allUsers.put(user.getId(), user);
        emailUniqSet.add(user.getEmail());
        return user;
    }

    @Override
    public User updateUser(User user, Long userId) {
        emailUniqSet.remove(getUser(userId).getEmail());
        emailUniqSet.add(user.getEmail());
        return allUsers.put(user.getId(), user);
    }

    @Override
    public User getUser(Long userId) {
        return allUsers.get(userId);
    }

    @Override
    public void deleteUser(Long userId) {
        emailUniqSet.remove(getUser(userId).getEmail());
        allUsers.remove(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(allUsers.values());
    }

    @Override
    public boolean checkEmail(String userEmail) {
        return !emailUniqSet.contains(userEmail);
    }
}
