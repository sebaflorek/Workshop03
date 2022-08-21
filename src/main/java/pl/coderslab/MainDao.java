package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import java.util.Arrays;

public class MainDao {
    public static void main(String[] args) {

        // TEST 1 - CREATE --> z obiektu tworzy rekord do bazy danych.
        UserDao userDao = new UserDao();
//        User user = new User();
//        user.setEmail("jan.kowalski@workshop.pl");
//        user.setUsername("janek");
//        user.setPassword("janko_pass");
//        userDao.create(user);

        // test 2 - READ --> odczytuje z bazy danych i tworzy z rekordu obiekt.
//        User user = UserDao.read(2); // musiałem zrobić metodę static
//        System.out.println(user);

        // test 3 - UPDATE --> edytuje rekord na podstawie obiektu wyciągniętego z bazy danych.
//        user.setEmail("kamil.kot@workshop.pl");
//        user.setUsername("kamil");
//        user.setPassword("kamil_pass");
//        UserDao.update(user); // musiałem zrobić metodę static

        // test 4 - DELETE --> usuwa rekord
//        UserDao.delete(3);

        // test 5 - --> odczytuje z bazy danych i tworzy z rekordów tablicę obiektów

        //System.out.println(Arrays.toString(UserDao.findAll()));
        //System.out.println(UserDao.findAll()[0]);
        //System.out.println(UserDao.findAll()[1]);

        User[] allUsers = userDao.findAll();
        for (User user : allUsers) {
            System.out.println(user);
        }

    }
}
