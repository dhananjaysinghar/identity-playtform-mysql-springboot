package com.identity.platform.entity;

import com.identity.platform.dto.UserRequestDto;
import com.identity.platform.dto.UserResponseDto;
import com.identity.platform.entity.enums.Status;
import com.identity.platform.utils.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
public class User {

    @Id
    @Column(name = Constants.USER_ID)
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = Constants.USER_ROLE_TABLE, joinColumns = @JoinColumn(name = Constants.USER_ID, referencedColumnName = Constants.USER_ID), inverseJoinColumns = @JoinColumn(name = Constants.ROLE_ID, referencedColumnName = Constants.ROLE_ID))
    private List<Role> roles;

    @ManyToMany
    @JoinTable(name = Constants.USER_TENANT_TABLE, joinColumns = @JoinColumn(name = Constants.USER_ID, referencedColumnName = Constants.USER_ID), inverseJoinColumns = @JoinColumn(name = Constants.TENANT_ID, referencedColumnName = Constants.TENANT_ID))
    private List<Tenant> tenants;


    @ManyToMany
    @JoinTable(name = Constants.USER_USER_GROUP_TABLE, joinColumns =
    @JoinColumn(name = Constants.USER_ID, referencedColumnName = Constants.USER_ID),
            inverseJoinColumns = @JoinColumn(name = Constants.GROUP_ID, referencedColumnName = Constants.GROUP_ID))
    private List<UserGroup> groups;

    public static User getUserFromUserDto(UserRequestDto userRequestDto) {
        return User.builder()
                .name(userRequestDto.getName())
                .userId(UUID.randomUUID().toString())
                .email(userRequestDto.getEmail())
                .password(new String(Base64.getEncoder()
                        .encode(userRequestDto.getPassword().getBytes(StandardCharsets.UTF_8))))
                .phoneNumber(userRequestDto.getPhoneNumber())
                .status(Status.ACTIVE)
                .roles(List.of())
                .groups(List.of())
                .tenants(List.of())
                .build();
    }

    public static UserResponseDto getUserDtoFromUser(User user) {
        return UserResponseDto.builder()
                .name(user.getName())
                .userId(user.getUserId())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus().toString())
                .roles(user.getRoles().stream().map(Role::roleDtoFromRole).collect(Collectors.toList()))
                .groups(user.getGroups().stream().map(UserGroup::userGroupResponseDtoFromUserGroup).collect(Collectors.toList()))
                .tenants(user.getTenants().stream().map(Tenant::fromTenant).collect(Collectors.toList()))
                .build();
    }
}
