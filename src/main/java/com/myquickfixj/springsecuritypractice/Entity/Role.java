package com.myquickfixj.springsecuritypractice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles",
        uniqueConstraints = @UniqueConstraint(columnNames = "roleName"))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    private String roleId;
    @Column(nullable = false, unique = true)
    private String roleName;



}
