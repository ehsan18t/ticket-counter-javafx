package dev.pages.ahsan.user;

import java.time.LocalDate;

public class Ticket {
    String uid;
    String from;
    String to;
    LocalDate date;
    String time;
    int seat;
    int bus;

    public Ticket(String uid, String from, String to, LocalDate date, String time, int seat, int bus) {
        this.uid = uid;
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.seat = seat;
        this.bus = bus;
    }
}
