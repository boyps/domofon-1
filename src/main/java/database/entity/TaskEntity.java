package database.entity;

import java.sql.Time;
import java.sql.Timestamp;

public class TaskEntity {


    private int id;
    private String text_in;
    private Timestamp date;
    private Time time;
    private int id_status;
    private  long superintendent_id;
    private  long logist_id;
    private int answers;

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText_in() {
        return text_in;
    }

    public void setText_in(String text_in) {
        this.text_in = text_in;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public long getSuperintendent_id() {
        return superintendent_id;
    }

    public void setSuperintendent_id(long superintendent_id) {
        this.superintendent_id = superintendent_id;
    }

    public long getLogist_id() {
        return logist_id;
    }

    public void setLogist_id(long logist_id) {
        this.logist_id = logist_id;
    }

    public int getAnswers() {
        return answers;
    }

    public void setAnswers(int answers) {
        this.answers = answers;
    }
}
