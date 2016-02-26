(function () {


    $(document).ready(function () {
        //signing event on my subscription button
        var subscriptionButton= document.getElementById("subscriptionButton");
        subscriptionButton.onclick = findSubscription;
        //signing event on my followers button
        var followersButton= document.getElementById("followersButton");
        followersButton.onclick = findFollowers;
        //signing my message button
        var messagesButton= document.getElementById("messagesButton");
        messagesButton.onclick = findMessages;
        //signing news button
        var newsButton =document.getElementById("newsButton");
        newsButton.onclick = showMessageSubscriber;
        //button find users
        var findUsers = document.getElementById("findUsers");
        findUsers.onclick = findAllUsers;
        //show news on the start page
        showMessageSubscriber();
    });


    //delete operation
    //if removal from the database went well, then remove from the page
    function deleteUserSubscription(event) {
        var id = event.target.id;
    $.get('eventsaround/users/delete/subscription', {id: id}).done(function () {
        $(event.target).parent().parent().remove();
            });
    }

    //at the touch of a subscription button send request and displays the data
    function findSubscription() {
        var request = new XMLHttpRequest();
        var searchResultContainer = document.getElementById("output_element");
        request.onreadystatechange = function () {
            if(request.readyState == 4) {
                searchResultContainer.innerHTML = request.responseText;
                //signing event to remove
                $(".delete-subscription").click(deleteUserSubscription);
            }
        };
        request.open("GET",'eventsaround/users/search/subscription', false);
        request.send();
    }

    //at the touch of a followers button send request and displays the data
    function findFollowers() {
        var request = new XMLHttpRequest();
        var searchResultContainer = document.getElementById("output_element");
        request.onreadystatechange = function () {
            if(request.readyState == 4) {
                searchResultContainer.innerHTML = request.responseText;
            }
        };
        request.open("GET",'eventsaround/users/search/followers', false);
        request.send();
    }

    //at the touch of a followers button send request and displays the data
    function findMessages() {
        var request = new XMLHttpRequest();
        var searchResultContainer = document.getElementById("output_element");
        request.onreadystatechange = function () {
            if(request.readyState == 4) {
                searchResultContainer.innerHTML = request.responseText;
                //signing event to remove
                $(".delete-message").click(deleteMessage);
            }
        };
        request.open("GET",'eventsaround/messages/usermessages', false);
        request.send();
        //button send new message
        var sendMessageButton=document.getElementById("sendNewMessage");
        sendMessageButton.onclick=creteMessage;
    }

    //delete message operation
    function deleteMessage(event) {
        var id = event.target.id;
        $.get('eventsaround/messages/delete', {id: id}).done(function () {
            $(event.target).parent().parent().remove();
        });
    }
    //create message operation
    function creteMessage() {
        var theme= document.getElementById("theme");
        var text= document.getElementById("text");
        $.get('eventsaround/messages/sendmessage', {theme: theme.value,text:text.value}).done(function () {
            findMessages();
        });
    }

    //show subscriber messages
    function showMessageSubscriber(){
        var request = new XMLHttpRequest();
        var searchResultContainer = document.getElementById("output_element");
        request.onreadystatechange = function () {
            if(request.readyState == 4) {
                searchResultContainer.innerHTML = request.responseText;
            }
        };
        request.open("GET",'eventsaround/messages/messagesubscription', false);
        request.send();
    }

    //at the touch of a subscription button send request and displays the data
    function findAllUsers() {
        var request = new XMLHttpRequest();
        var searchResultContainer = document.getElementById("output_element");
        request.onreadystatechange = function () {
            if(request.readyState == 4) {
                searchResultContainer.innerHTML = request.responseText;
                //signing event to remove
                $(".аdd-subscription").click(addUserSubscription);
            }
        };
        request.open("GET",'eventsaround/users/search/all', false);
        request.send();
    }

    //subscription on user
    function addUserSubscription(event) {
        var id = event.target.id;
        $.get('eventsaround/users/add/subscription', {id: id}).done(function () {
            $(event.target).parent().parent().remove();
        });
    }
}());

