package com.identity.platform.entity;

import com.identity.platform.dto.RoleRequestDto;
import com.identity.platform.dto.RoleResponseDto;
import com.identity.platform.entity.enums.Status;
import com.identity.platform.utils.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Role {
    @Id
    @Column(name = Constants.ROLE_ID)
    private String roleId;
    private String name;
    private Status status;

    public static Role roleFromRoleDto(RoleRequestDto requestDto) {
        return Role.builder()
                .roleId(UUID.randomUUID().toString())
                .name(requestDto.getName())
                .status(Status.ACTIVE)
                .build();
    }

    public static RoleResponseDto roleDtoFromRole(Role role) {
        return RoleResponseDto.builder()
                .roleId(role.getRoleId())
                .name(role.getName())
                .status(role.getStatus().toString())
                .build();
    }
}
