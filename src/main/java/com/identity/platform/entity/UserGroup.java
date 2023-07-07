package com.identity.platform.entity;

import com.identity.platform.dto.UserGroupRequestDto;
import com.identity.platform.dto.UserGroupResponseDto;
import com.identity.platform.dto.UserRequestDto;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserGroup {
    @Id
    @Column(name = Constants.GROUP_ID)
    private String groupId;
    private String groupName;

    @ManyToMany
    @JoinTable(name = Constants.ROLE_GROUP_TABLE, joinColumns = @JoinColumn(name = Constants.GROUP_ID, referencedColumnName = Constants.GROUP_ID), inverseJoinColumns = @JoinColumn(name = Constants.ROLE_ID, referencedColumnName = Constants.ROLE_ID))
    private List<Role> roles;

    private Status status;


    public static UserGroup userGroupFromDto(UserGroupRequestDto userRequestDto) {
        return UserGroup.builder()
                .groupId(UUID.randomUUID().toString())
                .groupName(userRequestDto.getGroupName())
                .status(Status.ACTIVE)
                .roles(List.of())
                .build();
    }

    public static UserGroupResponseDto userGroupResponseDtoFromUserGroup(UserGroup userGroup) {
        return UserGroupResponseDto.builder()
                .groupId(userGroup.getGroupId())
                .groupName(userGroup.getGroupName())
                .status(userGroup.getStatus().toString())
                .roles(userGroup.getRoles().stream().map(Role::roleDtoFromRole).collect(Collectors.toList()))
                .build();
    }
}
