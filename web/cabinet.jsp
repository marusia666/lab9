<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- Импортировать JSTL-библиотеку --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Импортировать собственную библиотеку теговых файлов --%>
<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%-- Импортировать собственную библиотеку тегов --%>
<%@taglib prefix="ad" uri="http://bsu.rfe.java.teacher.tag/ad" %>
<%-- Если пользователь не аутентифицирован, то просмотр страницы невозможен --%>
<c:if test="${sessionScope.authUser==null}">
    <c:redirect url="/" />
</c:if>
<!DOCTYPE html>
<head>
    <title>Личный кабинет</title>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF8'/>
    <meta http-equiv="Expires" content="Mon, 11 May 1998 0:00:00 GMT">
    <meta http-equiv="Last-Modified" content="Fri, Jan 25 2099 23:59:59 GMT">
</head>
<body>
<%-- Подключить заголовок страницы --%>
<jsp:include page="/static/header.jsp"></jsp:include>
<%-- Вставить кнопку создания нового объявления --%>
<my:deleteAds />
<my:newButton />
<h1>Личный кабинет</h1>
<%-- Вставить разметку 1-колоночной страницы --%>
<my:layout1Column>
    <%-- Извлечь список объявлений пользователя --%>
    <ad:getAds range="my" var="adListing" sort="${sessionScope.sort}" dir="${sessionScope.dir}" />
    <%-- Показать объявления с возможностью редактирования --%>
    <my:adListing adListing="${adListing}" editMode="true" />
</my:layout1Column>
<%-- Вставить нижний заголовок страницы --%>
<%@ include file="/static/footer.jsp" %>
</body>
</html>