$(document).ready(function() {
    alert("logout.js");
    $.ajax({
        url: "http://localhost:9000/restservice/v1/api/user/logout",
        method: 'POST', 
        type: 'POST',   	
    	contentType: 'application/json'
    	
    }).then(function(data) {
    	alert(JSON.stringify(data));
       $('.greeting-id').append(data.id);
       $.each(data, function(index) {
            //alert(data[index].username+"*****"+data[index].password);
            $('.greeting-username').append("<br/>"+data[index].username);
        });
       window.location.href="http://localhost:9000/login.html";
    });
});