package com.ohigiraffers.project;

import com.ohigiraffers.project.repository.TodoJdbcRepository;
import com.ohigiraffers.project.repository.UserJDBCRepository;
import com.ohigiraffers.project.service.Service;

public class Application {
    public static void main(String[] args) {
        Service service = new Service(new UserJDBCRepository() , new TodoJdbcRepository());
        service.connect(); ;

    }
}
