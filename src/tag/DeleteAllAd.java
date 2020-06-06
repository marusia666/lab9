package tag;

import entity.Ad;
import entity.AdList;
import entity.User;
import helper.AdListHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class DeleteAllAd extends SimpleTagSupport {
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

        if (errorMessage == null) {
            for(Ad advertisement : adList.getAds()){
                if (currentUser != null && (advertisement.getId() <= 0 || advertisement.getAuthorId() == currentUser.getId())) {
                    adList.deleteAd(advertisement);
                }
            }
            AdListHelper.saveAdList(adList);
        }

        getJspContext().setAttribute("errorMessage", errorMessage, PageContext.SESSION_SCOPE);
    }
}
