<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/html">
<head lang="en">
    <meta charset="UTF-8">
    <script src="<c:url value="/resources/js/jquery-1.10.2.js"/>"></script>
    <title>Input</title>
    <link href="<c:url value="/resources/css/form.css" />" rel="stylesheet">
<body>
  <div class="input_form" >
      <form name='f' action="<c:url value='/j_spring_security_check' />" method='POST'>
            <!--logo application-->
          <div class="input_logo"> <strong class="logo_text">Events Around</strong></div><br>
            <!--user login-->
          <p><strong class="text_field">Логин <em>*</em></strong><br>
            <input type="email" name="login" placeholder="Введите логин (email)" required pattern="^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$" /><br>
          </p>
            <!--user password-->
          <p><strong class="text_field">Пароль<em>*</em></strong><br>
            <input id="pass" type="password" name="password" placeholder="Введите пароль" required pattern="((?=.*\d)(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{6,20})"/>
              <!--flag show password-->
              <label class="text_field">
                  <input onchange="if ($('#pass').get(0).type=='password') $('#pass').get(0).type='text'; else $('#pass').get(0).type='password';"
                         name="fff" type="checkbox" value="false">Показать
              </label>
          </p>
          <div>
            <input class="submit" type="submit" value="Войти"  >
      </div>
    </form>
      <%--button registration--%>
      <form style="margin-top: 5px" action="${pageContext.request.contextPath}/registration">
          <input class="submit" type="submit" value="Регистрация">
      </form>
        <%--show error message--%>
      <c:if test="${not empty error}">
          <div  class="alert_danger" role="alert">
                  ${error}</div>
      </c:if>
  </div>
</body>
</html>
