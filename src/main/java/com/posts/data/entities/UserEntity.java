package com.posts.data.entities;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table("users")
public class UserEntity {

    @Id
    Long id;
    String username;
    String password;
    UserRole role;
    String first_name;
    String last_name;
    boolean enabled;
    LocalDateTime created_at;
    LocalDateTime updated_at;

    @ToString.Include(name = "password")
    private String maskPassword() {
        return "********";
    }
}