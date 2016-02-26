<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h3>Все пользователи на которых вы не подписанны</h3>
<table id="findUsersResultTable">
    <tr>
        <th>Имя:</th>
        <th>Фамилия:</th>
        <th>Интересы:</th>
    </tr>
    <c:forEach items="${allUsers}" var="user">
        <tr>
            <td>${user.name}</td>
            <td>${user.surname}</td>
            <td>${user.aboutUser}</td>
            <td>
                <button id='${user.id}' class="аdd-subscription">
                    подписаться
                </button>
            </td>
        </tr>
    </c:forEach>
</table>