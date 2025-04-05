package com.ohigiraffers.project.repository;

import com.ohigiraffers.project.model.User;
import com.ohigiraffers.project.model.dto.RankingDto;
import com.ohigiraffers.project.model.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserJDBCRepository implements UserRepository{
    @Override
    public void save(Connection con, User user) {

        String sql = "insert into USER(username, password ,email) values (?, ?,?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean findEmail(Connection con, String find) {

        PreparedStatement ps = null ;


        String sql = "select email from USER where email = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, find);
            ResultSet rs = ps.executeQuery();
            // 결과가 존재하면 true 반환
            if (rs.next()) {
                return true ;  // 이메일이 중복된 경우
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {

                ps.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return false;

    }

    @Override
    public boolean findPassword(Connection con, String find) {
        PreparedStatement ps = null ;

        String sql = "select ID,email from USER where password = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, find);

            ResultSet rs = ps.executeQuery();

            // 결과가 존재하면 true 반환
            if (rs.next()) {
                return true ;  // 이메일이 중복된 경우
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                //rs.close();
                ps.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return false;
    }

    @Override
    public long  findId(Connection con, String email , String password) {
        PreparedStatement ps = null ;

        String sql = "select ID from USER where email = ? and password = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID") ;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {

                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return -1 ;
    }

    @Override
    public List<UserDto> findAll(Connection con) {
        PreparedStatement ps = null ;
        List<UserDto> list = new ArrayList<>();
        String sql = "select ID , username from USER";
        try{
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long findId = rs.getLong(1);
                String name = rs.getString(2);
                list.add(new UserDto(findId, name));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list ;
    }


}
