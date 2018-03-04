package database;


import database.Impl.*;

import javax.sql.DataSource;

public class DaoFactory {

    private DaoFactory() {
    }

    private static DataSource source;
    private static DaoFactory ourInstance = new DaoFactory();

    public void setDataSource(DataSource dataSource) {
        source = dataSource;
    }

    public static DaoFactory getInstance() {
        return ourInstance;
    }


    public UsersDao usersDao() {
        return new UsersDaoImpl(source);
    }



    
}
