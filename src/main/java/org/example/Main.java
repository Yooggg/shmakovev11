package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        printMenu();
        Scanner scanner = new Scanner(System.in);
        int var = scanner.nextInt();

        UserDAOimpl userDAOimpl = new UserDAOimpl();
        while (var != 0){
            switch (var){
                case 1 -> {
                    System.out.println("Введите ID пользователя: ");
                    int id = scanner.nextInt();
                    System.out.println(userDAOimpl.getByID(id));
                }
                case 2 -> {
                    System.out.println("Введите ID пользователя у которого нужно изменить данные: ");
                    int id = scanner.nextInt();
                    userDAOimpl.changeInfo(id);
                }
                case 3 -> {
                    User user = new User();
                    System.out.println("Введите Фамилию пользователя: ");
                    user.setSurname(scanner.next());
                    System.out.println("Введите Имя пользователя: ");
                    user.setName(scanner.next());
                    System.out.println("Введите Отчество пользователя: ");
                    user.setSecondname(scanner.next());
                    System.out.println("Введите Должность пользователя: ");
                    user.setPosition(scanner.next());
                    System.out.println("Введите Отдел пользователя: ");
                    user.setDepartment(scanner.next());
                    System.out.println("Введите Зарплату пользователя: ");
                    user.setSalary(scanner.nextInt());
                    userDAOimpl.createUser(user);
                }
                case 4 -> {
                    System.out.println("Введите ID пользователя, которого нужно удалить: ");
                    int id = scanner.nextInt();
                    userDAOimpl.delete(id);
                }
            }
            printMenu();
            var = scanner.nextInt();
        }
    }

    public static void printMenu(){
        System.out.println("Введите команду: ");
        System.out.println("1 - Получить пользователя по ID, 2 - Отредактировать данные сотрудника, 3 - Внесить нового сотрудника, 4 - Удалить сотрудника, 0 - Выход ");
    }
}