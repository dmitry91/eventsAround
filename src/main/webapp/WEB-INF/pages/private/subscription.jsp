<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h3>Мои подписки</h3>
<table id="subscriptionResultTable">
    <tr>
        <th>Имя:</th>
        <th>Фамилия:</th>
        <th>Интересы:</th>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.name}</td>
            <td>${user.surname}</td>
            <td>${user.aboutUser}</td>
            <td>
                <button id='${user.id}' class="delete-subscription">
                    удалить
                </button>
            </td>
        </tr>
    </c:forEach>
</table>

