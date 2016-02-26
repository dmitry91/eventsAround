<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/html">
<head lang="en">
    <meta charset="UTF-8">
    <title>Index</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/index_style.css"/>">
    <script src="<c:url value="/resources/js/jquery-1.10.2.js"/>"></script>
    <script src="<c:url value="/resources/js/index_script.js"/>"></script>

    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
            font-size: 18px;
        }
    </style>
</head>
<body>
<!--main element-->
<div class="main">
    <!--page header-->
    <div class="menu_header">
        <div class="left"><img class="events_around" src="<c:url value="/resources/image/events_around.jpg"/>"></div>
        <div class="settings"><a href="<c:url value="/eventsaround/users/settings"/>"><img class="wheel" src="<c:url value="/resources/image/settings_wheel.png"/>" alt="" /></a></div>
        <div class="right"><a class="exit" href="<c:url value="/j_spring_security_logout" />" >Выход</a></div>
    </div>
    <!--the main elements of the page-->
    <div class="page_layout">
        <!--menu on left-->
        <div class="side_bar">
            <ol>
                <li><a id="subscriptionButton" class="button">Мои подписки</a></li>
                <li><a id="followersButton" class="button">Мои подписчики</a></li>
                <li><a id="messagesButton" class="button">Мои сообщения</a></li>
                <li><a id="newsButton" class="button">Новости</a></li>
                <li><a id="findUsers" class="button">Поиск людей</a></li>
            </ol>
        </div>
        <!--user avatar-->
        <div class="user_photo">
            <img class="user_avatar"  src="<c:url value="/eventsaround/users/getavatar"/>" alt="no photo" />
        </div>
        <!--information about user-->
        <div class="user_info">
            <div class="user_name_surname">${user_name_sername}</div>
            <div style="margin-top: 10px">${user_birthday}</div>
            <div class="about_user">${user_about}</div>
        </div>
        <!--element for out information-->
        <div id="output_element" class="output_element">

        </div>
    </div>
    <!--Footer applications-->
    <div class="footer">
        Events Around © 2016<br>
        create by <a href="#" style="text-decoration: none;">Dmitry Gubarenko</a>
    </div>
</div>

</body>
</html>