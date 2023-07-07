package com.identity.platform.service;

import com.identity.platform.entity.Role;
import com.identity.platform.entity.Tenant;
import com.identity.platform.entity.User;
import com.identity.platform.entity.enums.Status;
import com.identity.platform.repository.RoleRepository;
import com.identity.platform.repository.TenantRepository;
import com.identity.platform.repository.UserGroupRepository;
import com.identity.platform.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final TenantRepository tenantRepository;
    private final UserGroupRepository userGroupRepository;


    @PostConstruct

    public void init() {

        String roleOwner = "OWNER";
        Optional<Role> owner = roleRepository.findRoleByName(roleOwner);
        if (owner.isPresent()) {
            // CREATE ADMIN, CONTRIBUTOR, READER Roles.
            List<Role> roles = List.of(Role.builder().roleId(UUID.randomUUID().toString()).name(roleOwner).build(), Role.builder().roleId(UUID.randomUUID().toString()).name("CONTRIBUTOR").build(), Role.builder().roleId(UUID.randomUUID().toString()).name("READER").build());
            roleRepository.saveAll(roles);

            // CREATE DEFAULT TENANT
            Tenant tenant = Tenant.builder().tenantId(UUID.randomUUID().toString())
                    .tenantName("DEFAULT_TENANT").status(Status.ACTIVE).build();

            // CREATE AN ADMIN USER
            List<Role> userRole = new ArrayList<>();
            userRole.add(roleRepository.findRoleByName(roleOwner).get());

            User user = User.builder().userId(UUID.randomUUID().toString())
                    .name("DEFAULT_USER").phoneNumber("0000000000").email("default.idm@gmail.com").password("Password@123").roles(userRole)
                    .tenants(null).status(Status.ACTIVE).build();
            userRepository.save(user);




        }
    }
}
