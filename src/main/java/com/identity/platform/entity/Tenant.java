package com.identity.platform.entity;

import com.identity.platform.dto.TenantResponseDto;
import com.identity.platform.entity.enums.Status;
import com.identity.platform.utils.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Tenant {
    @Id
    @Column(name = Constants.TENANT_ID)
    private String tenantId;
    private String tenantName;
    private Status status;

    @ManyToMany
    @JoinTable(name = Constants.TENANT_GROUP_TABLE, joinColumns =
    @JoinColumn(name = Constants.TENANT_ID, referencedColumnName = Constants.TENANT_ID),
            inverseJoinColumns = @JoinColumn(name = Constants.GROUP_ID, referencedColumnName = Constants.GROUP_ID))
    private List<UserGroup> groups;

    public static Tenant fromTenantDto(TenantResponseDto tenantResponseDto) {
        return Tenant.builder()
                .tenantId(UUID.randomUUID().toString())
                .tenantName(tenantResponseDto.getTenantName())
                .status(Status.ACTIVE)
                .groups(List.of())
                .build();
    }

    public static TenantResponseDto fromTenant(Tenant tenant) {
        return TenantResponseDto.builder()
                .tenantId(tenant.getTenantId())
                .tenantName(tenant.getTenantName())
                .status(tenant.getStatus().toString())
                .groups(tenant.getGroups().stream().map(UserGroup::userGroupResponseDtoFromUserGroup).collect(Collectors.toList()))
                .build();
    }

}
