package com.rds.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.LambdaLogger;


public class DbConnection {

    private static DataSource dataSource;

    private static final String rdsHostName= System.getenv("rds_endpoint");
    private static final String port= System.getenv("port");
    private static final String username= System.getenv("username");
    private static final String password= System.getenv("password");
    private static final String dbName= System.getenv("dbName");

    // public static final Logger LOGGER = LoggerFactory.getLogger(DbConnection.class);

    private static void initialize(LambdaLogger LOGGER) {
        if (dataSource == null) {
            LOGGER.log("new connection initialized");
            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setUrl("jdbc:mysql://"+rdsHostName+":"+port+"/"+dbName);
            basicDataSource.setUsername(username);
            basicDataSource.setPassword(password);
            basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            basicDataSource.setInitialSize(5);
            basicDataSource.setMaxTotal(10);
            dataSource = basicDataSource;
            LOGGER.log("rdsHostName "+rdsHostName+" port "+ port +" dbName "+ dbName + " username "+ " password " +password);
        }
    }

    public static Connection getConnection(LambdaLogger LOGGER) throws SQLException {
        initialize(LOGGER);
        LOGGER.log("getting new connection");
        Connection conn = dataSource.getConnection();
        if (conn == null || conn.isClosed() || !conn.isValid(30)) {
            LOGGER.log("connection expired returning new connection");
            conn = dataSource.getConnection();
        }
        LOGGER.log("returning new connection");
        return conn;
    }

    public static void executeStatement(Connection conn, String sqlStatement, LambdaLogger LOGGER) throws Exception{
        if (conn != null) {
                LOGGER.log("Sql Statement"+sqlStatement);
                Statement st = conn.createStatement();
                st.execute(sqlStatement);
                st.close();
                LOGGER.log("Statement Executed");
        } else {
            throw new Exception("connection timed out");
        }
    }

    public static List<Map<String, Object>> executeQuiry(Connection conn, String sqlStatement, LambdaLogger LOGGER) throws Exception{
        if (conn != null) {
                List<Map<String,Object>> rowList = new ArrayList<>();
                LOGGER.log("Sql Statement"+sqlStatement);
                Statement st = conn.createStatement();
                ResultSet result = st.executeQuery(sqlStatement);
                rowList = getRowColumnMapObj(result);
                st.close();
                LOGGER.log("Statement Executed");
                return rowList;
        }else {
            throw new Exception("connection timed out");
        }
    }

    public static int executeUpdate(Connection conn, String sqlStatement, LambdaLogger LOGGER) throws Exception{
        if (conn != null) {
                LOGGER.log("Sql Statement"+sqlStatement);
                Statement st = conn.createStatement();
                int result = st.executeUpdate(sqlStatement);
                st.close();
                LOGGER.log("Statement Executed");
                return result;
        }else {
            throw new Exception("connection timed out");
        }
    }

    public static void closeConnection(Connection conn, LambdaLogger LOGGER) {
        if (conn != null) {
            try {
                conn.close();
                LOGGER.log("Connection closed");
            } catch (SQLException e) {
                LOGGER.log("error Occured while clossing connection"+e);
            }
        }
    }

    private static List<Map<String,Object>> getRowColumnMapObj(ResultSet result) throws SQLException{
        List<Map<String,Object>> rowList = new ArrayList<>();
        ResultSetMetaData rsmd = result.getMetaData();
        int columnCount = rsmd.getColumnCount();
        while(result.next()){
            Map<String,Object> row = new HashMap<>();
            for(int i=1;columnCount>=i;i++){
                row.put(rsmd.getColumnName(i), result.getObject(i));
            }
            rowList.add(row);     
        }
        return rowList;
    }
    
}
