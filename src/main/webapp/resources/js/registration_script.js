
$(document).ready(function() {

    var addButton = document.getElementById("button_add_user");
    addButton.onclick = addUser;

    var dateMin = new Date();
    var dateMax = new Date();

    var day = dateMin.getDate();
    var month = dateMin.getMonth() + 1;
    var year = dateMin.getFullYear()-120;

    if (month < 10) month = "0" + month;
    if (day < 10) day = "0" + day;
    var dayMin = year + "-" + month + "-" + day;

    day = dateMax.getDate();
    month = dateMax.getMonth() + 1;
    year = dateMax.getFullYear()-10;

    if (month < 10) month = "0" + month;
    if (day < 10) day = "0" + day;
    var dayMax = year + "-" + month + "-" + day;
    //set minimum and maximum date birthday
    $("#reg_birthday").attr("max", dayMax).attr("min",dayMin);



});

function addUser() {
    var name = document.getElementById("reg_name").value;
    var surname = document.getElementById("reg_surname").value;
    var email = document.getElementById("reg_email").value;
    var password = document.getElementById("reg_password").value;
    var aboutUser = document.getElementById("reg_about_user").value;
    var birthday = document.getElementById("reg_birthday").value;

    var xhr = new XMLHttpRequest();
    xhr.open("GET",'registration/createuser?name=' + name+'&surname='+surname+'&email='+email+'&password='+password+'&about_user='+aboutUser+'&birthday='+birthday, false);
    xhr.send();

    if (xhr.status != 200) {
        // обработать ошибку
        alert( xhr.status + ': ' + xhr.statusText ); // пример вывода: 404: Not Found
    } else {
        // вывести результат
        $('#errors').text( xhr.responseText);

    }

}

