package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users_roles")
@Data
@NoArgsConstructor
public class UserRoles {
    @Id
    int roles_id;
    @Column(name="users_id")
    int users_for_login_id;
}
