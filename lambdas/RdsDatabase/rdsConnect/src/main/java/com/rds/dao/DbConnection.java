package com.rds.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DbConnection {

    private static DataSource dataSource;
    
    private static final String rdsHostName= System.getenv("rds_endpoint");
    private static final String port= System.getenv("port");
    private static final String username= System.getenv("username");
    private static final String password= System.getenv("password");
    private static final String dbName= System.getenv("dbName");

    public static final Logger LOGGER = LoggerFactory.getLogger(DbConnection.class);

    private static void initialize() {
        if (dataSource == null) {
            LOGGER.info("new connection initialized");
            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setUrl("jdbc:mysql://"+rdsHostName+":"+port+"/"+dbName);
            basicDataSource.setUsername(username);
            basicDataSource.setPassword(password);
            basicDataSource.setDriverClassName("org.mysql.jdbc.Driver");
            basicDataSource.setInitialSize(5);
            basicDataSource.setMaxTotal(10);
            dataSource = basicDataSource;
        }
    }

    public static Connection getConnection() throws SQLException {
        initialize();
        Connection conn = dataSource.getConnection();
        if (conn == null || conn.isClosed() || !conn.isValid(30)) {
            LOGGER.info("connection expired returning new connection");
            conn = dataSource.getConnection();
        }
        return conn;
    }

    public static void executeStatement(Connection conn, String sqlStatement) throws Exception{
        if (conn != null) {
                LOGGER.info("Sql Statement"+sqlStatement);
                Statement st = conn.createStatement();
                st.execute(sqlStatement);
                st.close();
                LOGGER.info("Statement Executed");
        } else {
            throw new Exception("connection timed out");
        }
    }

    public static ResultSet executeQuiry(Connection conn, String sqlStatement) throws Exception{
        if (conn != null) {
                LOGGER.info("Sql Statement"+sqlStatement);
                Statement st = conn.createStatement();
                ResultSet result = st.executeQuery(sqlStatement);
                st.close();
                LOGGER.info("Statement Executed");
                return result;
        }else {
            throw new Exception("connection timed out");
        }
    }

    public static int executeUpdate(Connection conn, String sqlStatement) throws Exception{
        if (conn != null) {
                LOGGER.info("Sql Statement"+sqlStatement);
                Statement st = conn.createStatement();
                int result = st.executeUpdate(sqlStatement);
                st.close();
                LOGGER.info("Statement Executed");
                return result;
        }else {
            throw new Exception("connection timed out");
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                LOGGER.info("Connection closed");
            } catch (SQLException e) {
                LOGGER.error("error Occured while clossing connection"+e);
            }
        }
    }
    
}
