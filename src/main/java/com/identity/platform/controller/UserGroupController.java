package com.identity.platform.controller;

import com.identity.platform.dto.UserGroupRequestDto;
import com.identity.platform.dto.UserGroupResponseDto;
import com.identity.platform.dto.UserRequestDto;
import com.identity.platform.service.UserGroupService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-groups")
@RequiredArgsConstructor
public class UserGroupController {

    private final UserGroupService userGroupService;

    @PostMapping
    public UserGroupResponseDto createUserGroup(@RequestBody UserGroupRequestDto requestDto) {
        return userGroupService.createUserGroup(requestDto);
    }

    @GetMapping("/{userGroupId}")
    public UserGroupResponseDto getUserGroup(@PathVariable String userGroupId) {
        return userGroupService.getUserGroup(userGroupId);
    }

    @GetMapping
    public List<UserGroupResponseDto> getAllUserGroups() {
        return userGroupService.getAllUserGroups();
    }

    @DeleteMapping("/{userGroupId}")
    public UserGroupResponseDto inActivateUserGroup(@PathVariable String userGroupId) {
        return userGroupService.inActivateUserGroup(userGroupId);
    }
    @PatchMapping("/{userGroupId}/{roleId}")
    public UserGroupResponseDto addRolesToUserGroup(@PathVariable String userGroupId, @PathVariable String roleId) {
        return userGroupService.addRolesToUserGroup(userGroupId, roleId);
    }
}
