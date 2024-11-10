package ru.practicum.shareit.itemtests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class ItemDtoJsonTests {
    @Autowired
    JacksonTester<ItemDto> json;

    @Autowired
    JacksonTester<ItemCreateDto> jsonCreate;

    @Autowired
    JacksonTester<ItemUpdateDto> jsonUpdate;

    @Test
    void testItemDto() throws Exception {
        ItemDto itemDto = ItemDto
                .builder()
                .id(1L)
                .name("item")
                .available(true)
                .description("description")
                .build();

        JsonContent<ItemDto> result = json.write(itemDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("item");
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("description");
        assertThat(result).extractingJsonPathBooleanValue("$.available").isTrue();
    }

    @Test
    void testItemCreateDto() throws Exception {
        ItemCreateDto itemDto = ItemCreateDto
                .builder()
                .name("item")
                .available(true)
                .description("description")
                .build();

        JsonContent<ItemCreateDto> result = jsonCreate.write(itemDto);

        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("item");
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("description");
        assertThat(result).extractingJsonPathBooleanValue("$.available").isTrue();
    }

    @Test
    void testItemUpdateDto() throws Exception {
        ItemUpdateDto itemDto = ItemUpdateDto
                .builder()
                .name("item")
                .available(true)
                .description("description")
                .build();

        JsonContent<ItemUpdateDto> result = jsonUpdate.write(itemDto);

        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("item");
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("description");
        assertThat(result).extractingJsonPathBooleanValue("$.available").isTrue();
    }
}
