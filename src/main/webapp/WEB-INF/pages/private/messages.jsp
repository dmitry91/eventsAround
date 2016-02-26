<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<h3>Новое сообщение</h3>
<form>
  <input name="theme" id="theme" placeholder="введите тему" maxlength="80" required /><br>
  <label>
    <textarea style="resize: none; margin-top: 5px" maxlength="140" name="text" id="text" placeholder="Введите сообщение" cols="40" rows="3" required></textarea>
  </label>
  <div class="button_save" >
    <input id="sendNewMessage" type="button" value="отправить" >
  </div>
</form>
<table id="userMessageResultTable">
  <h3>Мои сообщения</h3>
  <tr>
    <th>Тема:</th>
    <th>Сообщение:</th>
    <th>Дата:</th>
  </tr>
  <c:forEach items="${userMessages}" var="message">
    <tr>
      <td>${message.theme}</td>
      <td>${message.text}</td>
      <td><fmt:formatDate type="both" value="${message.sendDate}" /></td>
      <td>
        <button id='${message.id}' class="delete-message">
          удалить
        </button>
      </td>
    </tr>
  </c:forEach>
</table>
