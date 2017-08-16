$(document).ready(function() {
  
    $.ajax({
        url: "http://localhost:9000/restservice/v1/api/users",
        method: 'GET', 
        type: 'GET',   	
    	contentType: 'application/json'
    	
    }).then(function(data) {
       $('.greeting-id').append(data.id);
       $.each(data, function(index) {
            $('.greeting-username').append("<br/>"+data[index].username);
        });
       //window.location.href="http://localhost:9000/UsersList.html";
    });
});