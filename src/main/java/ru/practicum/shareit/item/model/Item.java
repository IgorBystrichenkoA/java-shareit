package ru.practicum.shareit.item.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    @ManyToOne
    @JoinColumn(name = "request_id")
    @JsonBackReference
    private ItemRequest request;
}
