package com.reljicd.service.impl;

import com.reljicd.model.Role;
import com.reljicd.repository.RoleRepository;
import com.reljicd.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> findByRole(String role) {
        return roleRepository.findByRole(role);
    }

    @Override
    public Role saveAndFlush(Role role) {
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }
}
