package com.ohigiraffers.project;

import com.ohigiraffers.project.config.JDBCConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    Connection con ;

    @BeforeEach
    public void before() throws SQLException {
        con = JDBCConnection.getConnection() ;
        con.setAutoCommit(false);
    }



    @Test
    public void insert() throws SQLException {
        // given


        String createTableSQL = "CREATE TABLE  mango ("
                + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                + "nickname VARCHAR(50) NOT NULL, "
                + "password VARCHAR(255) NOT NULL"
                + ")";

        PreparedStatement ps = con.prepareStatement(createTableSQL);
        ps.executeUpdate();
        // 5. SQL 쿼리 실행

        System.out.println("테이블이 성공적으로 생성되었습니다.");


    }

    @AfterEach
    public void after() throws SQLException {
        con.rollback();
    }

}