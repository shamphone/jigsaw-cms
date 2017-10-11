/*
 * $Workfile: $
 *
 * $Revision: $
 *
 * $Author: $
 *
 * $Date: $
 *
 * Copyright (c) 2004 Zhongkefulong Corporation
 */


package com.fulong.lyvc.message.generator;

import java.sql.*;

public class MessageIdGenerator {

    private String url;
    private String username;
    private String password;

    public MessageIdGenerator(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void remove(String messageName) throws Exception {

        // Connect to database
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);

        // Delete name record
        String sql = " delete from message where name=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, messageName);
        ps.executeUpdate();
        ps.close();

        // Close connection
        connection.commit();
        connection.close();
    }

    public boolean exist(String messageName) throws Exception {

        // Connect to database
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);

        // check if the corresponding id exist.
        String sql = " select id from message where name=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, messageName);
        ResultSet rs = ps.executeQuery();
        boolean result = false;
        if( rs.next() ) {
            result = true;
        }
        rs.close();
        ps.close();

        // Close connection
        connection.commit();
        connection.close();
        return result;
    }

    public int getId(String messageName) throws Exception {

        // Connect to database
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);

        // check if the corresponding id exist.
        int id = 0;
        String sql = " select id from message where name=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, messageName);
        ResultSet rs = ps.executeQuery();
        if( rs.next() ) {
            id = rs.getInt(1);
        }
        rs.close();
        ps.close();

        if( id != 0 ) {
            return id;
        }

        // Get new id and insert record
        sql = " select nextval('messageid')";
        ps = connection.prepareStatement(sql);
        rs = ps.executeQuery();
        rs.next();
        id = rs.getInt(1);
        rs.close();
        ps.close();

        sql = " insert into message(id, name) values(?,?)";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setString(2, messageName);
        ps.executeUpdate();
        ps.close();

        // Close connection
        connection.commit();
        connection.close();
        return id;

    }
}
