package dev.pages.ahsan.user;

public class Ticket {
    String uid;
    String from;
    String to;
    String expTime;
    int seat;
    int bus;

    public Ticket(String uid, String from, String to, String expTime) {
        this.uid = uid;
        this.from = from;
        this.to = to;
        this.expTime = expTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getExpTime() {
        return expTime;
    }

    public void setExpTime(String expTime) {
        this.expTime = expTime;
    }
}
