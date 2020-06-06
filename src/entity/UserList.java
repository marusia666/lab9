package entity;

public class UserList extends ListOfIdentifiables<User> {
    private static final long serialVersionUID = 7115985836992230188L;
    public synchronized User findUser(String login) {
        // Ищем пользователя с заданным логином
        for(User user : items) {
            if(user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }
    public synchronized User findUser(Integer id) {
        // Ищем пользователя с заданным идентификатором
        for(User user : items) {
            if(user.getId() == id) {
                return user;
            }
        }
        return null;
    }
    public synchronized User addUser(User user)
            throws UserExistsException {
        // Если пользователь с данным логином уже зарегистрирован, генерируем исключение
        if(findUser(user.getLogin()) != null)
            throw new UserExistsException();
        // Выбрать следующий незанятый id для автора
        user.setId(getNextId());
        // Добавить автора в список
        items.add(user);
        return user;
    }
    // Класс искючения, указывающего при попытке добавления пользователя,
    // что пользователь с заданным логином уже присутствует в системе
    public static class UserExistsException extends Exception {
        private static final long serialVersionUID = 584737643480913385L;
    }
}
