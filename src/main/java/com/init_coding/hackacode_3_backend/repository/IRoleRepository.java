package com.init_coding.hackacode_3_backend.repository;

import com.init_coding.hackacode_3_backend.model.RoleEntity;
import com.init_coding.hackacode_3_backend.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {

    List<RoleEntity> findByRoleEnumIn(List<String> roleEnums);

}
