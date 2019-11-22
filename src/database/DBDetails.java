package database;

public class DBDetails {
    public static final String username = "aferron";
    public static final String password = "1058100";

    /* Change nothing here */
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    public static final String DB_URL = String.format("jdbc:mysql://dursley.socs.uoguelph.ca:3306/%s?useLegacyDatetimeCode=false&serverTimezone=America/New_York",username);
}