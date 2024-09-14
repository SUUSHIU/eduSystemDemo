package com.example.edusystem.dao;

import com.example.edusystem.dto.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//dao层直接接触dto，dto是数据库的映射，对应表和字段。

@Repository
public interface AccountDao extends JpaRepository<Account,Long> {
    Account findByLoginName(String loginName);

    //因为名字重复，通过学号去查数据库？
    //同一个名字对应多个密码时，只要match上就可以登录？
    //要求学生填写注册名时唯一，可以不是真实姓名？
}

