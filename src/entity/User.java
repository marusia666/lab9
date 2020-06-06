package entity;

import java.io.Serializable;
public class User implements Serializable, Identifiable {
    private static final long serialVersionUID = -5363773994153628499L;
    // Идентификатор пользователя
    private int id;
    // Логин пользователя
    private String login = "";
    // Имя пользователя
    private String name = "";
    // Пароль пользователя
    private String password = "";
    // Email пользователя
    private String email = "";
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int hashCode() {
        return id;
    }
    public boolean equals(Object obj) {
        // Если obj - ссылка на другой объект, равна this, то это один и тот же объект
        if (this == obj)
            return true;
        // Если ссылка на другой объект - null, то объекты не равны
        if (obj == null)
            return false;
        // Если классы объектных ссылок не совпадают, объекты не равны
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        // Результат сравнения решается равенством идентификаторов
        if (id != other.id)
            return false;
        return true;
    }
}
