package com.identity.platform.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupResponseDto {
    private String groupId;
    private String groupName;
    private List<RoleResponseDto> roles;
    private String status;
}
