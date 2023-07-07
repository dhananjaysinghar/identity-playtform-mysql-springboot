package com.identity.platform.controller;

import com.identity.platform.dto.UserRequestDto;
import com.identity.platform.dto.UserResponseDto;
import com.identity.platform.service.UserService;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @GetMapping("/{userId}")
    public UserResponseDto getUserById(@PathVariable String userId) {
        return userService.getUser(userId);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public UserResponseDto inActivateUser(@PathVariable String id) {
        return userService.inActivateUser(id);
    }

    @PatchMapping("/add-role/{userId}/{roleId}")
    public UserResponseDto addUserToRole(@PathVariable String userId, @PathVariable String roleId) {
        return userService.addUserToRole(userId, roleId);
    }
    @PatchMapping("/add-group/{userId}/{userGroupId}")
    public UserResponseDto addUserToUserGroup(@PathVariable String userId, @PathVariable String userGroupId) {
        return userService.addUserToUserGroup(userId, userGroupId);
    }
}
