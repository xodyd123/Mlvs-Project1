package com.ohigiraffers.project.repository;

import com.ohigiraffers.project.model.Status;
import com.ohigiraffers.project.model.dto.*;
import com.ohigiraffers.project.model.TodoList;

import java.sql.*;
import java.util.*;

public class TodoJdbcRepository implements TodoRepository{
    @Override
    public void save(Connection con, TodoList todoList)  {
        PreparedStatement ps = null;
        String sql = "insert into task(user_id , contents , create_day , update_day , status, category_id) values (?,?,?,?,?,?)";
        try {
            ps  = con.prepareStatement(sql);
            ps.setLong(1 , todoList.getUserId());
            ps.setString(2 , todoList.getTask());
            ps.setTimestamp(3 , Timestamp.valueOf(todoList.getLocalDateTime()));
            ps.setTimestamp(4 , Timestamp.valueOf(todoList.getLocalDateTime()));
            ps.setString(5 , String.valueOf(todoList.getStatus()));
            ps.setLong(6 , todoList.getCategoryId());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                ps.close();
               // con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void update(Connection con, UpdateDto updateDto)  {
        PreparedStatement ps = null;
        String sql = "update task set contents = ?, update_day = ? where task_id = ?";
        try {
            ps  = con.prepareStatement(sql);
            ps.setString(1 , updateDto.getText());
            ps.setTimestamp(2 , Timestamp.valueOf(updateDto.getUpdated_at()));
            ps.setLong(3 , updateDto.getTask_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                ps.close();
                //con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void delete(Connection con, DeleteDto deleteDto)  {
        PreparedStatement ps = null;
        String sql = "delete from task where user_id=? and task_id=?";

        try {
            ps  = con.prepareStatement(sql);
            ps.setLong(1 , deleteDto.getUser_id());
            ps.setLong(2 , deleteDto.getProject_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                ps.close();
                // con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<ReadDto> read(Connection con, long user_id)  {
        PreparedStatement ps = null;
        List<ReadDto> list = new ArrayList<>();
        String sql = "select task_id ,contents from task where user_id=?";

        try {
            ps  = con.prepareStatement(sql);
            ps.setLong(1, user_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long task_id = rs.getLong(1);
                String content = rs.getString(2);
                list.add(new ReadDto(task_id , user_id , content));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                ps.close();
                //con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return list ;
    }

    @Override
    public List<ReadDto> readCategory(Connection con, long user_id, long category_id) {
        PreparedStatement ps = null;
        List<ReadDto> list = new ArrayList<>();
        String sql = "SELECT contents , title " +
                "FROM task " +
                "JOIN category ON task.category_id = category.category_id" +
                " WHERE task.user_id = ? AND category.category_id = ?";

        try {
            ps  = con.prepareStatement(sql);
            ps.setLong(1, user_id);
            ps.setLong(2, category_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String task = rs.getString(1);
                String content = rs.getString(2);
                list.add(new ReadDto(content , task));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                ps.close();
                //con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return list ;
    }

    @Override
    public List<ReadDto> readStatus(Connection con, long user_id, Enum status) {
        PreparedStatement ps = null;
        List<ReadDto> list = new ArrayList<>();
        String sql = "SELECT  task_id, contents " +
                "FROM task " +
                "WHERE task.user_id=? AND status=?";

        try {
            ps  = con.prepareStatement(sql);
            ps.setLong(1, user_id);
            ps.setString(2, String.valueOf(status));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long task_id = rs.getLong(1);
                String content = rs.getString(2);
                list.add(new ReadDto(task_id , user_id , content));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                ps.close();
                //con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return list ;
    }

    @Override
    public void updateStatus(Connection con, UpdateDto updateDto) {
        PreparedStatement ps = null;
        String sql = "update task set status = ?, update_day = ? where task_id = ?";
        try {
            ps  = con.prepareStatement(sql);
            ps.setString(1 , String.valueOf(updateDto.getStatus()));
            ps.setTimestamp(2 , Timestamp.valueOf(updateDto.getUpdated_at()));
            ps.setLong(3 , updateDto.getTask_id());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                ps.close();
                //con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<RankingDto> rank(Connection con, List<UserDto> userDtos) {
        PreparedStatement ps = null;
        String sql = "select status from task where user_id =?";
        List<RankingDto> list = new ArrayList<>();
        /*PriorityQueue<RankingDto> pq = new PriorityQueue<>((a,b) -> {
            if(b.getSuccessRate() > a.getSuccessRate()) {
                return 1;
            }
            else {
                return -1;
            }
        }
          );*/
        for (UserDto dto : userDtos) {
            RankingDto rankingDto = new RankingDto();
            rankingDto.setName(dto.getUsername());
            try {
                ps  = con.prepareStatement(sql);
                ps.setLong(1 , dto.getId());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String status = rs.getString(1);
                    rankingDto.increaseTotal(); ;
                    if (status.equals(String.valueOf(Status.COMPLETED))) {
                        rankingDto.increaseCompleted();
                    }
                }
                rankingDto.circulate();
                list.add(rankingDto);

            } catch (SQLException e) {

                System.out.println("서버 오류가 발생하였습니다.");
            }
            finally {
                try {
                    ps.close();
                    //con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }

       Collections.sort(list,(a, b) -> {
            if (b.getSuccessRate() > a.getSuccessRate()) {
                return 1;
            } else if (b.getSuccessRate() == a.getSuccessRate()) {
                if (b.getTotal() > a.getTotal()) {
                    return 1;
                } else {
                    return -1;
                }
            }
             else {
                return -1;
            }
        });


       // Collections.sort(list , new RankingDto());

        return list ;
    }
}
