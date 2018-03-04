package database.entity;

public class PartnersEntity {

    private int id;
    private int id_booking_list;
    private long employee_id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_booking_list() {
        return id_booking_list;
    }

    public void setId_booking_list(int id_booking_list) {
        this.id_booking_list = id_booking_list;
    }

    public long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(long employee_id) {
        this.employee_id = employee_id;
    }
}
