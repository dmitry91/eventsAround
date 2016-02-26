(function () {
    jQuery(document).ready(function($) {
        $("#passport_update").submit(function(event) {
            /* off standard shipping forms */
            event.preventDefault();
            setPasswordAjax();
        });
        $("#name_update").submit(function(event) {
          /* off standard shipping forms */
          event.preventDefault();
          setNameAjax();
        });
        $("#surname_update").submit(function(event) {
            /* off standard shipping forms */
            event.preventDefault();
            setSurnameAjax();
        });
        $("#email_update").submit(function(event) {
            /* off standard shipping forms */
            event.preventDefault();
            setEmailAjax();
        });
        $("#about_update").submit(function(event) {
            /* off standard shipping forms */
            event.preventDefault();
            setAboutAjax();
        });

});


//update user password
function setPasswordAjax() {
    //get value from form
    var oldPassword = $("#old_password").val();
    var newPassword = $("#new_password").val();
    var repeatNewPassword = $("#repeat_new_password").val();
    //send request
    $.ajax({
        type: "POST",
        url: '/eventsaround/users/update/password',
        data: {oldPassword:oldPassword, newPassword:newPassword, repeatNewPassword:repeatNewPassword},
        dataType: 'json',
        async: true,
        statusCode: {
            200: function (response) {
                //show message response
                $('#message_settings').text( response.responseText);
                }
            }
        }
    );
}
    //update user name
    function setNameAjax() {
        //get value from form
        var newName = $("#new_user_name").val();
        //send request
        $.ajax({
                type: "GET",
                url: '/eventsaround/users/update/name',
                data: {newName:newName},
                dataType: 'json',
                async: true,
                statusCode: {
                    200: function (response) {
                        //show message response
                        $('#message_settings').text( response.responseText);
                    }
                }
            }
        );
    }

    //update user surname
    function setSurnameAjax() {
        //get value from form
        var newSurname = $("#new_user_surname").val();
        //send request
        $.ajax({
                type: "GET",
                url: '/eventsaround/users/update/surname',
                data: {newSurname:newSurname},
                dataType: 'json',
                async: true,
                statusCode: {
                    200: function (response) {
                        //show message response
                        $('#message_settings').text( response.responseText);
                    }
                }
            }
        );
    }

    //update user email (login)
    function setEmailAjax() {
        //get value from form
        var newEmail = $("#new_user_email").val();
        //send request
        $.ajax({
                type: "GET",
                url: '/eventsaround/users/update/email',
                data: {newEmail:newEmail},
                dataType: 'json',
                async: true,
                statusCode: {
                    200: function (response) {
                        //show message response
                        $('#message_settings').text( response.responseText);
                    }
                }
            }
        );
    }

    //update data about user
    function setAboutAjax() {
        //get value from form
        var newAbout = $("#new_about_user").val();
        //send request
        $.ajax({
                type: "GET",
                url: '/eventsaround/users/update/about',
                data: {newAbout:newAbout},
                dataType: 'json',
                async: true,
                statusCode: {
                    200: function (response) {
                        //show message response
                        $('#message_settings').text( response.responseText);
                    }
                }
            }
        );
    }

}());