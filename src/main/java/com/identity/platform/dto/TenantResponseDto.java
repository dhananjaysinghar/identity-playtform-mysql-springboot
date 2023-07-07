package com.identity.platform.dto;

import com.identity.platform.entity.enums.Status;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenantResponseDto {
    private String tenantId;
    private String tenantName;
    private String status;
    private List<UserGroupResponseDto> groups;

}
