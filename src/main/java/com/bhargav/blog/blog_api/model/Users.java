package com.bhargav.blog.blog_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // e.g., "USER", "ADMIN"
}
