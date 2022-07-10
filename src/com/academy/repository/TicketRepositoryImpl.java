package com.academy.repository;

import com.academy.entity.film.Film;
import com.academy.entity.ticket.Ticket;
import com.academy.entity.user.User;
import com.academy.util.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketRepositoryImpl implements TicketRepository<Ticket> {

    @Override
    public boolean buy(Film film, int seatNumber, User user) {
        boolean result = false;
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ticket (id_users, " +
                    "id_films, seatNumber, price) VALUES (?,?,?,?)");
            statement.setInt(1, user.getId());
            statement.setInt(2, film.getId());
            statement.setInt(3, seatNumber);
            statement.setInt(4, film.getTicketPrize());
            int executeUpdate = statement.executeUpdate();
            if (executeUpdate > 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return result;
        }

    }

    @Override
    public boolean sell(Ticket ticket) {
        boolean result = false;
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM ticket WHERE id=?");
            statement.setInt(1, ticket.getId());
            int executeUpdate = statement.executeUpdate();
            if (executeUpdate > 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Ticket read(int idTicket) {
        Ticket ticketResult = new Ticket();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket WHERE id=?");
            statement.setInt(1, idTicket);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int idFilms = resultSet.getInt("id_films");
                int seatNumber = resultSet.getInt("seatNumber");
                int price = resultSet.getInt("price");
                int idUser = resultSet.getInt("id_users");
                Film film = FilmRepositoryImpl.readFilmById(idFilms);
                ticketResult.setId(id);
                ticketResult.setSeatNumber(seatNumber);
                ticketResult.setPrice(price);
                ticketResult.setIdUser(idUser);
                ticketResult.setFilm(film);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketResult;
    }

    @Override
    public List<Ticket> readAllUserTickets(User user) {
        List<Ticket> ticketsList = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket WHERE id_users=?");
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idTicket = resultSet.getInt("id");
                int idFilms = resultSet.getInt("id_films");
                int seatNumber = resultSet.getInt("seatNumber");
                int price = resultSet.getInt("price");
                Film film = FilmRepositoryImpl.readFilmById(idFilms);
                Ticket ticket = new Ticket();
                ticket.setId(idTicket);
                ticket.setSeatNumber(seatNumber);
                ticket.setPrice(price);
                ticket.setUserName(user.getLogin());
                ticket.setFilm(film);
                ticketsList.add(ticket);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketsList;
    }

    @Override
    public List<Integer> readPurchasedTickets(Film film) {
        List<Integer> ticketsList = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket WHERE id_films=?");
            statement.setInt(1, film.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int seatNumber = resultSet.getInt("seatNumber");
                ticketsList.add(seatNumber);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketsList;
    }
}
