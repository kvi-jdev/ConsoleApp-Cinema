package com.academy.repository;

import com.academy.entity.film.Film;
import com.academy.entity.user.User;

import java.util.List;

public interface TicketRepository<Ticket> {

    boolean buy(Film film, int seatNumber, User user);

    boolean sell(Ticket ticket);

    Ticket read(int idTicket);

    List<Ticket> readAllUserTickets(User user);

    List<Integer> readPurchasedTickets(Film film);

}
