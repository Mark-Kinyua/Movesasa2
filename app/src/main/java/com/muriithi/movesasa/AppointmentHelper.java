package com.muriithi.movesasa;

public class AppointmentHelper {

    private String old_loc, new_loc, date, time;

    public AppointmentHelper() {
    }

    public AppointmentHelper(String old_loc,String new_loc, String date, String time) {
        this.new_loc = new_loc;
        this.old_loc = old_loc;
        this.date = date;
        this.time = time;
    }

    public String getOld_loc() {
        return old_loc;
    }

    public void setOld_loc(String old_loc) {
        this.old_loc = old_loc;
    }

    public String getNew_loc() {
        return new_loc;
    }

    public void setNew_loc(String new_loc) {
        this.new_loc = new_loc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
