package tag;

import entity.User;
import entity.UserList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
public class Login extends SimpleTagSupport {
    // Поле данных для атрибута login
    private String login;
    // Поле данных для атрибута password
    private String password;
    // Метод-сеттер для установки атрибута (вызывается контейнером)
    public void setLogin(String login) {
        this.login = login;
    }
    // Метод-сеттер для установки атрибута (вызывается контейнером)
    public void setPassword(String password) {
        this.password = password;
    }
    public void doTag() throws JspException, IOException {
        // Изначально описание ошибки = null (т.е. ошибки нет)
        String errorMessage = null;
        // Извлечь из контекста приложения общий список пользователей
        UserList userList = (UserList)  getJspContext().getAttribute("users", PageContext.APPLICATION_SCOPE);
        if (login==null || login.equals("")) {
            errorMessage = "Логин не может быть пустым!";
        } else {
            // Найти пользователя с таким логином
            User user = userList.findUser(login);
            // Проанализировать результат поиска
            if (user==null || !user.getPassword().equals(password)) {
                // Пользователь с таким логином не найден или неправильный пароль
                errorMessage = "Такой пользователь не существует или указанная комбинация логин/пароль неверна!";
            } else {
                // Логин и пароль верны, аутентифицировать пользователя сохранив user в сессии
                getJspContext().setAttribute("authUser", user, PageContext.SESSION_SCOPE);
            }
        }
        // Сохранить описание ошибки (текст или null) в сессии
        getJspContext().setAttribute("errorMessage", errorMessage, PageContext.SESSION_SCOPE);
    }
}