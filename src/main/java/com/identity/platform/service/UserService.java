package com.identity.platform.service;

import com.identity.platform.dto.UserRequestDto;
import com.identity.platform.dto.UserResponseDto;
import com.identity.platform.entity.Role;
import com.identity.platform.entity.User;
import com.identity.platform.entity.UserGroup;
import com.identity.platform.entity.enums.Status;
import com.identity.platform.exception.IdentityPlatformException;
import com.identity.platform.repository.UserRepository;
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
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    private final UserGroupService userGroupService;

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        log.info("Calling repository to save user: {}", userRequestDto);
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new IdentityPlatformException(HttpStatus.CONFLICT, "User already exist");
        }
        return Optional.ofNullable(User.getUserFromUserDto(userRequestDto)).map(userRepository::save).map(User::getUserDtoFromUser).orElseThrow();
    }


    public UserResponseDto getUser(String userId) {
        log.info("Calling repository to get user by id: {}", userId);
        Optional<User> byId = userRepository.findById(userId);
        if (byId.isEmpty()) {
            throw new IdentityPlatformException(HttpStatus.NOT_FOUND, "User not exist");
        }
        return byId.map(User::getUserDtoFromUser).orElseThrow();
    }

    public List<UserResponseDto> getAllUsers() {
        log.info("Calling repository to get all users");
        return userRepository.findAll().stream().map(User::getUserDtoFromUser).collect(Collectors.toList());
    }

    public UserResponseDto inActivateUser(String userId) {
        log.info("Calling repository to inactivate an users: {}", userId);
        if (userRepository.findById(userId).isEmpty()) {
            throw new IdentityPlatformException(HttpStatus.NOT_FOUND, "User not exist");
        }
        return userRepository.findById(userId).stream()
                .peek(e -> e.setStatus(Status.INACTIVE))
                .map(userRepository::save)
                .map(User::getUserDtoFromUser)
                .findFirst()
                .orElseThrow();
    }

    public UserResponseDto addUserToRole(String userId, String roleId) {
        Optional<Role> role = roleService.getRole(roleId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (role.isPresent() && userOptional.isPresent()) {
            ArrayList<Role> roleArrayList = new ArrayList<>();
            roleArrayList.add(role.get());
            User user = userOptional.get();
            user.setRoles(roleArrayList);
            return Optional.of(userRepository.saveAndFlush(user))
                    .map(User::getUserDtoFromUser).orElseThrow();
        }
        throw new IdentityPlatformException(HttpStatus.NOT_FOUND, "userId/roleId not exist");
    }

    public UserResponseDto addUserToUserGroup(String userId, String userGroupId) {
        Optional<UserGroup> userGroupEntity = userGroupService.getUserGroupEntity(userGroupId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (userGroupEntity.isPresent() && userOptional.isPresent()) {
            ArrayList<UserGroup> userGroups = new ArrayList<>();
            userGroups.add(userGroupEntity.get());
            User user = userOptional.get();
            user.setGroups(userGroups);
            return Optional.of(userRepository.saveAndFlush(user))
                    .map(User::getUserDtoFromUser).orElseThrow();
        }
        throw new IdentityPlatformException(HttpStatus.NOT_FOUND, "userId/userGroupId not exist");
    }
}
