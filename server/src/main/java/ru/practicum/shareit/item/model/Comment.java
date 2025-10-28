package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.user.model.User;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long id;
    @Column(name = "text", nullable = false, length = 4000)
    private String text;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    private Item item;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "author_id", referencedColumnName = "user_id")
    private User author;
    @Column(name = "created")
    private LocalDateTime created = LocalDateTime.now();
}