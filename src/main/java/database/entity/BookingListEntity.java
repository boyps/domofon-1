package database.entity;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class BookingListEntity {

    private  int id;
    private String target;
    private Time time_begin;
    private Time time_end;
    private Date date;
    private long superintendent_id;

    public long getSuperintendent_id() {
        return superintendent_id;
    }

    public void setSuperintendent_id(long superintendent_id) {
        this.superintendent_id = superintendent_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Time getTime_begin() {
        return time_begin;
    }

    public void setTime_begin(Time time_begin) {
        this.time_begin = time_begin;
    }

    public Time getTime_end() {
        return time_end;
    }

    public void setTime_end(Time time_end) {
        this.time_end = time_end;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
