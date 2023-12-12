package com.example.demo.repository;


import com.example.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
   // Optional<UserEntity> findByRolesId(Integer id);


   // SELECT roles.name from user_roles inner join  roles on roles.id = user_roles.roles_id where user_roles.users_id = 1;

   // @Query("SELECT Role.name FROM UserRoles INNER JOIN Role on Role.id = UserRoles.roles_id WHERE UserRoles.users_for_login_id = :loginId")
   // List<String> findRoleNamesByLoginId(@Param("loginId") Integer loginId);

}
