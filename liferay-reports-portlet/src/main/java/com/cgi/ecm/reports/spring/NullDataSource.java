package com.cgi.ecm.reports.spring;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * A non-configured DataSource. It will fail with IllegalStateException if it is asked for a connection
 * Created by kortatu on 13/08/15.
 */
public class NullDataSource implements DataSource {
    private final String name;

    public NullDataSource(String dataSourceName) {
        this.name = dataSourceName;
    }

    @Override
    public Connection getConnection() throws SQLException {
        throw new IllegalStateException("JDBC connection not configured. Datasource ["+this.name+"] not initialized");
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new IllegalStateException("JDBC connection not configured. Datasource ["+this.name+"] not initialized");
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
