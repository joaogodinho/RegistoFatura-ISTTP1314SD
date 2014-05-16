/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplemysql;

/**
 * Creates a MySQL query.
 *
 * This class is intended to help the programer printing the MySQL queries
 * strings. It is not intended to produce correct queries, thats up to the
 * programer.
 *
 * @author Daniel
 */
public class QueryBuilder {

    /**
     * Adds a MySQL function to current query.
     *
     * @param query the StringBuilder query
     * @param function a MySQL function like MAX, MIN, AVG...
     * @param distinct if is distinct FUNCTION([DISTINCT] column)
     * @param column the column to apply function
     */
    private static void function(StringBuilder query, String function,
            boolean distinct, String column) {
        query.append(function).append("(");
        if (distinct) {
            query.append("DISTINCT ");
        }
        query.append("`").append(column).append("`) ");
    }

    private static String escape(String str) {
        return str.replaceAll("['\"\\\\]", "\\\\$0");
    }
    
    /**
     * Adds a MySQL function to current query.
     *
     * @param query the StringBuilder query
     * @param function a MySQL function like MAX, MIN, AVG...
     * @param distinct if is distinct FUNCTION([DISTINCT] column)
     * @param table the table that contains the column
     * @param column the column to apply function
     */
    private static void function(StringBuilder query, String function,
            boolean distinct, String table, String column) {
        query.append(function).append("(");
        if (distinct) {
            query.append("DISTINCT ");
        }
        query.append("`").append(table).append("`.`").
                append(column).append("`) ");
    }

    /**
     * The StringBuilder query.
     *
     * It's used to create the query appending new elements. At end, is used the
     * toString() method to produce the MySQL query string.
     */
    protected final StringBuilder query = new StringBuilder();

    /**
     * Initializates a QueryPreparer.
     */
    public QueryBuilder() {
    }

    /**
     * Creates the select clause.
     *
     * @param columns the columns on the select clause
     */
    private void selectColumns(String[] columns) {
        boolean all = false;
        for (String s : columns) {
            if (s.equals("*")) {
                query.append("* ");
                all = true;
            } else {
                query.append("`").append(s).append("`, ");
            }
        }
        if (columns.length > 0 && !all) {
            query.replace(query.length() - 2, query.length(), " ");
        }
    }

    /**
     * Selects the specified columns.
     *
     *
     *
     * @param distinct if needed to print DISTINCT
     * @param columns the columns on select clause
     * @return
     */
    public QueryBuilder SELECT(boolean distinct, String... columns) {
        query.append("SELECT ");
        if (distinct) {
            query.append("DISTINCT ");
        }
        selectColumns(columns);
        return this;
    }

    /**
     * Selects all.
     *
     * Creates a "SELECT [DISTINCT] * " statement.
     *
     * @param distinct if needed to print DISTINCT
     * @return this
     */
    public QueryBuilder SELECT(boolean distinct) {
        query.append("SELECT ");
        if (distinct) {
            query.append("DISTINCT ");
        }
        query.append("* ");
        return this;
    }

    /**
     *
     * @param distinct
     * @param clause
     * @return
     */
    public QueryBuilder SELECT(boolean distinct, SelectClause clause) {
        query.append("SELECT ");
        if (distinct) {
            query.append("DISTINCT ");
        }
        query.append(clause.toString());
        return this;
    }

    public QueryBuilder DELETE(boolean ignore) {
        query.append("DELETE ");
        if (ignore) {
            query.append("IGNORE ");
        }
        return this;
    }

    /**
     *
     * @param tables
     * @return this
     */
    public QueryBuilder FROM(String... tables) {
        query.append("FROM ");
        for (String s : tables) {
            query.append("`").append(s).append("`, ");
        }
        if (tables.length > 0) {
            query.replace(query.length() - 2, query.length(), " ");
        }
        return this;
    }

    /**
     *
     * @param clause
     * @return this
     */
    public QueryBuilder FROM(FromClause clause) {
        query.append("FROM ").append(clause.toString());
        return this;
    }

    /**
     *
     * @param condition
     * @return
     */
    public QueryBuilder WHERE(WhereClause condition) {
        query.append("WHERE ").append(condition.toString());
        return this;
    }

    public QueryBuilder UPDATE(String table) {
        query.append("UPDATE `").append(table).append("` ");
        return this;
    }

    public QueryBuilder SET(SetClause columns) {
        query.append("SET ").append(columns.toString());
        return this;
    }

    /**
     *
     * @param table
     * @param columns
     * @param values
     * @return
     */
    public QueryBuilder INSERT(String table, InsertColumns columns,
            InsertValues... values) {
        query.append("INSERT INTO `").append(table).append("`");
        query.append(columns.toString());
        query.append("VALUES ");
        for (InsertValues v : values) {
            query.append(v.toString()).append(", ");
        }
        if (values.length > 0) {
            query.replace(query.length() - 2, query.length(), " ");
        }
        return this;
    }

    public QueryBuilder CREATE_TABLE(boolean ifNotExists, String tableName,
            CreateTableClause struct) {
        query.append("CREATE TABLE ");
        if(ifNotExists) {
            query.append("IF NOT EXISTS ");
        }
        query.append("`").append(tableName).append("`").
                append(struct.toString());
        return this;
    }

    public QueryBuilder ORDER_BY(OrderByClause orderBy) {
        query.append("ORDER BY ").append(orderBy.toString());
        return this;
    }

    public QueryBuilder GROUP_BY(String... columns) {
        query.append("GROUP BY ");
        for (String c : columns) {
            query.append("`").append(c).append("`, ");
        }
        if (columns.length > 0) {
            query.replace(query.length() - 2, query.length(), " ");
        }
        return this;
    }

    public QueryBuilder HAVING(HavingClause clause) {
        query.append("HAVING ");
        query.append(clause);
        return this;
    }

    public QueryBuilder ALTER_TABLE_ADD(boolean ignore, String table,
            String column, String columnDefinition) {
        query.append("ALTER ");
        if (ignore) {
            query.append("IGNORE ");
        }
        query.append("TABLE `").append(table).append("` ADD `").append(column).
                append("` ").append(columnDefinition).append(" ");
        return this;
    }

    public QueryBuilder ALTER_TABLE_MODIFY(boolean ignore, String table,
            String column, String columnDefinition) {
        query.append("ALTER ");
        if (ignore) {
            query.append("IGNORE ");
        }
        query.append("TABLE `").append(table).append("` MODIFY `").
                append(column).append("` ").append(columnDefinition).
                append(" ");
        return this;
    }

    public QueryBuilder ALTER_TABLE_DROP(boolean ignore, String table,
            String column) {
        query.append("ALTER ");
        if (ignore) {
            query.append("IGNORE ");
        }
        query.append("TABLE `").append(table).append("` DROP `").
                append(column).append("` ");
        return this;
    }

    public QueryBuilder DROP_TABLE(boolean ifExists, String table) {
        query.append("DROP TABLE ");
        if (ifExists) {
            query.append("IF EXISTS ");
        }
        query.append("`").append(table).append("` ");
        return this;
    }

    @Override
    public String toString() {
        return query.toString();
    }

    public static class FromClause {

        private final StringBuilder query = new StringBuilder();

        public FromClause(String table) {
            query.append("`").append(table).append("` ");
        }

        public FromClause(String table, String asName) {
            query.append("`").append(table).append("` AS `").append(asName).
                    append("` ");
        }

        /**
         *
         * @param table
         * @return this
         */
        public FromClause CARTESIAN_PRODUCT(String table) {
            query.append(", `").append(table).append("` ");
            return this;
        }

        public FromClause CARTESIAN_PRODUCT_AS(String table, String asName) {
            query.append(", `").append(table).append("` AS `").append(asName).
                    append("` ");
            return this;
        }

        public FromClause NATURAL_JOIN(String table) {
            query.append("NATURAL JOIN `").append(table).append("` ");
            return this;
        }

        public FromClause NATURAL_JOIN_AS(String table, String asName) {
            query.append("NATURAL JOIN `").append(table).append("` AS `").
                    append(asName).append("` ");
            return this;
        }

        @Override
        public String toString() {
            return query.toString();
        }
    }

    public static class SelectClause {

        private final StringBuilder query = new StringBuilder();

        public SelectClause() {
        }

        public SelectClause addColumn(String column) {
            if (column.equals("*")) {
                query.append("* ");
            } else {
                query.append("`").append(column).append("`, ");
            }
            return this;
        }

        public SelectClause addColumn(String table, String column) {
            query.append("`").append(table).append("`.`").
                    append(column).append("`, ");
            return this;
        }

        public SelectClause addColumnAS(String column, String asName) {
            query.append("`").append(column).append("` AS ").append("`").
                    append(asName).append("`, ");
            return this;
        }

        public SelectClause addColumnAS(String table, String column,
                String asName) {
            query.append("`").append(table).append("`.`").append(column).
                    append("` AS ").append("`").append(asName).append("`, ");
            
            return this;
        }

        public SelectClause addFunctionAS(String function, boolean distinct,
                String column, String asName) {
            function(query, function, distinct, column);
            query.append(" AS `").append(asName).append("`, ");
            return this;
        }

        public SelectClause addFunctionAS(String function, boolean distinct,
                String table, String column, String asName) {
            function(query, function, distinct, table, column);
            query.append(" AS `").append(asName).append("`, ");
            return this;
        }

        @Override
        public String toString() {
            if (query.length() > 0 && query.toString().endsWith(", ")) {
                query.replace(query.length() - 2,
                        query.length(), " ");
            }
            return query.toString();
        }

    }

    public static class HavingClause {

        private final StringBuilder query = new StringBuilder();

        public HavingClause() {
        }

        public HavingClause addClause(String function, boolean distinct,
                String functionTable, String functionColumn, String comparator,
                String value) {
            String newValue = escape(value);
            function(query, function, distinct, functionColumn);
            query.append(comparator).append("'").append(newValue).append("' ");
            return this;
        }

        public HavingClause addClause(String function, boolean distinct,
                String functionColumn, String comparator, String value) {
            String newValue = escape(value);
            function(query, function, distinct, functionColumn);
            query.append(comparator).append(" '").append(newValue).append("' ");
            return this;
        }

        public HavingClause AND() {
            query.append("AND ");
            return this;
        }

        public HavingClause OR() {
            query.append("OR ");
            return this;
        }

        @Override
        public String toString() {
            return query.toString();
        }
    }

    public static class OrderByClause {

        private final StringBuilder query = new StringBuilder();

        public OrderByClause() {
        }

        public OrderByClause addColumn(String column) {
            query.append("`").append(column).append("`, ");
            return this;
        }

        /**
         *
         * @param column
         * @param direction can be ASC ascendent or DESC descendent
         * @return
         */
        public OrderByClause addColumn(String column, String direction) {
            addColumn(column);
            query.append(direction).append(", ");
            return this;
        }

        public OrderByClause addFunction(String function) {
            query.append(function).append(", ");
            return this;
        }

        public OrderByClause addFunction(String function, String direction) {
            addFunction(function);
            query.append(direction).append(", ");
            return this;
        }

        @Override
        public String toString() {
            if (query.length() > 0 && query.toString().endsWith(", ")) {
                query.replace(query.length() - 2,
                        query.length(), " ");
            }
            return query.toString();
        }
    }

    public static class CreateTableClause {

        private final StringBuilder query = new StringBuilder();

        public CreateTableClause() {
            query.append("(");
        }

        /**
         *
         * @param column the column name
         * @param typeAndConstraints column type like INT, VARCHAR(56)... and/or
         * constraints like NOT NULL, DEFAULT 8...
         * @return this
         */
        public CreateTableClause addColumn(String column,
                String typeAndConstraints) {
            query.append("`").append(column).append("` ").
                    append(typeAndConstraints).append(", ");
            return this;
        }

        public CreateTableClause addPrimaryKey(String column) {
            query.append("PRIMARY KEY(`").append(column).append("`), ");
            return this;
        }

        public CreateTableClause addForeignKey(String column,
                String foreighTable, String foreignColumn, String options) {
            query.append("FOREIGN KEY(`").append(column).append("`) ").
                    append("REFERENCES `").append(foreighTable).append("`(`").
                    append(foreignColumn).append("`) ").append(options).
                    append(", ");
            return this;
        }

        public CreateTableClause addForeignKey(String column,
                String foreighTable, String foreignColumn) {
            return addForeignKey(column, foreighTable, foreignColumn, "");
        }

        @Override
        public String toString() {
            if (query.length() > 0) {
                query.replace(query.length() - 2,
                        query.length(), ") ");
            }
            return query.toString();
        }
    }

    public static class WhereClause {

        private final StringBuilder query = new StringBuilder();

        public WhereClause() {
        }

        public WhereClause AND() {
            query.append("AND ");
            return this;
        }

        public WhereClause OR() {
            query.append("OR ");
            return this;
        }

        public WhereClause IN(QueryBuilder in) {
            query.append("IN( ").append(in.toString()).append(") ");
            return this;
        }

        public WhereClause NOT_IN(QueryBuilder in) {
            query.append("NOT IN( ").append(in.toString()).append(") ");
            return this;
        }

        public WhereClause EXISTS(QueryBuilder in) {
            query.append("NOT EXISTS( ").append(in.toString()).append(") ");
            return this;
        }

        public WhereClause NOT_EXISTS(QueryBuilder in) {
            query.append("NOT EXISTS( ").append(in.toString()).append(") ");
            return this;
        }

        public WhereClause compareWithValue(String column, String comparator,
                String value) {
            String newValue = escape(value);
            query.append("`").append(column).append("`").append(comparator).
                    append("'").append(newValue).append("' ");
            return this;
        }

        public WhereClause compareWithValue(String table, String column,
                String comparator, String value) {
            String newValue = escape(value);
            query.append("`").append(table).append("`.`").append(column).
                    append("`").append(comparator).append("'").
                    append(newValue).append("' ");
            return this;
        }

        public WhereClause compareWithColumn(String column1, String comparator,
                String column2) {
            query.append("`").append(column1).append("`").append(comparator).
                    append("`").append(column2).append("` ");
            return this;
        }

        public WhereClause compareWithColumn(String table1, String column1,
                String comparator, String table2, String column2) {
            query.append("`").append(table1).append("`.`").append(column1).
                    append("`").append(comparator).append("`").append(table2).
                    append("`.`").append(column2).append("` ");
            return this;
        }

        @Override
        public String toString() {
            return query.toString();
        }

    }

    public static class SetClause {

        private final StringBuilder query = new StringBuilder();

        public SetClause() {
        }

        public SetClause add(String column, String value) {
            String newValue = escape(value);
            query.append("`").append(column).append("`='").
                    append(newValue).append("', ");
            return this;
        }

        @Override
        public String toString() {
            if (query.length() > 0 && query.toString().endsWith(", ")) {
                query.replace(query.length() - 2,
                        query.length(), " ");
            }
            return query.toString();
        }
    }

    public static class InsertColumns {

        private final StringBuilder query = new StringBuilder();

        public InsertColumns() {
            query.append("(");
        }

        public InsertColumns addColumns(String... columns) {
            for (String c : columns) {
                query.append("`").append(c).append("`, ");
            }
            return this;
        }

        @Override
        public String toString() {
            if (query.toString().endsWith(", ")) {
                query.replace(query.length() - 2, query.length(), ") ");
            }
            return query.toString();
        }
    }

    public static class InsertValues {

        private final StringBuilder query = new StringBuilder();

        public InsertValues() {
            query.append("(");
        }

        public InsertValues addValues(String... values) {
            for (String v : values) {
                String newValue = escape(v);
                query.append("'").append(newValue).append("', ");
            }
            return this;
        }

        @Override
        public String toString() {
            if (query.toString().endsWith(", ")) {
                query.replace(query.length() - 2, query.length(), ") ");
            }
            return query.toString();
        }
    }
}
