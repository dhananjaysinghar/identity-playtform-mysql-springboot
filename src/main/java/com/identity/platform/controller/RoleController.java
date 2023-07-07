package com.identity.platform.controller;

import com.identity.platform.dto.RoleRequestDto;
import com.identity.platform.dto.RoleResponseDto;
import com.identity.platform.service.RoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public RoleResponseDto createRole(@RequestBody RoleRequestDto role) {
        return roleService.createRole(role);
    }

    @GetMapping("/{roleId}")
    public RoleResponseDto getRole(@PathVariable String roleId) {
        return roleService.getRoleDto(roleId);
    }

    @GetMapping
    public List<RoleResponseDto> getAllRoles() {
        return roleService.getAllRoles();
    }


    @DeleteMapping("/{roleId}")
    public RoleResponseDto inActivateRole(@PathVariable String roleId) {
        return roleService.inActivateRole(roleId);
    }

}
