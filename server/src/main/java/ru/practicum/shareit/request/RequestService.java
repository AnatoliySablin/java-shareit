package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.RequestDto;

import java.util.List;

public interface RequestService {
    RequestDto addRequest(RequestDto requestDto, long requestorId);

    List<RequestDto> getAllRequestsForRequestor(long requestorId);

    List<RequestDto> getAllRequests(long requestorId, int from, int size);

    RequestDto getOneRequest(long requestId, long userId);
}