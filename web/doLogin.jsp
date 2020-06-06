<%@page language="java" pageEncoding="UTF-8" %>
<%-- Импортировать JSTL-библиотеки --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- Импортировать собственную библиотеку тегов --%>
<%@taglib prefix="ad" uri="http://bsu.rfe.java.teacher.tag/ad" %>
<%-- Указать, что мы ожидаем данные в кодировке UTF-8 --%>
<fmt:requestEncoding value="UTF-8" />
<%-- Обратиться к собственному тегу для аутентификации пользователя на основе
указанных им логина и пароля --%>
<ad:login login="${param.login}" password="${param.password}" />
<%-- Проверить сообщение об ошибке, чтобы узнать результат аутентификации --%>
<c:choose>
    <c:when test="${sessionScope.errorMessage==null}">
        <%-- Ошибок не возникло, переадресовать на страницу личного
        кабинета --%>
        <c:redirect url="/cabinet.jsp" />
    </c:when>
    <c:otherwise>
        <%-- Переадресовать на начальную страницу --%>
        <c:redirect url="/index.jsp" />
    </c:otherwise>
</c:choose>