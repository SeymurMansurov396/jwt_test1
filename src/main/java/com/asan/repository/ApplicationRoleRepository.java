package com.asan.repository;

import com.asan.model.ApplicationUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRoleRepository extends JpaRepository<ApplicationUserRole,Long> {
    public ApplicationUserRole findByRole(String role);

}
