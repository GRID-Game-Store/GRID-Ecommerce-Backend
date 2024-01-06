package com.khomsi.grid.main.user;

import com.khomsi.grid.main.user.model.entity.ERole;
import com.khomsi.grid.main.user.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
