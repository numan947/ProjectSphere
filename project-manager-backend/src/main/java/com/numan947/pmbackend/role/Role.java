package com.numan947.pmbackend.role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.numan947.pmbackend.common.BaseEntity;
import com.numan947.pmbackend.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;


@Entity
@Table(name="_roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role extends BaseEntity {
    @Column(unique = true)
    private String name;
    private String description;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;
}
