package com.reljicd.service;

import com.reljicd.model.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findById(Long id);

    Optional<Role> findByRole(String role);

    Role saveAndFlush(Role role);

    void delete(Role role);
}
