<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<table id="subscriberMessageResultTable">
    <h2 style="margin-left: 250px">Новости</h2>
    <tr>
        <th>Отправитель:</th>
        <th>Тема:</th>
        <th>Сообщение:</th>
        <th>Дата:</th>
    </tr>
    <c:forEach items="${messageSubscriber}" var="message">
        <tr>
            <td>${message.user.name} ${message.user.surname}</td>
            <td>${message.theme}</td>
            <td>${message.text}</td>
            <td><fmt:formatDate type="both" value="${message.sendDate}" /></td>
        </tr>
    </c:forEach>
</table>