package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String user;

    @Column(length = 200, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = false)
    private Role role = Role.ROLE_CLIENT;

    @CreatedDate
    @Column
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column
    private LocalDateTime changeDate;

    @CreatedBy
    @Column
    private String createBy;

    @LastModifiedBy
    @Column
    private String changeBy;

    public enum Role {
        ROLE_ADMIN, ROLE_CLIENT
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                '}';
    }
}
