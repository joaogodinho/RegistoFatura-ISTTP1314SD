/*
 * 
 * $Id$
 * 
 * Software License Agreement (BSD License)
 * 
 * Copyright (c) 2011, The Daniel Morante Company, Inc.
 * All rights reserved.
 * 
 * Redistribution and use of this software in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 *   Redistributions of source code must retain the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer.
 * 
 *   Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer in the documentation and/or other
 *   materials provided with the distribution.
 * 
 *   Neither the name of The Daniel Morante Company, Inc. nor the names of its
 *   contributors may be used to endorse or promote products
 *   derived from this software without specific prior
 *   written permission of The Daniel Morante Company, Inc.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * Simple MySQL Java Class
 * Makes it similair to PHP
 */
package simplemysql;

import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.exceptions.MySQLNonTransientConnectionException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel Morante
 */
public class SimpleMySQL {

    private Connection mysql_connection = null;

    private boolean auto_reconnect = true;
    private int auto_reconnect_time = 5000;
    private int auto_reconnect_retry = 15;

    private Savepoint savepoint;

    private String username_local_cache = null;
    private String password_local_cache = null;
    private String hostname_local_cache = null;
    private String database_local_cache = null;

    /**
     * Gets an existing SimpleMySQL instance.
     *
     * @return
     */
    public static SimpleMySQL getInstance() {
        return SimpleMySQLHolder.INSTANCE;
    }

    private static class SimpleMySQLHolder {

        private static final SimpleMySQL INSTANCE = new SimpleMySQL();
    }

    /**
     * Enable automatic auto_reconnect if the MySQL Database Connection is lost
     * By default this is enabled.
     *
     * @see #DisableReconnect()
     */
    public void EnableReconnect() {
        auto_reconnect = true;
    }

    /**
     * Disable automatic auto_reconnect if the MySQL Database Connection is lost
     * By default this is enabled
     *
     * @see #EnableReconnect()
     */
    public void DisableReconnect() {
        auto_reconnect = false;
    }

    /**
     * Test whether or not automatic reconnect is currently enabled. By default
     * automatic auto_reconnect is enabled
     *
     * @return true if automatic auto_reconnect is enabled, false if it is
     * disabled
     * @see #EnableReconnect()
     * @see #DisableReconnect()
     */
    public boolean isReconnectEnabled() {
        return auto_reconnect;
    }

    /**
     * Sets the waiting time before attempting to auto_reconnect to the MySQL
     * Database server.
     *
     * Default waiting time is 5 seconds
     *
     * @param time in milliseconds
     */
    public void setReconnectTime(int time) {
        auto_reconnect_time = time;
    }

    /**
     * Returns the waiting time before attempting to auto_reconnect to the MySQL
     * Database server.
     *
     * If this value was not changed with {@link #setReconnectTime(int)} the
     * default waiting time is 5 seconds
     *
     * @return time in milliseconds
     * @see #setReconnectTime(int)
     */
    public int getReconnectTime() {
        return auto_reconnect_time;
    }

    /**
     * Sets the maximum number of automatic reconnection attempts before giving
     * up and throwing an exception.
     *
     * Default number of attempts is 15
     *
     * @param retry_times
     */
    public void setReconnectNumRetry(int retry_times) {
        auto_reconnect_retry = retry_times;
    }

    /**
     * Returns the maximum number of automatic reconnection attempts before
     * giving up and throwing an exception.
     *
     * If this value was not changed with {@link #setReconnectNumRetry(int)} the
     * default number of attempts is 15
     *
     * @return Number of retries
     * @see #setReconnectNumRetry(int)
     */
    public int getReconnectNumRetry() {
        return auto_reconnect_retry;
    }

    /**
     * Allows to execute queries in a single transaction.
     *
     * If you use this, there is no need to call the method {@link #Query(
     * simplemysql.SimpleAsyncMySQLTransactionHandler, java.lang.String...)},
     * because all queries made with {@link #Query(java.lang.String)} will be in
     * a single transaction. Use {@link #transactionCommit()} to apply the
     * changes or {@link #transactionRollback()} to nullify the transaction.
     *
     * @see #transactionCommit()
     * @see #transactionRollback()
     */
    public void transactionBegin() {
        try {
            mysql_connection.setAutoCommit(false);
            savepoint = mysql_connection.setSavepoint();
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).
                    log(Level.WARNING, "Error happened", ex);
        }
    }

    /**
     * Commits the current transaction.
     *
     * This method must be called after {@link #transactionBegin()}. It commits
     * the changes to the database and ends the transation, therefore all
     * following queries made with {@link #Query(java.lang.String)} will not be
     * in a single transation. You should call this method or the
     * {@link #transactionRollback()} only once after
     * {@link #transactionBegin()} was called.
     *
     * @see #transactionBegin()
     * @see #transactionRollback()
     */
    public void transactionCommit() {
        try {
            mysql_connection.commit();
            mysql_connection.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).
                    log(Level.WARNING, "Error happened", ex);
        }
    }

    /**
     * Rollbacks the current transaction.
     *
     * This method nullifies all changes made to the database since
     * {@link #transactionBegin()} was called. It ends the transaction,
     * therefore you shouldn't call this method again, nor the
     * {@link #transactionCommit()} until you start a new transation with
     * {@link #transactionBegin()} method.
     *
     * @see #transactionBegin()
     * @see #transactionCommit()
     */
    public void transactionRollback() {
        try {
            mysql_connection.rollback(savepoint);
            mysql_connection.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).
                    log(Level.WARNING, "Error happened", ex);
        }
    }

    /**
     * Checks if a transaction is currently active.
     *
     * After call {@link #transactionBegin()} this method will return true until
     * {@link #transactionCommit()} or {@link #transactionRollback()} is called,
     * after the transaction ends, this method will return false. The same
     * happens before {@link #transactionBegin()} is called.
     *
     * @return true if is on a transaction, false otherwise
     */
    public boolean isOnTransaction() {
        try {
            return !mysql_connection.getAutoCommit();
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).
                    log(Level.WARNING, "Error happened", ex);
        }
        return false;
    }

    /**
     * Connect to the MySQL Server using default connection parameters.
     *
     * <p>
     * <strong>HOST</strong>: mysql<br />
     * <strong>USER</strong>: root<br />
     * No Password
     * </p>
     *
     * @return
     */
    public boolean connect() {
        return connect("mysql", "root", "");
    }

    /**
     * Connects to the MySQL Server using the given server, username, and
     * password.
     *
     * @param server
     * @param username
     * @param password
     * @return True on a successful connection
     */
    public boolean connect(String server, String username, String password) {
        String mysql_connectionURL;
        String mysql_driver;

        //Cache the server, user, and password localy for auto-auto_reconnect
        username_local_cache = username;
        password_local_cache = password;
        hostname_local_cache = server;

        try {
            //Load MySQL JDBC Driver
            mysql_driver = "com.mysql.jdbc.Driver";
            Class.forName(mysql_driver);

            //Open Connection
            mysql_connectionURL = "jdbc:mysql://" + server;
            mysql_connection = DriverManager.getConnection(mysql_connectionURL,
                    username, password);
            mysql_connection.setAutoCommit(true);
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            String desc = "\nCan  not connect to the MySQL Database Server. \n"
                    + "Please check your configuration.\n\n"
                    + "Hostname: " + hostname_local_cache + "\n"
                    + "Username: " + username_local_cache + "\n";
            Logger.getLogger(getClass().getName()).
                    log(Level.WARNING, desc, ex);

            return false;
        }
    }

    /**
     * Connects to the MySQL Server using the given server, username, and
     * password. Auto selects the given database.
     *
     * @param server
     * @param username
     * @param password
     * @param database
     * @return True on a successful connection.
     */
    public boolean connect(String server, String username, String password,
            String database) {
        //cache the database for auto-auto_reconnect
        database_local_cache = database;

        if (connect(server, username, password)) {
            return SelectDB(database);
        } else {
            return false;
        }
    }

    /**
     * Manually select the database to Query.
     *
     * @param database
     * @return True on successful operation.
     */
    public boolean SelectDB(String database) {
        boolean result = true;
        try {
            mysql_connection.setCatalog(database);
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).
                    log(Level.WARNING, null, e);
            result = false;
        }
        return result;
    }

    /**
     * Closes the current MySQL Connection.
     *
     * @return True on close
     */
    public boolean close() {
        try {
            mysql_connection.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).
                    log(Level.WARNING, "Could not close connection.", ex);
            return false;
        }
    }

    private boolean reconnect(String server, String username, String password,
            String database) throws SQLTransientConnectionException {
        boolean connected;
        try {
            connected = connect(server, username, password, database);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).
                    log(Level.INFO, "Could not reconnect.", e);
            connected = false;
        }

        if (!connected) {
            throw new SQLTransientConnectionException(
                    "Unable to re-establish database connection,"
                            + "please try again later.");
        }
        Logger.getLogger(getClass().getName()).
                log(Level.INFO, "Database connection re-established");
        return connected;
    }

    private synchronized void auto_reconnect() {
        Logger.getLogger(getClass().getName()).
                log(Level.INFO, "Attempting Auto-Reconnect...");

        try {
            mysql_connection.close();
            mysql_connection = null;
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).
                    log(Level.WARNING, null, e);
        }

        boolean connected = false;

        int retries_left = auto_reconnect_retry;
        while (retries_left > 0 && !connected) {
            retries_left--;
            Logger.getLogger(getClass().getName()).
                    log(Level.INFO, "Auto-Reconnect Attempt #{0} of {1}",
                            new Object[]{auto_reconnect_retry - retries_left,
                                auto_reconnect_retry});
            try {
                wait(auto_reconnect_time);
                connected = reconnect(hostname_local_cache,
                        username_local_cache, password_local_cache,
                        database_local_cache);
            } catch (InterruptedException i) {
                Logger.getLogger(getClass().getName()).
                        log(Level.WARNING, "Reconnect Canceled!", i);
            } catch (SQLTransientConnectionException e) {
                Logger.getLogger(getClass().getName()).
                        log(Level.WARNING, "AUTO RECONNECT.", e);
            }
        }
    }

    private void check_connection() {
        Statement stmt;
        ResultSet mysql_result;
        try {
            stmt = mysql_connection.createStatement();
            mysql_result = stmt.executeQuery("SELECT 1 from DUAL WHERE 1=0");
            mysql_result.close();
        } catch (CommunicationsException e) {
            Logger.getLogger(getClass().getName()).
                    log(Level.WARNING, "Database connection lost.", e);
            if (auto_reconnect) {
                auto_reconnect();
            }
        } catch (NullPointerException e) {
            Logger.getLogger(getClass().getName()).
                    log(Level.WARNING, "MySQL Database not connected!", e);
        } catch (SQLTransientConnectionException e) {
            Logger.getLogger(getClass().getName()).
                    log(Level.WARNING, "Database connection problem", e);
            if (auto_reconnect) {
                auto_reconnect();
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).
                    log(Level.WARNING, "Database Communications Error", e);
            if (auto_reconnect) {
                auto_reconnect();
            }
        }
    }

    /**
     * Old style SimpleMySQL query.
     *
     * @deprecated This method is replaced by the new
     * {@link #Query(java.lang.String)} method.
     * @param query
     * @return For SELECT type queries a Java SQL ResultSet object. all other
     * type of queries will return null
     * @see #Query(java.lang.String)
     */
    @Deprecated
    public ResultSet query(String query) {
        return Query(query).getResultSet();
    }

    private SimpleMySQLResult executeQuery(String query) {
        Statement stmt;
        ResultSet mysql_result;
        SimpleMySQLResult result = null;

        try {
            if (query.startsWith("SELECT")) {
                stmt = mysql_connection.createStatement();
                mysql_result = stmt.executeQuery(query);
                result = new SimpleMySQLResult(mysql_result);
            } else {
                stmt = mysql_connection.createStatement();
                stmt.executeUpdate(query);
            }
        } catch (NullPointerException ex) {
            Logger.getLogger(getClass().getName()).
                    log(Level.WARNING,
                            "You are not connected to a MySQL server", ex);
        } catch (MySQLNonTransientConnectionException ex) {
            Logger.getLogger(getClass().getName()).
                    log(Level.WARNING, "MySQL server Connection was lost", ex);
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).
                    log(Level.WARNING, "Error.", ex);
        }

        return result;
    }

    /**
     * Executes a simple Query on the MySQL database. You must first connect to
     * a MySQL database. If a MySQL database is not connected this will return
     * null.
     *
     * @param query
     * @return For SELECT type queries a SimpleMySQLResult. All other type of
     * queries will return null
     * @see #connect()
     * @see #connect(java.lang.String, java.lang.String, java.lang.String)
     * @see #connect(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    public SimpleMySQLResult Query(String query) {
        check_connection();
        return executeQuery(query);
    }

    /**
     * Executes multiple Queries on the MySQL database as a single transaction.
     * You must first connect to a MySQL database. The queries will be commited
     * all together as a transaction. If you for any reason attempt to rollback
     * no more queries will be executed and this method ends. If, after the last
     * query is processed, the handler returns true, then the transaction will
     * be commited.
     *
     * @param handler processQuery method will be called every time a query
     * returns, you may rollback or continue the transaction.
     * @param queries Queries for the transaction
     * @see #connect()
     * @see #connect(java.lang.String, java.lang.String, java.lang.String)
     * @see #connect(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    public void Query(SimpleAsyncMySQLTransactionHandler handler,
            String... queries) {
        check_connection();
        try {
            mysql_connection.setAutoCommit(false);
            int atQuery = 0;
            for (String query : queries) {
                if (!handler.processQuery(executeQuery(query), atQuery)) {
                    mysql_connection.rollback();
                    mysql_connection.setAutoCommit(true);
                    return;
                }
                atQuery += 1;
            }
            mysql_connection.commit();
            mysql_connection.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).
                    log(Level.WARNING,
                            "You are not connected to a MySQL server", ex);
        }
    }
}
