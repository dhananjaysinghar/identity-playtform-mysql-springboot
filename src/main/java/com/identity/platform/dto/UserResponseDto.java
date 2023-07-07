package com.identity.platform.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String status;
    private List<RoleResponseDto> roles;
    private List<TenantResponseDto> tenants;
    private List<UserGroupResponseDto> groups;
}
