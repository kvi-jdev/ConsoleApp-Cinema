package com.academy.entity.film;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Film {

    private int id;

    private String name;

    private LocalDateTime localDateTime;

    private int ticketPrize;

    public Film() {

    }

    public Film(String name, LocalDateTime localDateTime) {
        this.name = name;
        this.localDateTime = localDateTime;
        this.ticketPrize = checkPrize(localDateTime);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public int getTicketPrize() {
        return ticketPrize;
    }

    public void setTicketPrize(int ticketPrize) {
        this.ticketPrize = ticketPrize;
    }

    private static LocalDate getDate(LocalDateTime localDateTime) {
        String dateTime = String.valueOf(localDateTime);
        String[] strings = dateTime.split("T");
        return LocalDate.parse(strings[0]);
    }

    private static int checkPrize(LocalDateTime localDateTime) {
        LocalDate minDate = getDate(localDateTime);
        LocalTime minTime = LocalTime.of(17, 0);
        LocalDateTime requiredMinDateTime = LocalDateTime.of(minDate, minTime);
        LocalDateTime requiredMaxDateTime = requiredMinDateTime.plusHours(11L);
        if (localDateTime.isAfter(requiredMinDateTime) && localDateTime.isBefore(requiredMaxDateTime)) {
            return 50;
        } else {
            return 25;
        }
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", localDateTime=" + localDateTime +
                ", ticketPrize=" + ticketPrize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id && name.equals(film.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
