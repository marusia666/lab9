package tag;

import entity.Ad;
import entity.AdList;
import entity.User;
import helper.AdListHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class DeleteAd extends SimpleTagSupport {
    // Поле данных для атрибута
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
        // Проверить, что объявление изменяется его автором, а не чужаком
        if (currentUser == null || (ad.getId() > 0 && ad.getAuthorId() != currentUser.getId())) {
            // Произвол! Чужой, а не автор, меняет объявление!
            errorMessage = "Вы пытаетесь изменить сообщение, к которому не обладаете правами доступа!";
        }
        if (errorMessage == null) {
            // Непосредственное удаление объявления делает AdList
            adList.deleteAd(ad);
            // Записать обновлѐнный список объявлений в файл
            AdListHelper.saveAdList(adList);
        }
        // Сохранить описание ошибки (текст или null) в сессии
        getJspContext().setAttribute("errorMessage", errorMessage, PageContext.SESSION_SCOPE);
    }
}