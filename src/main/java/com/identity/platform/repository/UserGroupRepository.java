package com.identity.platform.repository;

import com.identity.platform.entity.UserGroup;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, String> {

    Optional<UserGroup> findUserGroupByGroupName(String groupName);

}
