package com.ohigiraffers.project.service;

import com.ohigiraffers.project.repository.TodoRepositoryV1;
import com.ohigiraffers.project.repository.UserRepository;
import com.ohigiraffers.project.repository.UserRepositoryV1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ToDoServiceTest {

    LoginServiceV1 service ;

    UserRepository userRepository = new UserRepositoryV1() ;

    @BeforeEach
    public void before() {
        service = new LoginServiceV1(new TodoRepositoryV1(), new UserRepositoryV1() {
        });
    }

    @Test
    public void save() {
        // given
        service.connect();

        // when

    }

}