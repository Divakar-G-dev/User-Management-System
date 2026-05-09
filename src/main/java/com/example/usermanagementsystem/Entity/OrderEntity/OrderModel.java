package com.example.usermanagementsystem.Entity.OrderEntity;

import com.example.usermanagementsystem.Entity.UserEntity.UserModel;
import com.example.usermanagementsystem.Enum.OrderEnum.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class OrderModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Product Name Cannot be Empty")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Name must contain only alphabets"
    )
    private String productName;
    @Min(message = "Amount must be greater than 0", value = 1)
    private Integer amount;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel userModel;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @Builder.Default
    private boolean isActive = true;

}
