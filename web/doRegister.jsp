<%@page language="java" pageEncoding="UTF-8" %>
<%-- Импортировать JSTL-библиотеку --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- Импортировать собственную библиотеку тегов --%>
<%@taglib prefix="ad" uri="http://bsu.rfe.java.teacher.tag/ad" %>
<%-- Указать, что мы ожидаем данные в кодировке UTF-8 --%>
<fmt:requestEncoding value="UTF-8" />
<%-- Удалить из сессии старые данные --%>
<c:remove var="userData" />
<%-- Сконструировать новый JavaBean в области видимости сессии --%>
<jsp:useBean id="userData" class="entity.User" scope="session" />
<%-- Скопировать в bean все параметры из HTTP-запроса --%>
<jsp:setProperty name="userData" property="*" />
<%-- Обратиться к собственному тегу для сохранения пользователя --%>
<ad:addUser user="${userData}" />
<%-- Проанализировать переменную errorMessage в области видимости session --%>
<c:choose>
    <c:when test="${sessionScope.errorMessage==null}">
        <%-- Ошибок не возникло, удалить из сессии сохранѐнные данные
        пользователя --%>
        <c:remove var="userData" scope="session" />
        <%-- Инициировать процесс аутентификации --%>
        <jsp:forward page="/doLogin.jsp" />
    </c:when>
    <c:otherwise>
        <%-- Переадресовать на форму регистрации --%>
        <c:redirect url="/register.jsp" />
    </c:otherwise>
</c:choose>