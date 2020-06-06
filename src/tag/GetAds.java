package tag;

import entity.Ad;
import entity.AdList;
import entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GetAds extends SimpleTagSupport {
    private int id = 0;
    // Поле данных для атрибута range (диапазон объявлений)
    private String range;
    // Поле данных для атрибута sort (основание сортировки)
    private String sort;
    // Поле данных для атрибута dir (направление сортировки)
    private char dir;
    // Поле данных для атрибута var (контейнер результата)
    private String var;

    // Метод-сеттер для установки атрибута (вызывается контейнером)
    public void setId(int id) {
        this.id = id;
    }

    // Метод-сеттер для установки атрибута (вызывается контейнером)
    public void setRange(String range) {
        this.range = range.toLowerCase();
    }

    // Метод-сеттер для установки атрибута (вызывается контейнером)
    public void setSort(String sort) {
        this.sort = sort.toLowerCase();
    }

    // Метод-сеттер для установки атрибута (вызывается контейнером)
    public void setDir(char dir) {
        this.dir = Character.toLowerCase(dir);
    }

    // Метод-сеттер для установки атрибута (вызывается контейнером)
    public void setVar(String var) {
        this.var = var;
    }

    public void doTag() throws JspException, IOException {
        // Извлечь из контекста приложения общий список объявлений
        final AdList adList = (AdList) getJspContext().getAttribute("ads", PageContext.APPLICATION_SCOPE);
        if (id > 0) {
            // Если требуется извлечь данные только 1 объявления
            for (Ad ad : adList.getAds()) {
                if (ad.getId() == id) {
                    // Сохранить найденное объявление в переменную varName
                    getJspContext().setAttribute(GetAds.this.var, ad, PageContext.PAGE_SCOPE);
                }
            }
        } else {
            // Необходимо построение выборки объявлений
            // Извлечь из сессии bean аутентифицированного пользователя
            final User authUser = (User) getJspContext().getAttribute("authUser", PageContext.SESSION_SCOPE);
            // В этом списке будут содержаться только отобранные объявления
            ArrayList<Ad> sortedList = new ArrayList<Ad>();
            // Проанализировать все объявления на доске объявлений
            for (Ad ad : adList.getAds()) {
                // Если режим фильтрации собственные сообщений выключен
                // или текущее объявление принадлежит пользователю
                if (!"my".equals(range) || (authUser != null &&
                        authUser.getId() == ad.getAuthorId())) {
                    // Добавить объявление в список
                    sortedList.add(ad);
                }
            }
            // Как анонимный класс определить и создать экземпляр компаратора
            // Именно компаратор будет отвечать за сортировку объявлений заданным способом
            Comparator<Ad> comparator = new Comparator<Ad>() {
                public int compare(Ad ad1, Ad ad2) {
                    int result;
                    // Если выбрана сортировка по дате последнего изменения объявления
                    if (GetAds.this.sort != null &&
                            GetAds.this.sort.equals("date")) {
                        // То результат определяется по значениям поля lastModified
                        result = ad1.getLastModified() < ad2.getLastModified() ? -1 : (ad1.getLastModified() > ad2.getLastModified() ? 1 : 0);
                        // Если порядок сортировки обратный - инвертируем результат сравнения
                        if (GetAds.this.dir == 'd') {
                            result = -result;
                        }
                    } else
                        // Если выбрана сортировка по теме объявления
                        if (GetAds.this.sort != null && GetAds.this.sort.equals("subject")) {
                            // То результат определяется по значениям поля subject
                            result = ad1.getSubject().compareTo(ad2.getSubject());
                            // Если порядок сортировки обратный - инвертируем результат сравнения
                            if (GetAds.this.dir == 'd') {
                                result = -result;
                            }
                        } else {
                            // Иначе сортируем по автору объявления по значениям поля name автора
                            result = ad1.getAuthor().getName().compareTo(ad2.getAuthor().getName());
                            // Если порядок сортировки обратный - инвертируем результат сравнения
                            if (GetAds.this.dir == 'd') {
                                result = -result;
                            }
                        }
                    return result;
                }
            };
            if (sortedList.size() == 0) {
                // Если не найдено ни одной записи, то возвратить null
                sortedList = null;
            } else {
                // Отсортировать список
                Collections.sort(sortedList, comparator);
            }
            // Сохранить отсортированный список в переменной с именем varName
            // В контексте страницы
            getJspContext().setAttribute(GetAds.this.var, sortedList,
                    PageContext.PAGE_SCOPE);
        }
    }
}
