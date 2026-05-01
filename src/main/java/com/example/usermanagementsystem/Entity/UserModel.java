package com.example.usermanagementsystem.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.example.usermanagementsystem.Enum.Role;

@Entity
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name cannot be Empty")
    private String name;
    @Positive
    @Min(value = 18,message = "Age must be greater than 18")
    @Max(value = 99,message = "Age must be less than 99")
    private Integer age;
    @Email(message = "Enter a valid Email")
    private String email;
    @NotBlank(message = "Password cannot be Empty")
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
