package com.academy.controller;

import com.academy.entity.film.Film;
import com.academy.entity.ticket.Ticket;
import com.academy.entity.user.Level;
import com.academy.entity.user.User;
import com.academy.repository.FilmRepositoryImpl;
import com.academy.service.FilmServiceImpl;
import com.academy.service.TicketServiceImpl;
import com.academy.service.UserServiceImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleController {

    private static final Logger log = Logger.getLogger(ConsoleController.class.getSimpleName());

    private String currentUserName = "UNKNOWN_USER";

    private String targetUserName = "UNKNOWN_USER";

    private final FilmServiceImpl filmService;

    private final UserServiceImpl userService;

    private final TicketServiceImpl ticketService;

    public ConsoleController(UserServiceImpl userService, FilmServiceImpl filmService, TicketServiceImpl ticketService) {
        this.filmService = filmService;
        this.userService = userService;
        this.ticketService = ticketService;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public void start() {
        info("Вход в главное меню");
        Scanner scanner = new Scanner(System.in);
        boolean isTrue = true;
        while (isTrue) {
            System.out.println(MenuConstant.MAIN_MENU);
            System.out.print(MenuConstant.CURSOR);
            String input = scanner.nextLine();
            switch (input) {
                case MenuConstant.THREE -> {
                    info("Завершение программы");
                    isTrue = false;
                }
                case MenuConstant.ONE -> registration();
                case MenuConstant.TWO -> authorization();
                default -> {
                    warning("Введено неверное число");
                    System.out.println(MenuConstant.WRONG_NUMBER);
                    start();
                }
            }
        }
    }

    private void authorization() {
        info("Вход в меню авторизации");
        Scanner scanner = new Scanner(System.in);
        System.out.println(MenuConstant.AUTHORIZATION_LOGIN);
        System.out.print(MenuConstant.CURSOR);
        String login = scanner.nextLine();
        if (MenuConstant.EXIT.equalsIgnoreCase(login)) {
            start();
        } else {
            System.out.println(MenuConstant.AUTHORIZATION_PASSWORD);
            System.out.print(MenuConstant.CURSOR);
            String password = scanner.nextLine();
            if (MenuConstant.EXIT.equalsIgnoreCase(password)) {
                start();
            } else {
                User user = new User(login, password);
                User read = userService.read(user);
                String decrypt = decrypt(read.getPassword());
                read.setPassword(decrypt);
                if (user.equals(read)) {
                    info("Прошел авторизацию");
                    System.out.println(MenuConstant.AUTHORIZED);
                    setCurrentUserName(read.getLogin());
                    getUserMenu(read);
                } else {
                    warning("Не прошел авторизацию");
                    System.out.println(MenuConstant.NOT_AUTHORIZED);
                    authorization();
                }
            }

        }
    }

    private void getUserMenu(User read) {
        if (read.getUserLevel().equals(Level.ADMIN.get())) {
            getAdminMenu();
        } else if (read.getUserLevel().equals(Level.MANAGER.get())) {
            getManagerMenu();
        } else {
            getSimpleUserMenu();
        }
    }

    private void getSimpleUserMenu() {
        Scanner scanner = new Scanner(System.in);
        info("Открыто меню пользователя");
        System.out.println(MenuConstant.USER_MENU);
        System.out.print(MenuConstant.CURSOR);
        String text = scanner.nextLine();
        switch (text) {
            case MenuConstant.THREE:
                break;
            case MenuConstant.ONE:
                getMenuReadAllFilms();
                break;
            case MenuConstant.TWO:
                getUserMenuWorkWithTickets();
                break;
            default:
                warning("Введено неверное число");
                System.out.println(MenuConstant.WRONG_NUMBER);
                getSimpleUserMenu();
        }

    }

    private void getUserMenuWorkWithTickets() {
        info("Вход в меню работы с билетами");
        Scanner scanner = new Scanner(System.in);
        System.out.println(MenuConstant.USER_MENU_WORK_WITH_TICKETS);
        System.out.print(MenuConstant.CURSOR);
        String text = scanner.nextLine();
        switch (text) {
            case MenuConstant.THREE:
                break;
            case MenuConstant.ONE:
                getMenuSellTicket();
                break;
            case MenuConstant.TWO:
                List<Ticket> tickets = getTicketList();
                System.out.println(MenuConstant.TICKET_LIST);
                for (Ticket ticket : tickets) {
                    System.out.println(ticket);
                }
                System.out.println(" ");
                getUserMenuWorkWithTickets();
                break;
            default:
                warning("Введено неверное число");
                System.out.println(MenuConstant.WRONG_NUMBER);
                getSimpleUserMenu();
        }

    }

    private void getMenuSellTicket() {
        info("Вход в меню продажи билетов");
        Scanner scanner = new Scanner(System.in);
        List<Ticket> tickets = getTicketList();
        int ticketIdFromList;
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
        System.out.println(MenuConstant.MENU_SELL_TICKET);
        System.out.print(MenuConstant.CURSOR);
        String text = scanner.nextLine();
        if (text.equalsIgnoreCase(MenuConstant.EXIT)) {
            getSimpleUserMenu();
        } else {
            try {
                int idTicket = Integer.parseInt(text);
                Ticket readTicket = ticketService.read(idTicket);
                for (Ticket ticket : tickets) {
                    if (!ticket.equals(readTicket)) {
                        warning("Введено неверное число");
                        System.out.println(MenuConstant.WRONG_NUMBER);
                        getSimpleUserMenu();
                    } else {
                        ticketIdFromList = tickets.indexOf(ticket);
                        Ticket searchedTicket = tickets.get(ticketIdFromList);
                        boolean sell = ticketService.sell(searchedTicket);
                        if (sell) {
                            info("Билет успешно продан");
                            System.out.println(MenuConstant.TICKET_SOLD);
                            getSimpleUserMenu();
                        } else {
                            warning("Билет не продан");
                            System.out.println(MenuConstant.TICKET_NOT_SOLD);
                            getSimpleUserMenu();
                        }
                    }
                }
            } catch (Exception e) {
                warning("Введено неверное число");
                System.out.println(MenuConstant.WRONG_NUMBER);
                getSimpleUserMenu();
            }
        }

    }

    private List<Ticket> getTicketList() {
        info("Просмотрен список билетов");
        User user = new User();
        if (currentUserName.equalsIgnoreCase(MenuConstant.MANAGER)) {
            user.setLogin(targetUserName);
        } else {
            user.setLogin(currentUserName);
        }
        User currentUser = userService.read(user);
        return ticketService.readAllUserTickets(currentUser);


    }

    private void getMenuReadAllFilms() {
        info("Вход в меню покупки билетов");
        Scanner scanner = new Scanner(System.in);
        List<Film> films = filmService.readAll();
        int filmsSize = films.size();
        System.out.println(MenuConstant.FILM_LIST);
        for (Film film : films) {
            System.out.println(film);
        }
        System.out.println(MenuConstant.BUY_TICKET_MENU);
        System.out.print(MenuConstant.CURSOR);
        String text = scanner.nextLine();
        if (text.equalsIgnoreCase(MenuConstant.EXIT)) {
            getSimpleUserMenu();
        } else {
            try {
                int idFilm = Integer.parseInt(text);
                if (idFilm > filmsSize) {
                    warning("Введено неверное число");
                    System.out.println(MenuConstant.WRONG_NUMBER);
                    getMenuReadAllFilms();
                } else {
                    Film film = FilmRepositoryImpl.readFilmById(idFilm);
                    List<Integer> purchasedTickets = ticketService.readPurchasedTickets(film);
                    List<Integer> emptyPlaces = checkEmptyPlaces(purchasedTickets);
                    info("Выбирает билет");
                    System.out.println(MenuConstant.CHOOSE_TICKET);
                    System.out.println(emptyPlaces);
                    System.out.print(MenuConstant.CURSOR);
                    String number = scanner.nextLine();
                    try {
                        int seatNumber = Integer.parseInt(number);
                        if (seatNumber < 1 || seatNumber > 30) {
                            warning("Введено неверное число");
                            System.out.println(MenuConstant.WRONG_NUMBER);
                            getMenuReadAllFilms();
                        } else {
                            User user = new User();
                            user.setLogin(currentUserName);
                            User currentUser = userService.read(user);
                            boolean buyTicket = ticketService.buy(film, seatNumber, currentUser);
                            if (buyTicket) {
                                info("Билет куплен");
                                System.out.println(MenuConstant.TICKET_BOUGHT);
                                getSimpleUserMenu();
                            } else {
                                warning("Билет не куплен");
                                System.out.println(MenuConstant.TICKET_NOT_BOUGHT);
                                getSimpleUserMenu();
                            }
                        }


                    } catch (Exception e) {
                        warning("Введено неверное число");
                        System.out.println(MenuConstant.WRONG_NUMBER);
                        getMenuReadAllFilms();
                    }
                }
            } catch (Exception e) {
                warning("Введено неверное число");
                System.out.println(MenuConstant.WRONG_NUMBER);
                getMenuReadAllFilms();
            }
        }
    }

    private List<Integer> checkEmptyPlaces(List<Integer> purchasedTickets) {
        List<Integer> allTickets = new ArrayList<>();
        for (int i = 1; i < 31; i++) {
            allTickets.add(i);
        }
        for (Integer integer : purchasedTickets) {
            if (allTickets.contains(integer)) {
                int x = allTickets.indexOf(integer);
                allTickets.set(x, 0);
            }
        }
        return allTickets;
    }

    private void getManagerMenu() {
        info("Открыто меню менеджера");
        Scanner scanner = new Scanner(System.in);
        System.out.println(MenuConstant.MANAGER_MENU);
        System.out.print(MenuConstant.CURSOR);
        String text = scanner.nextLine();
        switch (text) {
            case MenuConstant.THREE:
                break;
            case MenuConstant.ONE:
                getManagerMenuWorkWithUsers();
                break;
            case MenuConstant.TWO:
                getManagerMenuWorkWithFilms();
                break;
            default:
                warning("Введено неверное число");
                System.out.println(MenuConstant.WRONG_NUMBER);
                getManagerMenu();
        }
    }

    private void getManagerMenuWorkWithUsers() {
        info("Открыто меню менеджера работы с пользователями");
        Scanner scanner = new Scanner(System.in);
        System.out.println(MenuConstant.MANAGER_WORK_WITH_USERS);
        System.out.print(MenuConstant.CURSOR);
        String userName = scanner.nextLine();
        User user = new User();
        user.setLogin(userName);
        User readUser = userService.read(user);
        if (!userName.equals(readUser.getLogin())) {
            warning("Введен неверный логин! Пользователь не найден");
            System.out.println(MenuConstant.USER_NOT_FOUND);
            getManagerMenuWorkWithUsers();
        } else {
            targetUserName = userName;
            info("Открыто меню менеджера работы с пользователем " + targetUserName);
            System.out.println(MenuConstant.USER_FOUND);
            System.out.println(MenuConstant.MANAGER_BUY_SELL_TICKETS);
            System.out.print(MenuConstant.CURSOR);
            String text = scanner.nextLine();
            switch (text) {
                case MenuConstant.THREE:
                    break;
                case MenuConstant.ONE:
                    getMenuBuyTickets();
                    break;
                case MenuConstant.TWO:
                    getManagerMenuSellTicket();
                    break;
                default:
                    warning("Введено неверное число");
                    System.out.println(MenuConstant.WRONG_NUMBER);
                    getManagerMenu();
            }

        }
    }

    private void getManagerMenuSellTicket() {
        info("Открыто меню менеджера продажи билетов");
        Scanner scanner = new Scanner(System.in);
        List<Ticket> tickets = getTicketList();
        int ticketIdFromList;
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
        System.out.println(MenuConstant.MENU_SELL_TICKET);
        System.out.print(MenuConstant.CURSOR);
        String text = scanner.nextLine();
        if (text.equalsIgnoreCase(MenuConstant.EXIT)) {
            getManagerMenu();
        } else {
            try {
                int idTicket = Integer.parseInt(text);
                Ticket readTicket = ticketService.read(idTicket);
                for (Ticket ticket : tickets) {
                    if (!ticket.equals(readTicket)) {
                        warning("Введено неверное число");
                        System.out.println(MenuConstant.WRONG_NUMBER);
                        getManagerMenu();
                    } else {
                        ticketIdFromList = tickets.indexOf(ticket);
                        Ticket searchedTicket = tickets.get(ticketIdFromList);
                        boolean sell = ticketService.sell(searchedTicket);
                        if (sell) {
                            info("Возвращен требуемый билет");
                            System.out.println(MenuConstant.TICKET_SOLD);
                            getManagerMenu();
                        } else {
                            warning("Билет не возвращен");
                            System.out.println(MenuConstant.TICKET_NOT_SOLD);
                            getManagerMenu();
                        }
                    }
                }
            } catch (Exception e) {
                warning("Введено неверное число");
                System.out.println(MenuConstant.WRONG_NUMBER);
                getManagerMenu();
            }
        }

    }

    private void getMenuBuyTickets() {
        info("Открыто меню менеджера покупки билетов");
        Scanner scanner = new Scanner(System.in);
        List<Film> films = filmService.readAll();
        int filmsSize = films.size();
        System.out.println(MenuConstant.FILM_LIST);
        for (Film film : films) {
            System.out.println(film);
        }
        System.out.println(MenuConstant.BUY_TICKET_MENU);
        System.out.print(MenuConstant.CURSOR);
        String text = scanner.nextLine();
        if (text.equalsIgnoreCase(MenuConstant.EXIT)) {
            getManagerMenu();
        } else {
            try {
                int idFilm = Integer.parseInt(text);
                if (idFilm > filmsSize) {
                    System.out.println(MenuConstant.WRONG_NUMBER);
                    getMenuBuyTickets();
                } else {
                    Film film = FilmRepositoryImpl.readFilmById(idFilm);
                    List<Integer> purchasedTickets = ticketService.readPurchasedTickets(film);
                    List<Integer> emptyPlaces = checkEmptyPlaces(purchasedTickets);
                    System.out.println(MenuConstant.CHOOSE_TICKET);
                    System.out.println(emptyPlaces);
                    System.out.print(MenuConstant.CURSOR);
                    String number = scanner.nextLine();
                    try {
                        int seatNumber = Integer.parseInt(number);
                        if (seatNumber < 1 || seatNumber > 30) {
                            System.out.println(MenuConstant.WRONG_NUMBER);
                            getMenuBuyTickets();
                        } else {
                            User user = new User();
                            user.setLogin(targetUserName);
                            User currentUser = userService.read(user);
                            boolean buyTicket = ticketService.buy(film, seatNumber, currentUser);
                            if (buyTicket) {
                                info("Билет куплен для пользователя: " + targetUserName);
                                System.out.println(MenuConstant.TICKET_BOUGHT);
                                getManagerMenu();
                            } else {
                                warning("Билет не куплен для пользователя: " + targetUserName);
                                System.out.println(MenuConstant.TICKET_NOT_BOUGHT);
                                getManagerMenu();
                            }
                        }


                    } catch (Exception e) {
                        warning("Введено неверное число");
                        System.out.println(MenuConstant.WRONG_NUMBER);
                        getMenuReadAllFilms();
                    }
                }
            } catch (Exception e) {
                warning("Введено неверное число");
                System.out.println(MenuConstant.WRONG_NUMBER);
                getMenuReadAllFilms();
            }
        }
    }

    private void getManagerMenuWorkWithFilms() {
        info("Открыто меню менеджера редактирования фильмов");
        Scanner scanner = new Scanner(System.in);
        Film oldFilm = new Film();
        System.out.println(MenuConstant.MANAGER_OLD_FILM_NAME);
        System.out.print(MenuConstant.CURSOR);
        String oldFilmName = scanner.nextLine();
        if (oldFilmName.equalsIgnoreCase(MenuConstant.EXIT)) {
            getManagerMenu();
        } else {
            oldFilm.setName(oldFilmName);
            System.out.println(MenuConstant.MANAGER_UPD_FILM_NAME);
            System.out.print(MenuConstant.CURSOR);
            String updFilmName = scanner.nextLine();
            if (updFilmName.equalsIgnoreCase(MenuConstant.EXIT)) {
                getManagerMenu();
            } else {
                System.out.println(MenuConstant.MANAGER_UPD_FILM_DATE);
                System.out.print(MenuConstant.CURSOR);
                String dateTime = scanner.nextLine();
                if (dateTime.equalsIgnoreCase(MenuConstant.EXIT)) {
                    getManagerMenu();
                } else if (checkDateTime(dateTime)) {
                    Film updFilm = new Film(updFilmName, LocalDateTime.parse(dateTime));
                    boolean update = filmService.update(oldFilm, updFilm);
                    if (update) {
                        info("Фильм обновлен");
                        System.out.println(MenuConstant.FILM_UPDATED);
                        getManagerMenu();
                    } else {
                        warning("Фильм не обновлен");
                        System.out.println(MenuConstant.FILM_NOT_UPDATED);
                        getManagerMenuWorkWithFilms();
                    }

                } else {
                    warning("Введена неверная дата");
                    System.out.println(MenuConstant.WRONG_DATE);
                    getManagerMenuWorkWithFilms();
                }

            }
        }
    }

    private boolean checkDateTime(String dateTime) {
        Pattern pattern = Pattern.compile("[\\d-T:]{16}");
        Matcher matcher = pattern.matcher(dateTime);
        return matcher.matches();
    }

    private void getAdminMenu() {
        info("Открыто меню админа");
        Scanner scanner = new Scanner(System.in);
        System.out.println(MenuConstant.ADMIN_MENU);
        System.out.print(MenuConstant.CURSOR);
        String text = scanner.nextLine();
        switch (text) {
            case MenuConstant.THREE:
                break;
            case MenuConstant.ONE:
                getAdminMenuWorkWithUsers();
                break;
            case MenuConstant.TWO:
                getAdminMenuWorkWithFilms();
                break;
            default:
                warning("Введено неверное число");
                System.out.println(MenuConstant.WRONG_NUMBER);
                getAdminMenu();
        }
    }

    private void getAdminMenuWorkWithFilms() {
        info("Открыто меню работы с фильмами");
        Scanner scanner = new Scanner(System.in);
        System.out.println(MenuConstant.ADMIN_MENU_FILMS);
        System.out.print(MenuConstant.CURSOR);
        String text = scanner.nextLine();
        switch (text) {
            case MenuConstant.FOUR:
                break;
            case MenuConstant.ONE:
                getMenuCreateFilm();
                break;
            case MenuConstant.TWO:
                getMenuDeleteFilm();
                break;
            case MenuConstant.THREE:
                getMenuUpdateFilm();
                break;
            default:
                warning("Введено неверное число");
                System.out.println(MenuConstant.WRONG_NUMBER);
                getAdminMenuWorkWithFilms();
        }
    }

    private void getMenuUpdateFilm() {
        getManagerMenuWorkWithFilms();
    }

    private void getMenuDeleteFilm() {
        info("Открыто меню удаления фильмов");
        Scanner scanner = new Scanner(System.in);
        Film film = new Film();
        System.out.println(MenuConstant.MENU_DELETE_FILM);
        System.out.print(MenuConstant.CURSOR);
        String filmName = scanner.nextLine();
        if (filmName.equalsIgnoreCase(MenuConstant.EXIT)) {
            getAdminMenu();
        } else {
            film.setName(filmName);
            boolean result = filmService.delete(film);
            if (result) {
                System.out.println(MenuConstant.FILM_DELETED);
                getAdminMenuWorkWithFilms();
            } else {
                System.out.println(MenuConstant.FILM_NOT_DELETED);
                getAdminMenuWorkWithFilms();
            }
        }

    }

    private void getMenuCreateFilm() {
        info("Открыто меню создания фильма");
        Scanner scanner = new Scanner(System.in);
        System.out.println(MenuConstant.MENU_CREATE_FILM_NAME);
        System.out.print(MenuConstant.CURSOR);
        String filmName = scanner.nextLine();
        if (filmName.equalsIgnoreCase(MenuConstant.EXIT)) {
            getAdminMenu();
        } else {
            System.out.println(MenuConstant.MENU_CREATE_FILM_DATE);
            System.out.print(MenuConstant.CURSOR);
            String filmDateTime = scanner.nextLine();
            if (filmDateTime.equalsIgnoreCase(MenuConstant.EXIT)) {
                getAdminMenu();
            } else if (checkDateTime(filmDateTime)) {
                LocalDateTime localDateTime = LocalDateTime.parse(filmDateTime);
                Film film = new Film(filmName, localDateTime);
                boolean result = filmService.create(film);
                if (result) {
                    info("Фильм добавлен в афишу");
                    System.out.println(MenuConstant.FILM_ADDED);
                    getAdminMenuWorkWithFilms();
                } else {
                    warning("Фильм не добавлен в афишу");
                    System.out.println(MenuConstant.FILM_NOT_ADDED);
                    getAdminMenuWorkWithFilms();
                }
            } else {
                warning("Введена неверная дата");
                System.out.println(MenuConstant.WRONG_DATE);
                getMenuCreateFilm();
            }

        }

    }

    private void getAdminMenuWorkWithUsers() {
        info("Открыто меню работы с фильмами");
        Scanner scanner = new Scanner(System.in);
        System.out.println(MenuConstant.ADMIN_MENU_WORK_WITH_USERS);
        System.out.print(MenuConstant.CURSOR);
        String text = scanner.nextLine();
        switch (text) {
            case MenuConstant.FOUR:
                break;
            case MenuConstant.ONE:
                getMenuCreateUser();
                break;
            case MenuConstant.TWO:
                getMenuDeleteUser();
                break;
            case MenuConstant.THREE:
                getMenuUpdateUser();
                break;
            default:
                warning("Введено неверное число");
                System.out.println(MenuConstant.WRONG_NUMBER);
                getAdminMenuWorkWithUsers();
        }
    }

    private void getMenuUpdateUser() {
        info("Открыто меню обновления пользователя");
        Scanner scanner = new Scanner(System.in);
        System.out.println(MenuConstant.MENU_UPDATE_OLD_USER_LOGIN);
        System.out.print(MenuConstant.CURSOR);
        String oldLogin = scanner.nextLine();
        if (oldLogin.equalsIgnoreCase(MenuConstant.EXIT)) {
            getAdminMenu();
        } else {
            User oldUser = new User();
            oldUser.setLogin(oldLogin);
            System.out.println(MenuConstant.MENU_UPDATE_UPD_USER_LOGIN);
            System.out.print(MenuConstant.CURSOR);
            String updLogin = scanner.nextLine();
            if (updLogin.equalsIgnoreCase(MenuConstant.EXIT)) {
                getAdminMenu();
            } else {
                System.out.println(MenuConstant.MENU_UPDATE_USER_PASSWORD);
                System.out.print(MenuConstant.CURSOR);
                String password = scanner.nextLine();
                if (password.equalsIgnoreCase(MenuConstant.EXIT)) {
                    getAdminMenu();
                } else {
                    System.out.println(MenuConstant.MENU_UPDATE_USER_LEVEL);
                    System.out.print(MenuConstant.CURSOR);
                    String userLevel = scanner.nextLine();
                    if (userLevel.equalsIgnoreCase(MenuConstant.EXIT)) {
                        getAdminMenu();
                    } else {
                        String encrypt = encrypt(password);
                        User updUser = new User(updLogin, encrypt);
                        if (userLevel.equalsIgnoreCase(MenuConstant.ADMIN)) {
                            updUser.setUserLevel(Level.ADMIN.get());
                        } else if (userLevel.equalsIgnoreCase(MenuConstant.MANAGER)) {
                            updUser.setUserLevel(Level.MANAGER.get());
                        }
                        boolean update = userService.update(oldUser, updUser);
                        if (update) {
                            info("Пользователь успешно обновлен");
                            System.out.println(MenuConstant.USER_UPDATED);
                            getAdminMenuWorkWithUsers();
                        } else {
                            warning("Пользователь не обновлен");
                            System.out.println(MenuConstant.USER_NOT_UPDATED);
                            getAdminMenuWorkWithUsers();
                        }
                    }

                }

            }

        }

    }

    private void getMenuDeleteUser() {
        info("Открыто меню удаления пользователя");
        Scanner scanner = new Scanner(System.in);
        System.out.println(MenuConstant.MENU_DELETE_USER);
        System.out.print(MenuConstant.CURSOR);
        String login = scanner.nextLine();
        if (login.equalsIgnoreCase(MenuConstant.ADMIN) || login.equalsIgnoreCase(MenuConstant.MANAGER)) {
            System.out.println(MenuConstant.NOT_CORRECT_NAME);
            getMenuDeleteUser();
        } else if (login.equalsIgnoreCase(MenuConstant.EXIT)) {
            getAdminMenu();
        } else {
            User user = new User();
            user.setLogin(login);
            boolean result = userService.delete(user);
            if (result) {

                System.out.println(MenuConstant.USER_DELETED);
                getAdminMenuWorkWithUsers();
            } else {
                System.out.println(MenuConstant.USER_NOT_DELETED);
                getAdminMenuWorkWithUsers();
            }
        }

    }

    private void getMenuCreateUser() {
        info("Открыто меню создания пользователя");
        Scanner scanner = new Scanner(System.in);
        System.out.println(MenuConstant.MENU_CREATE_USER_LOGIN);
        System.out.print(MenuConstant.CURSOR);
        String login = scanner.nextLine();
        if (login.equalsIgnoreCase(MenuConstant.EXIT)) {
            getAdminMenu();
        } else {
            System.out.println(MenuConstant.MENU_CREATE_USER_PASSWORD);
            System.out.print(MenuConstant.CURSOR);
            String password = scanner.nextLine();
            if (password.equalsIgnoreCase(MenuConstant.EXIT)) {
                getAdminMenu();
            } else {
                System.out.println(MenuConstant.MENU_CREATE_USER_LEVEL);
                System.out.print(MenuConstant.CURSOR);
                String userLevel = scanner.nextLine();
                if (userLevel.equalsIgnoreCase(MenuConstant.EXIT)) {
                    getAdminMenu();
                } else {
                    User user = new User(login, password);
                    if (userLevel.equalsIgnoreCase(MenuConstant.ADMIN)) {
                        user.setUserLevel(Level.ADMIN.get());
                    } else if (userLevel.equalsIgnoreCase(MenuConstant.MANAGER)) {
                        user.setUserLevel(Level.MANAGER.get());
                    }
                    boolean result = userService.create(user);
                    if (result) {
                        info("Пользователь успешно добавлен");
                        System.out.println(MenuConstant.USER_ADDED);
                        getAdminMenuWorkWithUsers();
                    } else {
                        warning("Пользователь не добавлен");
                        System.out.println(MenuConstant.USER_NOT_ADDED);
                        getAdminMenuWorkWithUsers();
                    }
                }
            }
        }
    }

    private void registration() {
        info("Открыто меню регистрации");
        Scanner scanner = new Scanner(System.in);
        System.out.println(MenuConstant.REGISTRATION_LOGIN);
        System.out.print(MenuConstant.CURSOR);
        String textLogin = scanner.nextLine();
        if (textLogin.length() < 3) {
            System.out.println(MenuConstant.NOT_CORRECT_NAME);
            registration();
        } else if (MenuConstant.EXIT.equalsIgnoreCase(textLogin)) {
            start();
        } else {
            System.out.println(MenuConstant.REGISTRATION_PASSWORD);
            System.out.print(MenuConstant.CURSOR);
            String textPassword = scanner.nextLine();
            if (textPassword.length() < 3) {
                System.out.println(MenuConstant.NOT_CORRECT_NAME);
                registration();
            } else if (MenuConstant.EXIT.equalsIgnoreCase(textPassword)) {
                start();
            } else {
                User user = new User();
                user.setLogin(textLogin);
                String encrypt = encrypt(textPassword);
                user.setPassword(encrypt);
                boolean create = userService.create(user);
                if (create) {
                    info("Пользователь создан");
                    System.out.println(MenuConstant.USER_CREATED);
                } else {
                    warning("Пользователь не создан");
                    System.out.println(MenuConstant.USER_NOT_CREATED);
                    registration();
                }
                start();
            }
        }
    }

    private String decrypt(String encryptPass) {
        StringBuilder stringBuilder = new StringBuilder();
        int key = 7;
        char[] chars = encryptPass.toCharArray();
        for (char c : chars) {
            c -= key;
            stringBuilder.append(c);
        }
        return String.valueOf(stringBuilder);
    }

    private String encrypt(String password) {
        StringBuilder stringBuilder = new StringBuilder();
        int key = 7;
        char[] chars = password.toCharArray();
        for (char c : chars) {
            c += key;
            stringBuilder.append(c);
        }
        return String.valueOf(stringBuilder);

    }

    private void info(String text) {
        log.info(text + " Пользователь: " + currentUserName);
    }

    private void warning(String text) {
        log.warning(text + " Пользователь: " + currentUserName);
    }
}
