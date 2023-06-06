package ru.itmo.wp.model.repository.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class ResultSetData {
    ResultSetMetaData resultSetMetaData;
    ResultSet resultSet;

    public ResultSetData(ResultSetMetaData resultSetMetaData, ResultSet resultSet) {
        this.resultSetMetaData = resultSetMetaData;
        this.resultSet = resultSet;
    }

    public ResultSetMetaData getResultSetMetaData() {
        return resultSetMetaData;
    }

    public void setResultSetMetaData(ResultSetMetaData resultSetMetaData) {
        this.resultSetMetaData = resultSetMetaData;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }
}
