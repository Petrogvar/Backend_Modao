package com.SpringProject.core.Repository;

import com.SpringProject.core.Entity.GroupsEntity;
import liquibase.pro.packaged.G;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsRepository extends JpaRepository<GroupsEntity, Long> {

}
