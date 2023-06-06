package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.Parameter;
import ru.itmo.wp.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class EventRepositoryImpl extends BasicRepositoryImpl {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    public Event find(long id) throws SQLException {
        try {
            return toEvent(findBy("Event", "id", id));
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Event.", e);
        }
    }

    private Event toEvent(ResultSetData resultSetData) throws SQLException {
        ResultSetMetaData metaData = resultSetData.getResultSetMetaData();
        ResultSet resultSet = resultSetData.getResultSet();
        if (!resultSet.next()) {
            return null;
        }

        Event event = new Event();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id" -> event.setId(resultSet.getLong(i));
                case "userId" -> event.setUserId(resultSet.getLong(i));
                case "type" -> event.setType(Event.EventType.valueOf(resultSet.getString(i)));
                case "creationTime" -> event.setCreationTime(resultSet.getTimestamp(i));
                default -> {
                }
                // No operations.
            }
        }

        return event;
    }

    public void save(Event event) {
        ArrayList<Parameter> params = new ArrayList<>();
        params.add(new Parameter("userId", event.getUserId()));
        params.add(new Parameter("type", event.getType().name()));
        params.add(new Parameter("creationTime", new Object()));
        event = (Event) save(event, params, "Event");
    }
}
