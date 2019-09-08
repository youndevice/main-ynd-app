package com.youndevice.app.repository;

import com.youndevice.app.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByAuthority(String authority);

    Long countByAuthority(String authority);
}
