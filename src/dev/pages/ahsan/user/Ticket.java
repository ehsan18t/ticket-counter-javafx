package dev.pages.ahsan.user;

import java.io.Serializable;
import java.time.LocalDate;

public class Ticket implements Serializable {
    String from;
    String to;
    LocalDate date;
    String time;
    String seat;
    int bus;

    public Ticket(String from, String to, LocalDate date, String time, String seat, int bus) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.seat = seat;
        this.bus = bus;
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

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public int getBus() {
        return bus;
    }

    public void setBus(int bus) {
        this.bus = bus;
    }
}
