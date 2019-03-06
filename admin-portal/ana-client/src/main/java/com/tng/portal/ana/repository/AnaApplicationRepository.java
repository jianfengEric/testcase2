package com.tng.portal.ana.repository;


import com.tng.portal.ana.entity.AnaApplication;
import com.tng.portal.ana.entity.AnaFunction;
import com.tng.portal.ana.entity.AnaRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Zero on 2016/11/10.
 */
@Repository
public interface AnaApplicationRepository extends JpaRepository<AnaApplication,String>{
    AnaApplication findByAnaFunctions(AnaFunction anaFunction);
    AnaApplication findByAnaRoles(AnaRole anaRoles);
    AnaApplication findByCode(String code);
}
