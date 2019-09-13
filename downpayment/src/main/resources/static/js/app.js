function getUserNotifications () {
	var userName=$("#globalUserName").text();
	if(userName!=null&&userName!=undefined&&userName!="")
	{		
		$.ajax({
	        type: "GET",
	        url:  "http://localhost:8586/rest/userNotifications?username="+userName,
	        dataType: 'json',
	        async: true,
	        success: function(result) {
	        stringJsonResult=JSON.stringify(result);
	        parsedResult=JSON.parse(stringJsonResult);
	        var lines = parsedResult;
	        console.log(lines);
	        
	        $("#d#notificationsTable tr:gt(1)").remove();
	        for (var i = 0; i < lines.length; i++) {
	          $("#notificationsTable").append("<tr><td>"+lines[i].id+"</td><td>"+lines[i].notificationText+"</td><td>"+lines[i].read
	        		  +"</td><td>"+lines[i].notificationDate+"</td><td><button type='button' th:href=@{/rest/changeNotificationStatusReadTrue?id="+lines[i].id+"} class='btn btn-primary'>Mark as read</button></td></tr>")
	        }
	        
	        console.log(result);
	        $("#notificationCountBadge").text(lines.length);
	        $("#notificationCountBadge").val(lines.length);
	        
	        },
	        error: function(jqXHR, textStatus, errorThrown) {
	            console.log(jqXHR.status + ' ' + jqXHR.responseText);
	        }})
	   };	
	}
	
        
   setInterval(getUserNotifications(), 10000);
