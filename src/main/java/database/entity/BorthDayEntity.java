package database.entity;

import java.util.Date;

public class BorthDayEntity {

    private int id;
    private String name;
    private Date both_day;
    private String user_name;
    private boolean male;

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Date getBoth_day() {
        return both_day;
    }

    public void setBoth_day(Date both_day) {
        this.both_day = both_day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
