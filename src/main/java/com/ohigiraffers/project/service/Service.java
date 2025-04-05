package com.ohigiraffers.project.service;

import com.ohigiraffers.project.config.JDBCConnection;
import com.ohigiraffers.project.model.*;

import com.ohigiraffers.project.model.dto.*;
import com.ohigiraffers.project.repository.TodoRepository;
import com.ohigiraffers.project.repository.UserRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Service {
    private final Scanner scanner = new Scanner(System.in);

    private final UserRepository userRepository ;

    private final TodoRepository todoRepository ;


    public Service(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;

    }

    public void connect() {
        try {
            Connection con  = JDBCConnection.getConnection();
            System.out.println("TODOLIST");
            System.out.println("1.희원가입 , 2.로그인");
            int num = scanner.nextInt();
            scanner.nextLine();
            switch (num) {
                case 1 :
                    createAccount(con);
                    con.close();
                    break;
                case 2 :
                    login(con);
                    con.close();
                    break;
            }

        } catch (SQLException e) {
            System.out.println("서버 오류가 발생하였습니디.");
            //throw new RuntimeException(e);
        }
    }

    public void createAccount(Connection con)  {
        System.out.println("이름을 입력하세요");
        String str = scanner.nextLine();
        User user = new User();
        while (true) {
            if (str.length() != 0) {
                user.setUsername(str);  // 사용자 이름 설정
                System.out.println("이메일을 입력하세요");
                String find = scanner.nextLine();  // 이메일 입력받기

                if (!userRepository.findEmail(con, find)) {  // 이메일 중복 확인
                    user.setEmail(find);  // 이메일 설정
                    while (true) {
                        System.out.println("비밀번호를 입력하세요");
                        String password = scanner.nextLine();  // 비밀번호 입력받기

                        if (!userRepository.findPassword(con, password)) {  // 비밀번호 중복 확인
                            user.setPassword(password);  // 비밀번호 설정
                            System.out.println("회원가입이 완료되었습니다.");
                            userRepository.save(con, user);  // 사용자 저장
                            break;
                        } else {
                            System.out.println("이미 사용 중인 비밀번호입니다. 다시 입력하세요");
                        }
                    }
                    break;
                } else {
                    System.out.println("이미 사용 중인 이메일입니다. 다시 입력하세요");
                }
            } else {
                System.out.println("이름을 입력하지 않았습니다. 다시 입력해주세요");
            }
        }
    }
    private void login(Connection con)  {
        while (true) {
            System.out.println("이메일을 입력하세요");
            String userEmail  = scanner.nextLine();
            System.out.println("비밀번호를 입력하세요");
            String userPassword = scanner.nextLine();
            long id = userRepository.findId(con, userEmail, userPassword);
            if (id > 0) {
               System.out.println("로그인 성공");
               LoginMain(con , id);
               break;
           }
            else {
                System.out.println("이메일이나 비밀번호가 일치하지 않습니다.");
            }
        }
    }

    private void LoginMain(Connection con, long userId) {
        while (true) {
            System.out.println("1.할 일 등록 , 2.할 일 목록 조회 , 3.할 일 수정 , 4.할 일 삭제 , 5.카테고리 별 조회 , 6.랭킹 보기 , 7.종료하기");
            int num = 0 ;
            try{
                num = scanner.nextInt();
                scanner.nextLine();
                if (num  < 1 || num >7) {
                    throw new NoSuchElementException("1번 ~ 7번만 입력 가능합니다.")  ;
                }
            }catch (NoSuchElementException e){
                System.out.println("e.getMessage() = " + e.getMessage());
               // System.out.println(e.getMessage());
                scanner.nextLine();
            }
            if(num == 1) {
                System.out.println("카테고리 종류를 입력해주세요");
                System.out.println("1.운동 , 2.업무 , 3.공부");
                int index = 0 ;
                try {
                    index = scanner.nextInt();
                    if (index < 1 || index > 3) {
                        scanner.nextLine();
                        throw new NoSuchElementException("1번 ,2번 ,3번만 입력가능합니다.");
                    }
                } catch (NoSuchElementException e) {
                    System.out.println(e.getMessage());

                }finally {
                    scanner.nextLine();
                }
                System.out.println("할 일을 입력하세요.");
                String text = scanner.nextLine();
                System.out.println("등록 되었습니다.");
                todoRepository.save(con, new TodoList(LocalDateTime.now(), Status.INCOMPLETE, userId, text, index));


            }  else if(num == 2) {
                List<ReadDto> read = read(con, userId);
                if (read.size() == 0) {
                    System.out.println("할 일 목록이 존재하지 않습니다.");
                }

            } else if (num == 3) {
                System.out.println("1.할 일 수정하기 , 2.상태 수정하기");
                try{
                    int index = scanner.nextInt();
                    if(index < 1 || index >2) {
                        throw new NoSuchElementException("1번 아니면 2번만 입력가능합니다.");
                    }
                    else if(index == 1) {
                        List<ReadDto> read = read(con, userId);
                        if (read.size() > 0) {
                            try {
                                System.out.println("할 일 목록의 번호를 입력해주세요.");
                                int taskId = scanner.nextInt();
                                if(taskId > read.size()) {
                                    throw new IndexOutOfBoundsException();
                                }
                                scanner.nextLine();
                                System.out.println("수정 해주세요");
                                String text = scanner.next();
                                todoRepository.update(con, new UpdateDto(read.get(taskId - 1).getTaskId(), text, LocalDateTime.now()));
                                System.out.println("수정 완료 되었습니다.");
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("수정 할 목록의 번호가 존재하지 않습니다.");

                            } catch (NoSuchElementException e) {
                                System.out.println("숫자만 입력 가능합니다.");

                            }
                        } else {
                            System.out.println("수정할 것이 없습니다. 할 일을 등록해주세요");
                        }

                    }else if(index == 2) {
                        System.out.println("1.미 완료 업무를 완료로 바꾸기 , 2.완료 업무를 미 완료료 바꾸기");
                        System.out.println("번호를 입력해주세요");
                        int num2 = 0 ;
                        try{
                            num2 = scanner.nextInt();
                            if (num2 != 1 && num2 != 2) {
                                throw new NoSuchElementException()  ;
                            }
                        }catch (NoSuchElementException e){
                            System.out.println("1번 ~ 2번만 입력 가능합니다.");


                        }

                        if(num2 == 1) {
                            System.out.println("미완료 목록");
                            List<ReadDto> readDtos = todoRepository.readStatus(con, userId, Status.INCOMPLETE);
                            for(int i = 0 ; i<readDtos.size(); i++) {
                                System.out.println(i+1 + ": " + readDtos.get(i).getContents());
                            }

                            if (readDtos.size() > 0) {
                                try {
                                    System.out.println("완료료 변경할 목록의 번호를 입력해주세요.");
                                    int taskId = scanner.nextInt();
                                    if(taskId > readDtos.size()) {
                                        throw new IndexOutOfBoundsException();
                                    }

                                    todoRepository.updateStatus(con , new UpdateDto(readDtos.get(taskId - 1).getTaskId(), LocalDateTime.now() , Status.COMPLETED));
                                    System.out.println("수정 완료 되었습니다.");
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println("수정 할 목록의 번호가 존재하지 않습니다.");

                                } catch (NoSuchElementException e) {
                                    System.out.println("숫자만 입력 가능합니다.");

                                }

                            } else {
                                System.out.println("수정할 것이 없습니다. 할 일을 등록해주세요");
                            }

                        } else if (num2 == 2) {
                            System.out.println("완료 목록");
                            List<ReadDto> readDtos = todoRepository.readStatus(con, userId, Status.COMPLETED);
                            for(int i = 0 ; i<readDtos.size(); i++) {
                                System.out.println(i+1 + ": " + readDtos.get(i).getContents());
                            }
                            if (readDtos.size() > 0) {
                                try {
                                    System.out.println("미완료료 변경할 목록의 번호를 입력해주세요.");
                                    int taskId = scanner.nextInt();
                                    if(taskId > readDtos.size()) {
                                        throw new IndexOutOfBoundsException();
                                    }
                                    todoRepository.updateStatus(con , new UpdateDto(readDtos.get(taskId - 1).getTaskId(), LocalDateTime.now() , Status.INCOMPLETE));
                                    System.out.println("수정 완료 되었습니다.");
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println("수정 할 목록의 번호가 존재하지 않습니다.");

                                } catch (NoSuchElementException e) {
                                    System.out.println("숫자만 입력 가능합니다.");

                                }

                            } else {
                                System.out.println("수정할 것이 없습니다. 할 일을 등록해주세요");
                            }
                        }

                    }
                }catch (NoSuchElementException e) {
                    System.out.println("1~2번만 입력 가능합니다.");
                }finally {
                    scanner.nextLine();
                }
            } else if (num == 4) {
                List<ReadDto> read = read(con, userId);

                if(read.size() > 0) {
                    System.out.println("삭제 할 일의 번호를 입력해주세요");
                    try{
                        int taskId = scanner.nextInt() ;
                        todoRepository.delete(con, new DeleteDto(read.get(taskId - 1).getUserId(), read.get(taskId - 1).getTaskId()));
                        System.out.println("삭제 완료 되었습니다.");
                    }
                    catch (IndexOutOfBoundsException e){
                        System.out.println("삭제할 번호와 일치하지 않습니다.");

                    }catch (NoSuchElementException e){
                        System.out.println("숫자만 입력 가능합니다.");

                    }

                }else  {
                    System.out.println("더 이상 삭제할 것이 없습니다. 할 일을 등록해주세요");
                }
            } else if (num == 5) {
                System.out.println("카테고리 종류를 입력해주세요");
                System.out.println("1.운동 , 2.업무 , 3.공부");
                int index = 0;
                try {
                    index = scanner.nextInt();
                    if (index < 1 || index > 3) {
                        scanner.nextLine();
                        throw new NoSuchElementException();
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("1번 , 2번 , 3번만 입력가능합니다.");

                } finally {
                    scanner.nextLine();
                }
                List<ReadDto> dto = todoRepository.readCategory(con, userId, index);
                if (dto.size() == 0) {
                    System.out.println("카테고리 조회할 목록이 존재하지 않습니다.");
                    continue;
                }

                for(int i = 0 ; i < dto.size() ; i++) {
                    System.out.println( i+1 +" 카테고리 명 : " + dto.get(i).getCategoryContets() + " , 할 일 :" + dto.get(i).getContents());
                }

            } else if (num == 6) {
                List<UserDto> userDtos = userRepository.findAll(con);
                List<RankingDto> rank = todoRepository.rank(con, userDtos);

                for(int i = 0 ; i<rank.size() ; i++) {
                    System.out.println((i+1)+ "등 이름 : " + rank.get(i).getName() + ", 달성률 : " + rank.get(i).getSuccessRate() +"%"+ " 총 "
                            +  rank.get(i).getTotal() + "개 할일 중에 " + rank.get(i).getCompleted() + "개 완료");
                }

            }
            else if (num == 7) {
                System.out.println("종료 되었습니다.");
                break;
            }
        }
    }
    private List<ReadDto> read(Connection con, long userId) {
        List<ReadDto> read = todoRepository.read(con, userId);
        for (int i = 0 ; i < read.size() ; i++) {
            System.out.println( i+1 +". 할 일 : " +read.get(i).getContents());
        }
        return read;
    }



}
