package com.example.edusystem.dao;

import com.example.edusystem.dto.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//dao层直接接触dto，dto是数据库的映射，对应表和字段。

@Repository
public interface AccountDao extends JpaRepository<Account, Long> {
    Account findByStudentNumber(String studentNumber);

}

