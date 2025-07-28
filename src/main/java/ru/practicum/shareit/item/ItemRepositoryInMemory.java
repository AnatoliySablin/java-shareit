package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ItemRepositoryInMemory implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private final Map<Long, List<Item>> userItemIndex = new LinkedHashMap<>();
    private long idCounter = 0;

    @Override
    public Item addItem(Item item) {
        item.setId(++idCounter); // Генерируем ID здесь
        userItemIndex.computeIfAbsent(item.getOwner().getId(), k -> new ArrayList<>());
        userItemIndex.get(item.getOwner().getId()).add(item);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item updateItem(Item item) {
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item getItemById(long itemId) {
        return items.get(itemId);
    }

    @Override
    public List<Item> getAllItemsOfOwner(long userId) {
        return userItemIndex.getOrDefault(userId, List.of());
    }


    @Override
    public List<Item> getAllItemsAvailableToRent(String text) {
        return items.values().stream()
                .filter(Item::getAvailable)
                .filter(x -> x.getName().toLowerCase().contains(text.toLowerCase()) ||
                        x.getDescription().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());
    }
}
