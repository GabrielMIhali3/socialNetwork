package org.example._socialnetwork_.domain;

import java.time.LocalDateTime;

public class Message extends Entity<Long>{
    private String message;
    private Utilizator from;
    private Utilizator to;
    private LocalDateTime date;

    public Message(String message, Utilizator from, Utilizator to, LocalDateTime date) {
        this.message = message;
        this.from = from;
        this.to = to;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Utilizator getFrom() {
        return from;
    }

    public void setFrom(Utilizator from) {
        this.from = from;
    }

    public Utilizator getTo() {
        return to;
    }

    public void setTo(Utilizator to) {
        this.to = to;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
