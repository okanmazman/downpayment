var urlHead="http://localhost:8586/rest";
var userName=$("#globalUserName").text();
function getUserNotifications () {
	 
	
	if(userName!=null&&userName!=undefined&&userName!="")
	{		
		$.ajax({
	        type: "GET",
	        url:  urlHead+"/userNotifications?username="+userName,
	        dataType: 'json',
	        async: true,
	        success: function(result) {
	        stringJsonResult=JSON.stringify(result);
	        parsedResult=JSON.parse(stringJsonResult);
	        var lines = parsedResult;
	        //console.log(lines);
	        
	        $("#notificationsTable tr:gt(0)").remove();
	        for (var i = 0; i < lines.length; i++) 
	        {
	          $("#notificationsTable").append("<tr><td>"+lines[i].id
	        		  +"</td><td>"+lines[i].notificationText+"</td><td data-isread="+lines[i].read+">"+lines[i].read
	        		  +"</td><td>"+lines[i].notificationDate
	        		  +"</td><td onclick='changeStatus("+lines[i].id+");return false;'><button type='submit'  class='btn btn-outline-success'>Change Status</button>" +
	        		  "</td></tr>")	    
	        
	          
	        }
	        
	        console.log(result);
	        
	        var unreads=[];
	        for (var i = 0; i < lines.length; i++) 
	        {
	        	if(!lines[i].read)
	        	unreads.push(lines[i]);
	        }
	        $("#notificationCountBadge").text(unreads.length);
	        $(".dot").text(unreads.length);
	        
	        $.each($("#notificationsTable td[data-isread]"),function(key,val){
	        	if($(this).data("isread"))
	        	$("#notificationsTable tr").eq(key+1).find("td").eq(2).html("<i class='far fa-check-circle fa-2x'></i>")
	        	else if(!$(this).data("isread"))
	        	$("#notificationsTable tr").eq(key+1).find("td").eq(2).html("<i class='far fa-times-circle fa-2x'></i>")
	        	})	        	        	       
	        },
	        error: function(jqXHR, textStatus, errorThrown) {
	            console.log(jqXHR.status + ' ' + jqXHR.responseText);
	        }})	        
	            	        	
	   };	
	   
	}
 
        
	var changeStatus=function(id){
		$.ajax({
	        type: "GET",
	        url:  urlHead+"/changeNotificationStatusRead?id="+id,
	        dataType: 'json',
	        async: true,
	        success: function(result) {
	        if(result)
	        	getUserNotifications();  
	        if( $("#notificationCountBadge").text=="0")
	       	{
	       	$(this).css("color","lime");
	       	}
	        },
	        error: function(jqXHR, textStatus, errorThrown) {
	            console.log(jqXHR.status + ' ' + jqXHR.responseText);
	        }})
		
	}
   getUserNotifications();
   setInterval("getUserNotifications()", 80000);
   
   
   var checkIfAnyNewNotification=function(){};
   
   
   $("#purl").on("paste",function(e)
		   {
		   	$("#productImage").attr("src","");
		   	$("#productDescription").val("");
		   	var element = this;
		     setTimeout(function () {
		       var text = $(element).val();
		   		
		   		$("#spinner").show();
		   			$.ajax
		   			({
		   				url: "https://api.linkpreview.net?key=5d73a69f012735053f99d12400ffb9eebe6992bf56e82&q="+text,
		   				type: "GET",
		   				contentType: "application/json",
		   				success: function(result){
		   					var parsed=jQuery.parseJSON(JSON.stringify(result));
		   					$("#productDescription").html(parsed.description); 
		   					$("#productTitle").html(parsed.title); 					
		   					$("#productImage").attr("src",parsed.image) 
		   					
		   					$("#spinner").hide();
		   				}
		   			});
		   		
		     }, 10);
		   })
     
     function searchProductsByUser()     
	 {
	   $("#spinnerProductSearch").show();
	   $("#productsLoaded").hide();
	   setTimeout(function () {		  
	   $.ajax({
 	        type: "GET",
 	        url:  urlHead+"/getProductsByUser?username="+$("#sentToUserName").val(),
 	        dataType: 'json',
 	        async: true,
 	        success: function(result) {
 	        	console.log(result);
 	        	var parsed=jQuery.parseJSON(JSON.stringify(result));
 	        	$("#userProducts option:gt(0)").remove();
 	        	for (var i = 0; i < parsed.length; i++) 
 		        { 	        		
 	        		$("#userProducts").append("<option value="+parsed[i].id+" data-req-val="+parsed[i].requestedValue+">"+  parsed[i].name +"*Deposit Value=*"+parsed[i].requestedValue+"</option>")	
 	        	}
 	        	 
 	        	$("#spinnerProductSearch").hide();
 	        	$("#productsLoaded").show();
 	        },
 	        error: function(jqXHR, textStatus, errorThrown) {
 	            console.log(jqXHR.status + ' ' + jqXHR.responseText);
 	        }})
 	        },1000) 
 	      
	 }
   $("#userProducts").change(function(){
	   var reqval=$("#userProducts option:selected").data("req-val");
	   console.log(reqval);	   
	   $("#amount").val(reqval);
	   
   })
   
   /*function getAllusers(){
	   $.ajax({
	        type: "GET",
	        url:  urlHead+"/getAllUsers",
	        dataType: 'json',
	        async: true,
	        success: function(result) {
	        	console.log(result);
	        	var parsed=jQuery.parseJSON(JSON.stringify(result));
	        	return parsed;
	        },
	        error: function(jqXHR, textStatus, errorThrown) {
	            console.log(jqXHR.status + ' ' + jqXHR.responseText);
	        }})
   }*/
  

 
var options={
		url: urlHead+"/getAllUsers",

		getValue: "username",
		template: {
	        type: "description",
	        fields: {
	            description: "email"
	        }
	    },

	    list: {
	        match: {
	            enabled: true
	        }
	    },

	    theme: "plate-dark"
}
$("#sentToUserName").easyAutocomplete(options);
   