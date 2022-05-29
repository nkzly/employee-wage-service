package com.cloudmore.task.repository;

import com.cloudmore.task.entity.EmployeeWageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeWageRepository extends JpaRepository<EmployeeWageEntity, String> {

}
