<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/html">
<head lang="en">
    <meta charset="UTF-8">
    <title>registration</title>
    <script src="<c:url value="/resources/js/jquery-1.10.2.js"/>"></script>
    <script src="<c:url value="/resources/js/registration_script.js"/>"></script>
    <link href="<c:url value="/resources/css/form.css" />" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/settings_style.css"/>">
</head>
<body >
	<div class="positionDiv">
        <div class="message" id="errors"></div>
        <form>
            <strong class="main_text">Заполните форму регистрации<br></strong><br>
            <p><strong class="text_field">Имя</strong><br>
                <input name="name" id="reg_name" placeholder="Введите ваше имя" required pattern="[A-ZА-Яa-zа-я]{2,20}" />
            </p>
            <p><strong class="text_field">Фамилия</strong><br>
                <input name="surname" id="reg_surname" placeholder="Введите вашу фамилию" required pattern="[A-ZА-Яa-zа-я]{2,20}"/><br>
            </p>
            <p><strong class="text_field">Адрес электронной почты (логин пользователя)</strong><br>
                <input type="email" name="email" id="reg_email" placeholder="Введите ваш Email" required pattern="^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$" />
            </p>
            <p><strong class="text_field">Пароль</strong><br>
                <input name="password" id="reg_password" placeholder="Введите ваш пароль" required pattern="((?=.*\d)(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{6,20})" />
            </p>
            <p> <p style="width: 450px"> <strong class="text_field">Введите данные о интересах пользователя(темы будущих постов),
                данные могут состоят из латинницы-кириллицы, знаков препинания и цифр, максимум 80 символов</strong><br></p>
                <label>
                    <textarea style="resize: none;" maxlength="80" name="about_user" id="reg_about_user" placeholder="Введите данные о интересах пользователя" cols="40" rows="4" required></textarea>
                </label>
            <p><strong class="text_field">Дата рождения. Возраст пользователя: мин-10 лет, макс 120 лет</strong><br>
                <label>
                    <input type="date" name="birthday" id="reg_birthday" min="" max="" required/>
                </label><br>
            </p>
            <div>
                <input id="button_add_user" class="submit" type="button" value="отправить" >
            </div>
            <!--button go tu main page-->
            <div class="toMainPage">
                <a href="<c:url value="/login"/>" class="button_to_main">Перейти на страницу входа</a>
            </div>
        </form>
	</div>
</body>
</html>
