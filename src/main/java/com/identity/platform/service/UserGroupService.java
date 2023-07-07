package com.identity.platform.service;

import com.identity.platform.dto.UserGroupRequestDto;
import com.identity.platform.dto.UserGroupResponseDto;
import com.identity.platform.entity.Role;
import com.identity.platform.entity.UserGroup;
import com.identity.platform.entity.enums.Status;
import com.identity.platform.exception.IdentityPlatformException;
import com.identity.platform.repository.UserGroupRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserGroupService {

    private final UserGroupRepository userGroupRepository;
    private final RoleService roleService;


    public UserGroupResponseDto createUserGroup(UserGroupRequestDto requestDto) {
        log.info("Calling repository to save userGroup: {}", requestDto);
        if (userGroupRepository.findUserGroupByGroupName(requestDto.getGroupName()).isPresent()) {
            throw new IdentityPlatformException(HttpStatus.CONFLICT, "UserGroup already exist");
        }
        return Optional.ofNullable(UserGroup.userGroupFromDto(requestDto))
                .map(userGroupRepository::save)
                .map(UserGroup::userGroupResponseDtoFromUserGroup)
                .orElseThrow();
    }

    public UserGroupResponseDto getUserGroup(String userGroupId) {
        log.info("Calling repository to get userGroup: {}", userGroupId);
        Optional<UserGroup> userGroupEntity = getUserGroupEntity(userGroupId);
        if (userGroupEntity.isEmpty()) {
            throw new IdentityPlatformException(HttpStatus.NOT_FOUND, "UserGroup not exist");
        }
        return userGroupEntity
                .map(UserGroup::userGroupResponseDtoFromUserGroup)
                .orElseThrow();
    }

    public Optional<UserGroup> getUserGroupEntity(String userGroupId) {
        return userGroupRepository.findById(userGroupId);
    }

    public List<UserGroupResponseDto> getAllUserGroups() {
        return userGroupRepository.findAll()
                .stream()
                .map(UserGroup::userGroupResponseDtoFromUserGroup)
                .collect(Collectors.toList());
    }

    public UserGroupResponseDto inActivateUserGroup(String userGroupId) {
        log.info("Calling repository to inactivate an usersGroup: {}", userGroupId);
        Optional<UserGroup> byId = userGroupRepository.findById(userGroupId);
        if (byId.isEmpty()) {
            throw new IdentityPlatformException(HttpStatus.NOT_FOUND, "UserGroup not exist");
        }
        return byId.stream()
                .peek(e -> e.setStatus(Status.INACTIVE))
                .map(userGroupRepository::save)
                .map(UserGroup::userGroupResponseDtoFromUserGroup)
                .findFirst()
                .orElseThrow();
    }

    public UserGroupResponseDto addRolesToUserGroup(String userGroupId, String roleId) {
        Optional<Role> role = roleService.getRole(roleId);
        Optional<UserGroup> userOptional = userGroupRepository.findById(userGroupId);
        if (role.isPresent() && userOptional.isPresent()) {
            ArrayList<Role> roleArrayList = new ArrayList<>();
            roleArrayList.add(role.get());
            UserGroup userGroup = userOptional.get();
            userGroup.setRoles(roleArrayList);
            return Optional.of(userGroupRepository.saveAndFlush(userGroup))
                    .map(UserGroup::userGroupResponseDtoFromUserGroup)
                    .orElseThrow();
        }
        throw new IdentityPlatformException(HttpStatus.NOT_FOUND, "userGroupId/roleId not exist");
    }
}
