package com.example.edusystem.dao;

import com.example.edusystem.dto.CourseCategory;
import com.example.edusystem.dto.GroupClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupClassDao extends JpaRepository<GroupClass, Long> {
    List<GroupClass> findByStudentNumber(String studentNumber);

    List<GroupClass> findByStudentNumberIn(List<String> studentNumberList);

}
