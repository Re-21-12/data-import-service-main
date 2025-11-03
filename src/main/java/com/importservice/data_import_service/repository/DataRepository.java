package com.importservice.data_import_service.repository;

import com.importservice.data_import_service.entity.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<DataEntity, Long> {
    boolean existsByEmail(String email);
}
