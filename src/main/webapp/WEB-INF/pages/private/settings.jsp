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
    <script src="<c:url value="/resources/bootstrap3/js/bootstrap.js"/>"></script>
    <!-- Bootstrap -->
    <link href="<c:url value="/resources/bootstrap3/css/bootstrap.css"/>" type="text/css" rel="stylesheet">
    <link href="<c:url value="/resources/css/bootstrap_style.css"/>" type="text/css" rel="stylesheet">
</head>
<body>
<%--header--%>
    <div class="container navbar navbar-inverse ">
        <div class="navbar-header">
            <a class="navbar-brand" href="<c:url value="/index"/>" >Events Around</a>
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#responsive-menu">
                <span class="sr-only">Открыть навигацию</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div class="collapse navbar-collapse " id="responsive-menu">
            <div class="navbar-right">
                <ul class="nav navbar-nav">
                    <li style="margin-right: 10px">
                        <%--back to the main page--%>
                            <button type="submit" class="btn btn-default btn-lg" onclick="history.back();"  >
                                <i class="glyphicon glyphicon-arrow-left" ></i> назад
                            </button>
                    </li>
                    <li>
                        <form action="<c:url value="/j_spring_security_logout"/>">
                            <button type="submit" class="btn btn-default btn-lg">
                                <i class="glyphicon glyphicon-log-out"></i> Выход
                            </button>
                        </form>
                    </li>
                </ul>
            </div>

        </div>
    </div>
    <!--main element-->
<div class="container">
    <div class="row">
        <div class="col-lg-2 col-md-1 hidden-sm hidden-xs indent"></div>
         <div class="col-lg-8 col-md-10 col-sm-12 col-xs-12" >
            <div class="message" id="message_settings"></div>
            <!--the main elements of the page-->
            <div class="page_settings_layout">
                <div class="form">
                    <div>
                    <p>Ваш пароль должен содержать символы верхнего и нижнего регистров, а так же цифры.</p>
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
                <p>Введите новое имя пользователя, длина имени от 2 до 20 символов.</p>
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
                <p>Введите новую фамилию пользователя, длина от 2 до 20 символов.</p>
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
                <p>Введите новый email</p>
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
            <a href="<c:url value="/index"/>" type="button" class="btn btn-default">Перейти на главную страницу</a>
            </div>
        </div>
    </div>
    <div class="col-lg-2 col-md-1 hidden-sm hidden-xs indent" ></div>
</div>
</div>
</body>
</html>