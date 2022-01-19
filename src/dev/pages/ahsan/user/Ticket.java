package dev.pages.ahsan.user;

import java.time.LocalDate;

public class Ticket {
    String from;
    String to;
    LocalDate date;
    String time;
    int seat;
    int bus;

    public Ticket(String from, String to, LocalDate date, String time, int seat, int bus) {
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

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getBus() {
        return bus;
    }

    public void setBus(int bus) {
        this.bus = bus;
    }
}
