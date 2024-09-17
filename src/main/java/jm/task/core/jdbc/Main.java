package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Matthew", "McConaughey", (byte) 54);
        System.out.println("User с именем — Matthew добавлен в базу данных");
        userService.saveUser("Leonardo", "DickCaprio", (byte) 49);
        System.out.println("User с именем — Leonardo добавлен в базу данных");
        userService.saveUser("German", "Sevostianov", (byte) 28);
        System.out.println("User с именем — German добавлен в базу данных");
        userService.saveUser("Roman", "Sakutin", (byte) 29);
        System.out.println("User с именем — Roman добавлен в базу данных");

        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}