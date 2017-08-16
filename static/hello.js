$(document).ready(function() {
 //var sendInfo = { username: "john7", password: "smith7" };
  var sendInfo = { username: $("#username").val(), password: $("#password").val() };
  alert("hello.js of "+sendInfo);
    $.ajax({
        url: "http://localhost:9000/restservice/v1/api/user/login",
        method: 'POST', 
        type: 'POST',   	
    	contentType: 'application/json',
    	data: JSON.stringify(sendInfo),	
    }).then(function(data) {
       $('.greeting-id').append(data.id);
       window.location.href="http://localhost:9000/home.html";
      
    });
});