package ru.practicum.shareit.itemrequesttests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class ItemRequestDtoJsonTests {
    @Autowired
    JacksonTester<ItemRequestDto> json;

    @Test
    void testItemRequestDto() throws Exception {
        ItemRequestDto itemRequestDto = ItemRequestDto
                .builder()
                .id(1L)
                .description("descriptionOfItemRequest")
                .created(LocalDateTime.of(2024, 11, 11, 12, 00))
                .requestor(User.builder().id(1L).build())
                .build();

        JsonContent<ItemRequestDto> result = json.write(itemRequestDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.description")
                          .isEqualTo("descriptionOfItemRequest");
        assertThat(result).extractingJsonPathStringValue("$.created")
                          .isEqualTo("2024-11-11T12:00:00");
        assertThat(result).extractingJsonPathNumberValue("$.requestor.id").isEqualTo(1);
    }
}
