package com.example.edusystem.dao;

import com.example.edusystem.dto.GroupClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupClassDao extends JpaRepository<GroupClass, Long> {

//    List<GroupClass> findByStudentNumberListIn(List<String> studentNumberList);

    @Query("SELECT gc FROM GroupClass gc JOIN gc.studentNumberList sn WHERE sn IN :studentNumberList")
    List<GroupClass> findByStudentNumberListIn(@Param("studentNumberList") List<String> studentNumberList);


}
