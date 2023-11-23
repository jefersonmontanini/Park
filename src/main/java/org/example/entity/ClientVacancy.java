package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientVacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 15)
    private String receipt;

    @Column(nullable = false, length = 8)
    private String plate;

    @Column(nullable = false, length = 45)
    private String brand;

    @Column(nullable = false, length = 45)
    private String model;

    @Column(nullable = false, length = 45)
    private String color;

    @Column(nullable = false)
    private LocalDateTime entryDate;

    private String departureDate;

    @Column(columnDefinition = "decimal(7,2)")
    private BigDecimal price;

    @Column(columnDefinition = "decimal(7,2)")
    private BigDecimal discount;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;


    @ManyToOne
    @JoinColumn(name = "id_vacancy", nullable = false)
    private Vacancy vacancy;

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Vacancy.StatusVacancy status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientVacancy that = (ClientVacancy) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
