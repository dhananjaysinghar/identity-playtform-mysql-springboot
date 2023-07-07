package com.identity.platform.service;

import com.identity.platform.dto.RoleRequestDto;
import com.identity.platform.dto.RoleResponseDto;
import com.identity.platform.entity.Role;
import com.identity.platform.entity.enums.Status;
import com.identity.platform.exception.IdentityPlatformException;
import com.identity.platform.repository.RoleRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleResponseDto createRole(RoleRequestDto role) {
        if (roleRepository.findRoleByName(role.getName()).isPresent()) {
            throw new IdentityPlatformException(HttpStatus.CONFLICT, "Role already exist");
        }
        return Optional.ofNullable(Role.roleFromRoleDto(role))
                .map(roleRepository::save)
                .map(Role::roleDtoFromRole)
                .orElseThrow();
    }

    public RoleResponseDto getRoleDto(String roleId) {
        return getRole(roleId)
                .map(Role::roleDtoFromRole)
                .orElseThrow();
    }

    public Optional<Role> getRole(String roleId) {
        return roleRepository.findById(roleId);
    }

    public List<RoleResponseDto> getAllRoles() {
        return roleRepository
                .findAll().stream()
                .map(Role::roleDtoFromRole)
                .collect(Collectors.toList());
    }

    public RoleResponseDto inActivateRole(String roleId) {
        return roleRepository.findById(roleId)
                .stream().peek(e -> e.setStatus(Status.INACTIVE))
                .map(Role::roleDtoFromRole)
                .findFirst()
                .orElseThrow();
    }
}
