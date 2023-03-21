package dev.pages.ehsan.user;

import java.io.Serializable;
import java.time.LocalDate;

public class Bus implements Serializable {
    private int id;
    private String from;
    private String to;
    private LocalDate date;
    private String time;

    public Bus(int id, String from, String to, LocalDate date, String time) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
