package org.example._socialnetwork_.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Prietenie extends Entity<Long>{
    private Long first;
    private Long second;
    private LocalDateTime date;

    private boolean status;
    private Long sender;

    public Prietenie(Long first, Long second, LocalDateTime date) {
        this.first = first;
        this.second = second;
        this.date = date;
        sender = 0L;
        status = true;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getFirst() {
        return first;
    }

    public void setFirst(Long first) {
        this.first = first;
    }

    public Long getSecond() {
        return second;
    }

    public void setSecond(Long second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prietenie prietenie = (Prietenie) o;
        return Objects.equals(first, prietenie.first) && Objects.equals(second, prietenie.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "Prietenie{" +
                "id1=" + first +
                ", id2=" + second +
                '}';
    }
}
