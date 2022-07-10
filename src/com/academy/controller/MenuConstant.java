package com.academy.controller;

public class MenuConstant {

    public static final String CURSOR = "-->";

    public static final String EXIT = "exit";

    public static final String FOUR = "4";

    public static final String THREE = "3";

    public static final String TWO = "2";

    public static final String ONE = "1";

    public static final String ADMIN = "admin";

    public static final String MANAGER = "manager";

    public static final String NOT_CORRECT_NAME = "\nВведено некорректное имя!\n";

    public static final String WRONG_NUMBER = "\nВведено неверное число!\n";

    public static final String AUTHORIZED = "\nАвторизация прошла успешно!\n";

    public static final String FILM_UPDATED = "\nМероприятие успешно обновлено!\n";

    public static final String FILM_NOT_UPDATED = "\nМероприятие успешно обновлено!\n";

    public static final String WRONG_DATE = "\nОшибка! Неверно введена дата!\n";

    public static final String FILM_DELETED = "\nФильм успешно удален!\n";

    public static final String FILM_NOT_DELETED = "\nОшибка! Фильм не удален!\n";

    public static final String FILM_ADDED = "\nФильм успешно добавлен!\n";

    public static final String FILM_NOT_ADDED = "\nОшибка! Фильм не добавлен!\n";

    public static final String USER_UPDATED = "\nПользователь успешно обновлен!\n";

    public static final String USER_NOT_UPDATED = "\nОшибка! Пользователь не обновлен!\n";

    public static final String USER_ADDED = "\nПользователь успешно добавлен!\n";

    public static final String USER_NOT_ADDED = "\nОшибка! Пользователь не добавлен!\n";

    public static final String USER_FOUND = "\nПользователь успешно найден!\n";

    public static final String USER_NOT_FOUND = "\nОшибка! Пользователь не найден!\n";

    public static final String USER_DELETED = "\nПользователь успешно удален!\n";

    public static final String USER_NOT_DELETED = "\nОшибка! Пользователь не удален!\n";

    public static final String TICKET_BOUGHT = "\nБилет успешно куплен!\n";

    public static final String TICKET_NOT_BOUGHT = "\nОшибка! Билет не куплен!\n";

    public static final String TICKET_SOLD = "\nБилет успешно возвращен!\n";

    public static final String TICKET_NOT_SOLD = "\nОшибка! Билет не возвращен!\n";

    public static final String USER_CREATED = "\nПользователь создан!\n";

    public static final String FILM_LIST = "\n* Список фильмов *\n";

    public static final String TICKET_LIST = "\n* Список ваших билетов *\n";

    public static final String USER_NOT_CREATED = """
                                
            Такой пользователь уже существует!
            Попробуйте снова""";

    public static final String REGISTRATION_LOGIN = """
                            
            * Меню регистрации *
            * Введите exit для выхода *
            * Придумайте логин *""";

    public static final String REGISTRATION_PASSWORD = """
                            
            * Меню регистрации *
            * Введите exit для выхода *
            * Придумайте пароль *""";

    public static final String NOT_AUTHORIZED = """
                        
            Неверные логин или пароль
            Повторите попытку!""";

    public static final String AUTHORIZATION_LOGIN = """
                                            
            * Меню авторизации *
            * Введите exit для выхода *
            * Введите логин *""";

    public static final String AUTHORIZATION_PASSWORD = """
                            
            *Меню авторизации*
            * Введите exit для выхода *
            * Введите пароль *""";

    public static final String MAIN_MENU = """
                        
            ******** Главное меню *********
            *Добро пожаловать в кинотеатр!*
            ** Введите 1 для регистрации **
            ** Введите 2 для авторизации **
            **** Введите 3 для выхода *****""";

    public static final String ADMIN_MENU_WORK_WITH_USERS = """
                        
            ******** Меню администратора ********
            * Введите 1 для создания пользователя *
            * Введите 2 для удаления пользователя *
            * Введите 3 для изменения пользователя *
            ******** Введите 4 для выхода *******""";

    public static final String MANAGER_MENU = """
                        
            ******** Меню менеджера ********
            * Введите 1 для работы с пользователями *
            * Введите 2 для работы с мероприятиями  *
            ******** Введите 3 для выхода *******""";

    public static final String MANAGER_OLD_FILM_NAME = """
                            
            ******** Меню редактирования мероприятий ********
            * Введите название мероприятия *
            ******** Введите exit для выхода *******""";

    public static final String MANAGER_UPD_FILM_NAME = """
                                
            ******** Меню редактирования мероприятий ********
            * Введите новое название мероприятия *
            ******** Введите exit для выхода *******""";

    public static final String MANAGER_UPD_FILM_DATE = """
                                    
            ******** Меню редактирования мероприятий ********
            * Введите новую дату мероприятия *
            * Формат 'yyyy-MM-ddTHH:mm' *
            ******** Введите exit для выхода *******""";

    public static final String ADMIN_MENU = """
                            
            ******** Меню администратора ********
            * Введите 1 для работы с пользователями *
            * Введите 2 для работы с мероприятиями  *
            ******** Введите 3 для выхода *******""";

    public static final String ADMIN_MENU_FILMS = """
                            
            ******** Меню администратора ********
            * Введите 1 для создания мероприятия *
            * Введите 2 для удаления мероприятия *
            * Введите 3 для изменения мероприятия *
            ******** Введите 4 для выхода *******""";

    public static final String MENU_DELETE_FILM = """
                            
            * Меню удаления мероприятия *
            * Введите название фильма *
            * Введите exit для выхода *""";

    public static final String MENU_CREATE_FILM_NAME = """
                            
            * Меню создания мероприятия *
            * Введите название фильма *
            * Введите exit для выхода *""";

    public static final String MENU_CREATE_FILM_DATE = """
                            
            * Меню создания мероприятия *
            * Введите дату и время мероприятия *
            * Формат 'yyyy-MM-ddTHH:mm' *
            * Введите exit для выхода *""";

    public static final String MENU_DELETE_USER = """
                            
            * Меню удаления пользователя *
            * Введите логин *
            * Введите exit для выхода *""";

    public static final String MENU_CREATE_USER_LOGIN = """
                            
            * Меню создания пользователя *
            * Введите логин *
            * Введите exit для выхода *""";

    public static final String MENU_CREATE_USER_PASSWORD = """
                            
            * Меню создания пользователя *
            * Введите пароль *
            * Введите exit для выхода *""";

    public static final String MENU_CREATE_USER_LEVEL = """
                            
            * Меню создания пользователя *
            * Введите уровень доступа пользователя *
            * ADMIN/MANAGER/SIMPLE на выбор *
            * Введите exit для выхода *""";

    public static final String MENU_UPDATE_OLD_USER_LOGIN = """
                        
            * Меню изменения пользователя *
            * Введите логин пользователя *
            * Введите exit для выхода *""";

    public static final String MENU_UPDATE_UPD_USER_LOGIN = """
                        
            * Меню изменения пользователя *
            * Введите новый логин *
            * Введите exit для выхода *""";

    public static final String MENU_UPDATE_USER_PASSWORD = """
                        
            * Меню изменения пользователя *
            * Введите новый пароль *
            * Введите exit для выхода *""";

    public static final String MENU_UPDATE_USER_LEVEL = """
                        
            * Меню создания пользователя *
            * Введите уровень доступа пользователя *
            * ADMIN/MANAGER/SIMPLE на выбор *
            * Введите exit для выхода *""";

    public static final String USER_MENU = """
                            
            ******** Меню пользователя ********
            * Введите 1 для просмотра мероприятий *
            * Введите 2 для работы с билетами  *
            ******** Введите 3 для выхода *******""";

    public static final String BUY_TICKET_MENU = """
                            
            ******** Меню покупки билета ********
            * Введите id фильма для покупки билета *
            ********  Введите exit для выхода  *******""";

    public static final String CHOOSE_TICKET = """
                            
            ******** Меню покупки билета ********
            * Выберите № билета *
            * '0' - место занято *
            ********  Введите exit для выхода  *******""";

    public static final String USER_MENU_WORK_WITH_TICKETS = """
                            
             ******** Меню работы с билетами ********
            * Введите 1 для возврата билета *
            * Введите 2 для просмотра ваших билетов  *
            ******** Введите 3 для выхода *******""";

    public static final String MENU_SELL_TICKET = """
                            
             ******** Меню возврата билета ********
            * Выберите id билета из списка для возврата*
            ********  Введите exit для выхода  *******""";

    public static final String MANAGER_WORK_WITH_USERS = """
                            
             ******** Меню менеджера ********
            * Введите имя пользователя для редактирования*
            ********  Введите exit для выхода  *******""";

    public static final String MANAGER_BUY_SELL_TICKETS = """
                            
             ******** Меню менеджера ********
            * Введите 1 для покупки билета пользователю *
            * Введите 2 для вовзрата билета пользователя *
            ********  Введите 3 для выхода  *******""";



    private MenuConstant() {
    }
}
