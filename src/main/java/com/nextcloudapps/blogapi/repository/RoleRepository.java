package com.nextcloudapps.blogapi.repository;

import java.util.Optional;

import com.nextcloudapps.blogapi.model.role.Role;
import com.nextcloudapps.blogapi.model.role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RoleName name);
}
