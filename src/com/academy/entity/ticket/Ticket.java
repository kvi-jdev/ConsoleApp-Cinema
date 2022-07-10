package com.academy.entity.ticket;

import com.academy.entity.film.Film;

import java.util.Objects;

public class Ticket {

    private int id;

    private int idUser;

    private String userName;

    private Film film;

    private int seatNumber;

    private int price;

    public Ticket() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", film=" + film +
                ", seatNumber=" + seatNumber +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
