/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplemysql;

/**
 * Provides the processQuery method.
 *
 * Implement this interface to handle the SimpleMySQL multiple query results in
 * Query method.
 *
 * @author Daniel
 */
public interface SimpleAsyncMySQLTransactionHandler {

    /**
     * Handles the result of a MySQL query during a transaction.
     *
     * After processing the transaction you may return true to continue or false
     * to rollback and end the transaction. If, after the last query is
     * processed, this method returns true, then the transaction will be
     * commited.
     *
     * @param result the result of the query if is a SELECT, null otherwise
     * @param atQuery the query that is being analized, starts at 0
     * @return true if you want to continue, false if you want to rollback the
     * changes during the transaction and end the transaction
     */
    boolean processQuery(SimpleMySQLResult result, int atQuery);
}
