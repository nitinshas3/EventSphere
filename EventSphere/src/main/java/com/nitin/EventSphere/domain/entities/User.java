package com.nitin.EventSphere.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    // organizing events - a user can organize many events  , this is owned by the many side that is the event entity

    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL) // cascade meaning like if i do something to organizer that should also happen to even , like persist save update delete and all , if deleted organizer , delete the event too
    private List<Event> organizedEvents = new ArrayList<>();


    // attending events
    // this is many to many doesnt matter who owns coz new talbe is created with primary keys of both the talbes event and user

    @ManyToMany
    @JoinTable(
            name = "user_attending_events",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> attendingEvents = new ArrayList<>();

    // staffing events
    // even this many users can become staff of many event  , doenst matter who owns new table

    @ManyToMany
    @JoinTable(
            name = "user_staffing_events",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> staffingEvents = new ArrayList<>();

    @CreatedDate // injects time when entity created
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // injets time when last time entity modified
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email,
                user.email) && Objects.equals(createdAt, user.createdAt) && Objects.equals(updatedAt,
                user.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, createdAt, updatedAt);
    }
}
// the reason we dont use @data for jpa entites is , it generates to_string automatially wehn we have relation ships one to many etc , and print the object , the current prints the next objets to_string , that prints this ones to_string and stackoverflow occurs infinte recursion thats why
// the converntion is many side owns the relation ship meaning that has the foreing key column