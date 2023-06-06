package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.Parameter;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SqlNoDataSourceInspection")
public class UserRepositoryImpl extends BasicRepositoryImpl implements UserRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    private User findBy(String paramName, Object param) {
        try {
            return toUser(findBy("User", paramName, param));
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    @Override
    public long findCount() {
        return findAll().size();
    }

    @Override
    public User findByLogin(String login) {
        return findBy("login", login);
    }

    @Override
    public User findByEmail(String email) {
        return findBy("email", email);
    }

    @Override
    public User find(long id) {
        return findBy("id", id);
    }

    @Override
    public User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User WHERE (login=? OR email=?) AND (passwordSha=?)")) {
                statement.setString(1, loginOrEmail);
                statement.setString(2, loginOrEmail);
                statement.setString(3, passwordSha);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toUser(new ResultSetData(statement.getMetaData(), resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM User ORDER BY id DESC")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    User user;
                    while ((user = toUser(new ResultSetData(statement.getMetaData(), resultSet))) != null) {
                        users.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
        return users;
    }

    private User toUser(ResultSetData resultSetData) throws SQLException {
        ResultSetMetaData metaData = resultSetData.getResultSetMetaData();
        ResultSet resultSet = resultSetData.getResultSet();
        if (!resultSet.next()) {
            return null;
        }

        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id" -> user.setId(resultSet.getLong(i));
                case "login" -> user.setLogin(resultSet.getString(i));
                case "email" -> user.setEmail(resultSet.getString(i));
                case "creationTime" -> user.setCreationTime(resultSet.getTimestamp(i));
                default -> {
                }
                // No operations.
            }
        }

        return user;
    }

    @Override
    public void save(User user, String passwordSha) {
        ArrayList<Parameter> params = new ArrayList<>();
        params.add(new Parameter("login", user.getLogin()));
        params.add(new Parameter("passwordSha", passwordSha));
        params.add(new Parameter("creationTime", new Object()));
        params.add(new Parameter("email", user.getEmail()));
        user = (User) save(user, params, "User");
    }
}
