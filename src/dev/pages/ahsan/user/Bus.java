package dev.pages.ahsan.user;

public class Bus {
    int id;
    String from;
    String to;
    String time;

    public Bus(int id, String from, String to, String time) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
