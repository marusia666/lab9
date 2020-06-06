package tag;

import entity.Ad;
import entity.AdList;
import entity.User;
import helper.AdListHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Calendar;
public class UpdateAd extends SimpleTagSupport {
    // Поле данных для атрибута ad
    private Ad ad;
    // Метод-сеттер для установки атрибута (вызывается контейнером)
    public void setAd(Ad ad) {
        this.ad = ad;
    }
    public void doTag() throws JspException, IOException {
        // Изначально описание ошибки = null (т.е. ошибки нет)
        String errorMessage = null;
        // Извлечь из контекста приложения общий список объявлений
        AdList adList = (AdList) getJspContext().getAttribute("ads", PageContext.APPLICATION_SCOPE);
        // Извлечь из сессии описание текущего пользователя
        User currentUser = (User) getJspContext().getAttribute("authUser", PageContext.SESSION_SCOPE);
        // Проверить, что заголовок не пустой
        if (ad.getSubject()==null || ad.getSubject().equals("")) {
            errorMessage = "Заголовок не может быть пустым!";
        } else {
            // Проверить авторство пользователя - имеет ли он право на редактирование
            if (currentUser==null || (ad.getId()>0 && ad.getAuthorId()!=currentUser.getId())) {
                // Произвол! Чужой, а не автор, меняет объявление!
                errorMessage = "Вы пытаетесь изменить сообщение, к которому не обладаете правами доступа!";
            }
        }
        // Если ошибки не было - обновить объявление
        if (errorMessage==null) {
            // Обновить последнюю дату модификации объявления
            ad.setLastModified(Calendar.getInstance().getTimeInMillis());
            // Если id объявлений пустой, то оно создаѐтся
            if (ad.getId()==0) {
                adList.addAd(currentUser, ad);
            } else {
                // Объявление просто обновляется
                adList.updateAd(ad);
            }
            // Записать обновлѐнный список объявлений в файл
            AdListHelper.saveAdList(adList);
        }
        // Сохранить описание ошибки (текст или null) в сессии
        getJspContext().setAttribute("errorMessage", errorMessage, PageContext.SESSION_SCOPE);
    }
}