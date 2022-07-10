package com.academy.repository;

import com.academy.entity.film.Film;
import com.academy.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FilmRepositoryImpl implements RepositoryOperations<Film> {
    @Override
    public boolean create(Film film) {
        boolean result = false;
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO films (name, " +
                    "date, ticket_prize) VALUES (?,?,?)");
            statement.setString(1, film.getName());
            statement.setTimestamp(2, Timestamp.valueOf(film.getLocalDateTime()));
            statement.setInt(3, film.getTicketPrize());
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
    public boolean delete(Film film) {
        boolean result = false;
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM films WHERE name=?");
            statement.setString(1, film.getName());
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
    public boolean update(Film oldT, Film updT) {
        boolean result = false;
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE films SET name=?, " +
                    "date=?, ticket_prize=? WHERE name=?");
            statement.setString(1, updT.getName());
            statement.setTimestamp(2, Timestamp.valueOf(updT.getLocalDateTime()));
            statement.setInt(3, updT.getTicketPrize());
            statement.setString(4, oldT.getName());
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
    public Film read(Film film) {
        Film filmResult = new Film();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT id_films, name, date, ticket_prize " +
                    "FROM films WHERE name=?, date=?");
            statement.setString(1, film.getName());
            statement.setTimestamp(2, Timestamp.valueOf(film.getLocalDateTime()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_films");
                String name = resultSet.getString("name");
                String date = resultSet.getString("date");
                int ticketPrice = resultSet.getInt("ticket_prize");
                filmResult.setId(id);
                filmResult.setName(name);
                LocalDateTime localDateTime = getDateTime(date);
                film.setLocalDateTime(localDateTime);
                filmResult.setTicketPrize(ticketPrice);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filmResult;
    }

    @Override
    public List<Film> readAll() {
        List<Film> filmsList = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM films");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_films");
                String name = resultSet.getString("name");
                String date = resultSet.getString("date");
                int prize = resultSet.getInt("ticket_prize");
                Film film = new Film();
                film.setId(id);
                film.setName(name);
                film.setTicketPrize(prize);
                LocalDateTime localDateTime = getDateTime(date);
                film.setLocalDateTime(localDateTime);
                filmsList.add(film);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filmsList;
    }

    public List<Film> readFilmsWithDifferentTime(String filmName) {
        List<Film> filmsList = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM films WHERE name=?");
            statement.setString(1, filmName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_films");
                String name = resultSet.getString("name");
                String date = resultSet.getString("date");
                int prize = resultSet.getInt("ticket_prize");
                Film film = new Film();
                film.setId(id);
                film.setName(name);
                film.setTicketPrize(prize);
                LocalDateTime localDateTime = getDateTime(date);
                film.setLocalDateTime(localDateTime);
                filmsList.add(film);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filmsList;
    }

    private static LocalDateTime getDateTime(String date) {
        String[] split = date.split(" ");
        LocalDate localDate = LocalDate.parse(split[0]);
        LocalTime localTime = LocalTime.parse(split[1]);

        return LocalDateTime.of(localDate, localTime);
    }

    public static Film readFilmById(int idFilm) {
        Film filmResult = new Film();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT id_films, name, date, ticket_prize " +
                    "FROM films WHERE id_films=?");
            statement.setInt(1, idFilm);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_films");
                String name = resultSet.getString("name");
                String date = resultSet.getString("date");
                int ticketPrice = resultSet.getInt("ticket_prize");
                filmResult.setId(id);
                filmResult.setName(name);
                LocalDateTime localDateTime = getDateTime(date);
                filmResult.setLocalDateTime(localDateTime);
                filmResult.setTicketPrize(ticketPrice);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filmResult;
    }


}
