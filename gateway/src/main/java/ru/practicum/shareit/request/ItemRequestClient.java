package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.Map;

@Service
public class ItemRequestClient extends BaseClient {
    private static final String API_PREFIX = "/requests";

    @Autowired
    public ItemRequestClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public ResponseEntity<Object> addRequest(ItemRequestDto itemRequestDto, long requestorId) {
        return post(API_PREFIX, requestorId, itemRequestDto);
    }

    public ResponseEntity<Object> getAllRequestsForRequestor(long requestorId) {
        return get("", requestorId);
    }

    public ResponseEntity<Object> getAllRequests(long requestorId, int from, int size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get(API_PREFIX + "/all?from={from}&size={size}", requestorId, parameters);
    }

    public ResponseEntity<Object> getOneRequest(long requestId, long userId) {
        return get(API_PREFIX + "/" + requestId, userId);
    }
}