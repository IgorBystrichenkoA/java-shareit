package ru.practicum.shareit.request;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;

public class ItemRequestClient extends BaseClient {

    public ItemRequestClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> getAll(long userId) {
        return get("/all", userId);
    }

    public ResponseEntity<Object> create(long userId, ItemRequestCreateDto requestDto) {
        return post("", userId, requestDto);
    }

    public ResponseEntity<Object> getAllByUser(long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> get(Long requestId) {
        return get("/" + requestId);
    }
}