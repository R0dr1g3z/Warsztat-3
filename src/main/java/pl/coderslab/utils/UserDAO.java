package pl.coderslab.utils;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.utils.DBUtil;
import pl.coderslab.utils.User;

import java.sql.*;
import java.util.Arrays;

public class UserDAO {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?,?,?)";
    private static final String READ_USER_QUERY =
            "SELECT * FROM users WHERE id=?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET email = ?, username= ?, password = ? WHERE id=?;";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id=?;";
    private static final String READALL_USER_QUERY =
            "SELECT * FROM users;";
    private static final String FIND_ALL_USERS_QUERY =
            "SELECT * FROM users";

    public void create(User user) {
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATE_USER_QUERY);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public User read(int userID) {
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_USER_QUERY);
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId((resultSet.getInt("id")));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void update(User user, int userId) {
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, hashPassword(user.getPassword()));
            preparedStatement.setInt(4,userId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(int userId){
        try(Connection connection = DBUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY);
            preparedStatement.setInt(1,userId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public User[] readAll(){
        try(Connection connection = DBUtil.getConnection()) {
            User[] users = new User[1];
            int currency = 0;
            PreparedStatement preparedStatement = connection.prepareStatement(READALL_USER_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                users[currency] = user;
                users = Arrays.copyOf(users, users.length+1);
                currency++;
            }
            return users;
        } catch (SQLException throwables) {
            return null;
        }
    }
    public User[] findAll() {
        try (Connection conn = DBUtil.getConnection()) {
            User[] users = new User[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_USERS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                users = addToArray(user, users);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
        tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.
        return tmpUsers; // Zwracamy nową tablicę.
    }


    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
