package com.identity.platform.dto;

import com.identity.platform.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenantRequestDto {

    private String tenantId;
    private String tenantName;
    private Status status;

}
