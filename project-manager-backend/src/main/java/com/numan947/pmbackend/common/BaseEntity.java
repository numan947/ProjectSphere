package com.numan947.pmbackend.common;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;
/**
 * BaseEntity is a mapped superclass that provides common fields and functionality for all entities.
 * It includes fields for ID, creation date, and update date, and uses JPA auditing to automatically
 * populate these fields.
 *
 * Fields:
 * - id: The unique identifier for the entity.
 * - creationDate: The date when the entity was created, automatically populated.
 * - updateDate: The date when the entity was last updated, automatically populated.
 *
 * Methods:
 * - prePersist(): Sets a unique ID for the entity before it is persisted if the ID is not already set.
 *
 * Annotations:
 * - @MappedSuperclass: Indicates that this class is a superclass whose mapping information is applied to its subclasses.
 * - @EntityListeners(AuditingEntityListener.class): Specifies that the AuditingEntityListener should be used to populate auditing fields.
 * - @Getter, @Setter, @AllArgsConstructor, @NoArgsConstructor: Lombok annotations to generate boilerplate code.
 * - @Id: Specifies the primary key of the entity.
 * - @CreatedDate: Indicates that the creationDate field should be populated with the creation date.
 * - @Column: Specifies the details of the column to which a field will be mapped.
 * - @PrePersist: Specifies that the prePersist method should be called before the entity is persisted.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    private String id;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate creationDate;

    @Column(name = "updated_at", insertable = false)
    private LocalDate updateDate;

    @PrePersist
    public void prePersist() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
