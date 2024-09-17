package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoHibernateImpl();
        userDao.dropUsersTable();
        userDao.createUsersTable();
        userDao.saveUser("a", "a", (byte) 10);
        userDao.saveUser("b", "s", (byte) 100);
        userDao.saveUser("c", "d", (byte) 22);
        userDao.saveUser("d", "f", (byte) 34);
        userDao.saveUser("e", "va", (byte) 76);
        userDao.removeUserById(2);
        System.out.println(userDao.getAllUsers());
        userDao.cleanUsersTable();
    }
}
