package com.ohigiraffers.config;

import com.ohigiraffers.project.config.JDBCConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JDBCConnectionTest {

    @BeforeAll
    static void setUp() {
        System.out.println("테스트 시작");
    }

    @Test
    void testConnection() throws SQLException {
        try (Connection con = JDBCConnection.getConnection()) {
            assertNotNull(con, "connection이 생성되지 않았습니다.");
            assertFalse(con.isClosed(), " 커낵션은 열려있어야 합니다.");
            System.out.println("DB 커넥션이 연결되었습니다.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void tearDown() {
        System.out.println("테스트 종료 및 자원 반납");
        JDBCConnection.close();
    }
}
