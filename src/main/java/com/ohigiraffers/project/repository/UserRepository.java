package com.ohigiraffers.project.repository;

import com.ohigiraffers.project.model.User;
import com.ohigiraffers.project.model.dto.RankingDto;
import com.ohigiraffers.project.model.dto.UserDto;

import java.sql.Connection;
import java.util.List;

public interface UserRepository {

    void save(Connection con , User user) ;

    boolean findEmail(Connection con  , String find) ;

    boolean findPassword(Connection con  , String find) ;

    long findId(Connection con, String email, String password);

    List<UserDto> findAll(Connection con);
}
