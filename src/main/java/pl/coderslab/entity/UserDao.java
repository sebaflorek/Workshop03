package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Arrays;

public class UserDao {

    private static final String CREATE_USER_QUERY = "INSERT INTO users (email, username, password) VALUES (?, ?, ?)";
    private static final String UPDATE_DATA_QUERY = "UPDATE users SET email = ?, username = ?, password = ? WHERE id = ?";
    private static final String READ_USER_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String READ_ALL_USERS_QUERY = "SELECT * FROM users";

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User create(User user) { // Czy nie powinna być static, skoro zapytanie jest static? Czemu działa bez static?
        try (Connection conn = DbUtil.connectWorkshop3()) {
            PreparedStatement prStmt = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            prStmt.setString(1, user.getEmail());
            prStmt.setString(2, user.getUsername());
            prStmt.setString(3, hashPassword(user.getPassword()));
            prStmt.executeUpdate();
            // pobierz z bazy identyfikator
            ResultSet resultSet = prStmt.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User read(int id) { // musiałem zmienić na static, by w MainDao wywołać metodę.
        try (Connection conn = DbUtil.connectWorkshop3()) {
            PreparedStatement prStmt = conn.prepareStatement(READ_USER_QUERY);
            prStmt.setInt(1, id);
            ResultSet resultSet = prStmt.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void update(User user) { // musiałem zmienić na static, by w MainDao wywołać metodę.
        try (Connection conn = DbUtil.connectWorkshop3()) {
            PreparedStatement prStmt = conn.prepareStatement(UPDATE_DATA_QUERY);
            prStmt.setString(1, user.getEmail());
            prStmt.setString(2, user.getUsername());
            prStmt.setString(3, hashPassword(user.getPassword()));
            // UWAGA jak ktoś nie zmieni hasła to zahashujemy zahashowane hasło i już się potem do konta nie dostanie.
            prStmt.setInt(4, user.getId());
            prStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(int userID) {
        try (Connection conn = DbUtil.connectWorkshop3()) {
            PreparedStatement prStmt = conn.prepareStatement(DELETE_USER_QUERY);
            prStmt.setInt(1, userID);
            prStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User[] findAll() {
        // tu dać User[]
        try (Connection conn = DbUtil.connectWorkshop3()) {
            PreparedStatement prStmt = conn.prepareStatement(READ_ALL_USERS_QUERY);
            ResultSet resultSet = prStmt.executeQuery();
            User[] users = new User[0];
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                users = addToArray(user, users);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // w miarę możliwości unikać null (ze względu na null pointer exception)
        // return users
    }

    private static User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
        tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.
        return tmpUsers; // Zwracamy nową tablicę.
    }



}
