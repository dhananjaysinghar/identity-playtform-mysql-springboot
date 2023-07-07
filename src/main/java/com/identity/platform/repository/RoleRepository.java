package com.identity.platform.repository;

import com.identity.platform.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findRoleByName(String name);
}
