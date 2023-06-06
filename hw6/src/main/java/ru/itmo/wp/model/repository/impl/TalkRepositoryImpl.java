package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.DisplayTalk;
import ru.itmo.wp.model.domain.Parameter;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.service.UserService;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TalkRepositoryImpl extends BasicRepositoryImpl {
    private final UserService userService = new UserService();
    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    public Talk find(long id) {
        try {
            return toTalk(findBy("Talk", "id", id));
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Talk.", e);
        }
    }

    public List<DisplayTalk> findByUser(User user) {
        ArrayList<DisplayTalk> talks = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Talk " +
                    "WHERE sourceUserId=? OR targetUserId=? ORDER BY id ASC")) {
                statement.setLong(1, user.getId());
                statement.setLong(2, user.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    DisplayTalk talk;
                    while ((talk = toDisplayTalk(statement.getMetaData(), resultSet)) != null) {
                        talks.add(talk);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Talk.", e);
        }
        return talks;
    }

    private DisplayTalk toDisplayTalk(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Talk talk = toTalk(new ResultSetData(metaData, resultSet));

        DisplayTalk displayTalk = null;
        if (talk != null) {
            displayTalk = new DisplayTalk();
            displayTalk.setId(talk.getId());
            displayTalk.setSourceUserName(userService.find(talk.getSourceUserId()).getLogin());
            displayTalk.setTargetUserName(userService.find(talk.getTargetUserId()).getLogin());
            displayTalk.setText(talk.getText());
            displayTalk.setCreationTime(talk.getCreationTime());
        }

        return displayTalk;
    }

    private Talk toTalk(ResultSetData result) throws SQLException {
        ResultSetMetaData metaData = result.getResultSetMetaData();
        ResultSet resultSet = result.getResultSet();

        if (!resultSet.next()) {
            return null;
        }

        Talk talk = new Talk();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id" -> talk.setId(resultSet.getLong(i));
                case "sourceUserId" -> talk.setSourceUserId(resultSet.getLong(i));
                case "targetUserId" -> talk.setTargetUserId(resultSet.getLong(i));
                case "text" -> talk.setText(resultSet.getString(i));
                case "creationTime" -> talk.setCreationTime(resultSet.getTimestamp(i));
                default -> {
                    // No operations.
                }
            }
        }

        return talk;
    }

    public void save(Talk talk) {
        ArrayList<Parameter> params = new ArrayList<>();
        params.add(new Parameter("sourceUserId", talk.getSourceUserId()));
        params.add(new Parameter("targetUserId", talk.getTargetUserId()));
        params.add(new Parameter("text", talk.getText()));
        params.add(new Parameter("creationTime", new Object()));
        talk = (Talk) save(talk, params, "Talk");
    }
}
