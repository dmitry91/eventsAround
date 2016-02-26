<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/html">
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <script src="<c:url value="/resources/js/jquery-1.10.2.js"/>"></script>
    <script src="<c:url value="/resources/js/settings_script.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index_style.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/settings_style.css"/>">
</head>
<body>
    <!--main element-->
    <div class="main">
        <!--page header-->
        <div class="menu_header">
            <div class="left"><img class="events_around" src="<c:url value="/resources/image/events_around.jpg"/>"></div>
            <div class="settings"><a href="#"><img class="wheel" src="<c:url value="/resources/image/settings_wheel.png"/>" alt="" /></a></div>
            <div class="right"><a class="exit" href="<c:url value="/j_spring_security_logout" />">Выход</a></div>
        </div>
        <div class="message" id="message_settings"></div>
        <!--the main elements of the page-->
        <div class="page_settings_layout">
            <div class="form">
                <div>
                    Ваш пароль должен содержать символы верхнего и нижнего регистров, а так же цифры.
                    <form id="passport_update">
                        <p class="text_field"><strong >Старый пароль*</strong></p>
                        <input type="text" name="old_password" id="old_password" placeholder="Введите старый парль" required pattern="((?=.*\d)(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{6,20})" />
                        <p class="text_field"><strong >Новый пароль*</strong></p>
                        <input type="text" name="new_password" id="new_password" placeholder="Введите старый парль" required pattern="(?=.*\d)(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{6,20}"/>
                        <p class="text_field" ><strong >Повторите новый пароль*</strong></p>
                        <input type="text" name="repeat_new_password" id="repeat_new_password" placeholder="Повторите новый пароль" required pattern="(?=.*\d)(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{6,20}"/><br>
                        <div class="button_save" >
                            <input type="submit" value="сохранить" >
                        </div>
                    </form>
                </div>
            </div>
            <!--for new name-->
            <div class="form">
                Введите новое имя пользователя, длина имени от 2 до 20 символов.
                <form id="name_update">
                 <p class="text_field"><strong>Новое имя пользователя</strong></p>
                    <input name="new_user_name" id="new_user_name" placeholder="Введите новое имя" required pattern="[A-ZА-Яa-zа-я]{2,20}" />
                    <div class="button_save" >
                        <input type="submit" value="сохранить" >
                    </div>
                </form>
            </div>
            <!--set new surname-->
            <div class="form">
                Введите новую фамилию пользователя, длина от 2 до 20 символов.
                <form id="surname_update">
                    <p class="text_field"><strong>Новая фамилия пользователя</strong></p>
                    <input name="new_user_surname" id="new_user_surname" placeholder="Введите новую фамилию" required pattern="[A-ZА-Яa-zа-я]{2,20}" />
                    <div class="button_save" >
                        <input type="submit" value="сохранить" >
                    </div>
                </form>
            </div>
            <!--set new photo-->
            <div  class="form">
                <form method="POST" action="<c:url value="/eventsaround/users/uploadavatar"/>" enctype="multipart/form-data">
                    <p>Загрузите вашу фотографию. максимальный размер 16MB</p>
                    <input type="file" name="photo" id="photo" accept="image/jpeg,image/png" value="Upload">
                      <div style="margin-top: 5px">  <input type="submit" value="Сохранить"></div>
                </form>
            </div>
            <!--set new email-->
            <div class="form">
                Введите новый email
                <form id="email_update">
                    <p class="text_field"><strong>Новый email пользователя</strong></p>
                    <input name="new_user_email" id="new_user_email" placeholder="Введите новый email" required pattern="^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$" />
                    <div class="button_save" >
                        <input  type="submit" value="сохранить" >
                    </div>
                </form>
            </div>
            <!--set new data about user-->
            <div class="form" id="form_id">
                <p style="width: 550px"> Введите новые данные о деятельности пользователя, данные могут состоят из латинницы-кириллицы, знаков препинания и цифр, максимум 80 символов</p>
                <form id="about_update">
                    <label>
                        <textarea style="resize: none;" maxlength="80" id="new_about_user" placeholder="Новые интересы" cols="40" rows="3" required></textarea>
                    </label>
                    <div class="button_save" >
                        <input type="submit" value="сохранить" >
                    </div>
                </form>
            </div>
            <!--button go tu main page-->
            <div class="toMainPage">
            <a href="<c:url value="/index"/>" class="button_to_main">Перейти на главную страницу</a>
            </div>
        </div>
    </div>
</body>
</html>