package com.academy;

import com.academy.controller.ConsoleController;
import com.academy.repository.FilmRepositoryImpl;
import com.academy.repository.TicketRepositoryImpl;
import com.academy.repository.UserRepositoryImpl;
import com.academy.service.FilmServiceImpl;
import com.academy.service.TicketServiceImpl;
import com.academy.service.UserServiceImpl;

import java.util.logging.Logger;

import static com.academy.handler.Handler.handler;


public class Launcher {
    private static final Logger log = Logger.getLogger(Launcher.class.getSimpleName());

    public static void main(String[] args) {
        log.info("\nЗапуск программы\n");
        handler();
        UserRepositoryImpl userRepositoryImpl = new UserRepositoryImpl();
        FilmRepositoryImpl filmRepositoryImpl = new FilmRepositoryImpl();
        TicketRepositoryImpl ticketRepositoryImpl = new TicketRepositoryImpl();
        TicketServiceImpl ticketService = new TicketServiceImpl(ticketRepositoryImpl);
        UserServiceImpl userService = new UserServiceImpl(userRepositoryImpl);
        FilmServiceImpl filmService = new FilmServiceImpl(filmRepositoryImpl);
        ConsoleController controller = new ConsoleController(userService, filmService, ticketService);
        controller.start();


    }
}
