package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDAOimpl implements USERDAO {

    private static final String getUserById = "SELECT * FROM USERDATA WHERE ID = ?;";
    private static final String createUser = "INSERT INTO USERDATA(ID, SURNAME, NAME, SECONDNAME, POSITION, DEPARTMENT, SALARY) VALUES(?,?,?,?,?,?,?);";
    private static final String changeInfo = "UPDATE USERDATA SET SURNAME = ?, NAME = ?, SECONDNAME = ?, POSITION = ?, DEPARTMENT = ?, SALARY = ? WHERE ID = ?;";
    private static final String deleteUser = "DELETE FROM USERDATA WHERE ID = ?;";
    @Override
    public User getByID(int ID) {
        try (Connection connection = DBConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(getUserById);
            statement.setInt(1,ID);
            ResultSet result = statement.executeQuery();
            result.next();

            User user = new User();
            user.setId(result.getInt("ID"));
            user.setSurname(result.getString("SURNAME"));
            user.setName(result.getString("NAME"));
            user.setSecondname(result.getString("SECONDNAME"));
            user.setPosition(result.getString("POSITION"));
            user.setDepartment(result.getString("DEPARTMENT"));
            user.setSalary(result.getInt("SALARY"));

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createUser(User user) {
        try(Connection connection = DBConnector.getConnection()){
            PreparedStatement insert = connection.prepareStatement(createUser);
            insert.setInt(1,user.getId());
            insert.setString(2,user.getSurname());
            insert.setString(3,user.getName());
            insert.setString(4,user.getSecondname());
            insert.setString(5,user.getPosition());
            insert.setString(6,user.getDepartment());
            insert.setInt(7,user.getSalary());
            insert.execute();
            System.out.println("User added");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeInfo(int ID) {
        try (Connection connection = DBConnector.getConnection()){
            PreparedStatement change = connection.prepareStatement(changeInfo);
            change.setInt(7,ID);
            User user = new User(getByID(ID));


            System.out.println("Введите какое поле вы хотите изменить: ");
            Thread.sleep(1000);
            System.out.println("1 - Фамилию, 2 - Имя, 3 - Отчество, 4 - Должность, 5 - Отдел, 6 - Зарплату, 0 - Выход");

            Integer[] array = {1, 2, 3, 4, 5, 6};
            List<Integer> list = new ArrayList<>();
            list.addAll(Arrays.asList(array));
            Scanner scanner = new Scanner(System.in);
            int var = scanner.nextInt();
            while (var != 0){
                switch (var) {
                    case 1 -> {
                        System.out.println("Введите новую фамилию: ");
                        list.remove(0);
                        change.setString(1, scanner.next());
                    }
                    case 2 -> {
                        System.out.println("Введите новое имя: ");
                        list.remove(1);
                        change.setString(2, scanner.next());
                    }
                    case 3 -> {
                        System.out.println("Введите новое Отчество: ");
                        list.remove(2);
                        change.setString(3, scanner.next());
                    }
                    case 4 -> {
                        System.out.println("Введите новую должность: ");
                        list.remove(3);
                        change.setString(4, scanner.next());
                    }
                    case 5 -> {
                        System.out.println("Введите новый отдел: ");
                        list.remove(4);
                        change.setString(5, scanner.next());
                    }
                    case 6 -> {
                        System.out.println("Введите новую зарплату: ");
                        list.remove(5);
                        change.setInt(6, scanner.nextInt());
                    }
                }
                System.out.println("Что еще изменить? ");
                System.out.println("1 - Фамилию, 2 - Имя, 3 - Отчество, 4 - Должность, 5 - Отдел, 6 - Зарплату, 0 - Выход");
                var = scanner.nextInt();
            }

            for (var variable : list) {
                switch (variable){
                    case 1 -> {
                        change.setString(1,user.getSurname());
                    }
                    case 2 -> {
                        change.setString(2,user.getName());
                    }
                    case 3 -> {
                        change.setString(3,user.getSecondname());
                    }
                    case 4 -> {
                        change.setString(4,user.getPosition());
                    }
                    case 5 -> {
                        change.setString(5,user.getDepartment());
                    }
                    case 6 -> {
                        change.setInt(6,user.getSalary());
                    }
                }
            }
            System.out.println("User info changed");
            change.execute();
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int ID) {
        try(Connection connection = DBConnector.getConnection()) {
            PreparedStatement delete = connection.prepareStatement(deleteUser);
            delete.setInt(1,ID);
            delete.execute();
            System.out.println("User deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
