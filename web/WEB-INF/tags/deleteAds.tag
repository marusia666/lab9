<%@ tag pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Кнопка удаления показывается только если:
1) пользователь аутентифицирован (authUser!=null);
2) передано текущее объявление (ad!=null);
3) id автора объявленния и id аутентифицированного пользователясовпадают --%>

<c:if test="${sessionScope.authUser!=null}">
    <div style="float: right; margin: 10px; margin-top: 20px; padding: 5px 0px; border: 1px solid black; background-color: #ccc; width: 150px; text-align: center">
        <a href="<c:url value="/doDeleteAllAd.jsp"/>">Удалить всё</a>
    </div>
</c:if>