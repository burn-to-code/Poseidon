package com.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "users")
public class User implements BaseEntity<User>{
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Size(max = 125)
    @Column(name = "username", length = 125)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Size(max = 125)
    @Column(name = "password", length = 125)
    @NotBlank(message = "Password is mandatory")
    @Pattern(
            regexp = "(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}",
            message = "Password must be at least 8 characters long, contain one uppercase, one lowercase, one digit and one special character"
    )
    private String password;

    @Size(max = 125)
    @Column(name = "fullname", length = 125)
    @NotBlank(message = "Fullname is mandatory")
    private String fullname;

    @Size(max = 125)
    @Column(name = "role", length = 125)
    @NotBlank(message = "Role is mandatory")
    private String role;

    @Override
    public void update(User user) {
        this.fullname = user.getFullname();
        this.role = user.getRole();
        this.username = user.getUsername();
    }
}