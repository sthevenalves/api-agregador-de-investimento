package com.sthev.agregador_investimentos.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    private String username;

    private String email;

    private String password;

    @CreationTimestamp
    private LocalDateTime creationTimeStamp;

    @UpdateTimestamp
    private LocalDateTime updateTimeStamp;

    public User() {
    }

    public User(UUID userId, LocalDateTime updateTimeStamp, LocalDateTime creationTimeStamp, String password, String email, String username) {
        this.userId = userId;
        this.updateTimeStamp = updateTimeStamp;
        this.creationTimeStamp = creationTimeStamp;
        this.password = password;
        this.email = email;
        this.username = username;
    }

    public User(UserDTO user) {
        this.username = user.username();
        this.email = user.email();
        this.password = user.password();

    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(LocalDateTime creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public LocalDateTime getUpdateTimeStamp() {
        return updateTimeStamp;
    }

    public void setUpdateTimeStamp(LocalDateTime updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }
}
