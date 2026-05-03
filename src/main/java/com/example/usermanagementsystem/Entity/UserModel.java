package com.example.usermanagementsystem.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.example.usermanagementsystem.Enum.Role;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name cannot be Empty")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Name must contain only alphabets"
    )
    private String name;
    @Min(value = 18, message = "Age must be greater than 18")
    @Max(value = 99, message = "Age must be less than 99")
    private Integer age;
    @Email(message = "Enter a valid Email")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Password cannot be Empty")
    private String password;
    @NotNull(message = "Role must be a ADMIN or USER cannot be empty")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Builder.Default
    private boolean isActive = true;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
