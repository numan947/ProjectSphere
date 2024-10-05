package com.numan947.pmbackend.primary_packages.metadata;

import com.numan947.pmbackend.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_metadata",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "type"})
    }
)
public class Metadata extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetadataType type;
}
