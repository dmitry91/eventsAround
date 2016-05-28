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
    <script src="<c:url value="/resources/bootstrap3/js/bootstrap.js"/>"></script>
    <!-- Bootstrap -->
    <link href="<c:url value="/resources/bootstrap3/css/bootstrap.css"/>" type="text/css" rel="stylesheet">
    <link href="<c:url value="/resources/css/bootstrap_style.css"/>" type="text/css" rel="stylesheet">

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
            <ul class="nav navbar-nav visible-xs ">
                <li><a id="subscriptionButton-dropdown" class="button-responsive">Мои подписки</a></li>
                <li><a id="followersButton-dropdown" class="button-responsive">Мои подписчики</a></li>
                <li><a id="messagesButton-dropdown" class="button-responsive">Мои сообщения</a></li>
                <li><a id="newsButton-dropdown" class="button-responsive">Новости</a></li>
                <li><a id="findUsers-dropdown" class="button-responsive">Поиск людей</a></li>
            </ul>
            <div class="navbar-right">
                <ul class="nav navbar-nav">
                    <li style="margin-right: 10px">
                        <form action="<c:url value="/eventsaround/users/settings"/>">
                            <button type="submit" class="btn btn-default btn-lg" >
                                <i class="glyphicon glyphicon-cog" ></i>
                            </button>
                        </form>
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
    <div class="main">
            <!--the main elements of the page-->
            <div class="page_layout">
                <!--menu on left-->
                <div class="row">
                    <div class="side_bar col-lg-3 col-md-3 col-sm-3 hidden-xs">
                        <ol>
                            <li><a id="subscriptionButton" class="button">Мои подписки</a></li>
                            <li><a id="followersButton" class="button">Мои подписчики</a></li>
                            <li><a id="messagesButton" class="button">Мои сообщения</a></li>
                            <li><a id="newsButton" class="button">Новости</a></li>
                            <li><a id="findUsers" class="button">Поиск людей</a></li>
                        </ol>
                    </div>
                    <!--user avatar-->
                    <div class="user_photo col-lg-3 col-md-3 col-sm-3 col-xs-4">
                        <img class="user_avatar"  src="<c:url value="/eventsaround/users/getavatar"/>" alt="no photo" />
                    </div>
                    <!--information about user-->
                    <div class="user_info col-lg-6 col-md-6 col-sm-6 col-xs-8">
                            <div class="user_name_surname">${user_name_sername}</div>
                            <div class="user_birthday">${user_birthday}</div>
                            <div class="about_user">${user_about}</div>
                    </div>
                </div>
                <!--element for out information-->
                <div id="output_element" class="output_element"></div>
            </div>

        <!--Footer applications-->
        <div class="footer">
            Events Around © 2016<br>
            create by <a href="#" style="text-decoration: none;">Dmitry Gubarenko</a>
        </div>
    </div>
</body>
</html>