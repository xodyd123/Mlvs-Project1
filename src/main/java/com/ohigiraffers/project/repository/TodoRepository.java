package com.ohigiraffers.project.repository;

import com.ohigiraffers.project.model.dto.*;
import com.ohigiraffers.project.model.TodoList;

import java.sql.Connection;
import java.util.List;
import java.util.PriorityQueue;

public interface TodoRepository {
    void save(Connection con, TodoList todoList) ;

    void update(Connection con , UpdateDto updateDto)   ;

    void delete(Connection con, DeleteDto deleteDto) ;

    List<ReadDto> read(Connection con, long user_id)  ;

    List<ReadDto> readCategory(Connection con , long user_id , long category_id) ;

    List<ReadDto> readStatus(Connection con , long user_id , Enum status) ;

    void updateStatus(Connection con, UpdateDto updateDto) ;

    List<RankingDto> rank(Connection con, List<UserDto> id) ;
}
