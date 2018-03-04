package database.entity;

public class EmployeeEntity {

    private int id;
    private long superintendent_id;
    private long employee_id;
    private String employee_name;

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSuperintendent_id() {
        return superintendent_id;
    }

    public void setSuperintendent_id(long superintendent_id) {
        this.superintendent_id = superintendent_id;
    }

    public long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(long employee_id) {
        this.employee_id = employee_id;
    }
}
