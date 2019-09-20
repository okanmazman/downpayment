var urlHead="http://localhost:8588/rest";
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
   setInterval("getUserNotifications()", 60000);
   
   
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
 	        		$("#userProducts").append("<option value="+parsed[i].id+" data-req-val="+parsed[i].requestedValue+">"+  parsed[i].name +"  Deposit Value= "+parsed[i].requestedValue+"</option>")	
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
   
function beginTimer(expirationDate,infodiv)
{
	
	// Update the count down every 1 second
	var x = setInterval(function() {		
		
		var countDownDate = new Date(expirationDate).getTime();
	  // Get today's date and time
	  var now = new Date().getTime();
	    
	  // Find the distance between now and the count down date
	  var distance = countDownDate - now;
	    
	  // Time calculations for days, hours, minutes and seconds
	  var days = Math.floor(distance / (1000 * 60 * 60 * 24));
	  var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
	  var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
	  var seconds = Math.floor((distance % (1000 * 60)) / 1000);
	    
	  // Output the result in an element with id="demo"
	  if(document.getElementById(infodiv)!=null&&document.getElementById(infodiv)!=undefined)
	  {
		  document.getElementById(infodiv).innerHTML = days + "d " + hours + "h "
		  + minutes + "m " + seconds + "s ";	  
	  }
	
	    
	  // If the count down is over, write some text 
	  if (distance < 0) {
	    clearInterval(x);
	    document.getElementById(infodiv).innerHTML = "EXPIRED";
	  }
	}, 1000);
	
		
}


function rejectDeposit(requestId){
	$.ajax({
        type: "GET",
        url:  urlHead+"/rejectDepositRequest?requestId="+requestId,
        dataType: 'json',
        async: true,
        success: function(result) {
        if(result)
        	getUserDepositRequests();
        }
        ,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR.status + ' ' + jqXHR.responseText);
        }})
}

function getUserDepositRequests ()
{
	$.ajax({
        type: "GET",
        url:  urlHead+"/userDepositRequests?username="+userName,
        dataType: 'json',
        async: true,
        success: function(result) {
        stringJsonResult=JSON.stringify(result);
        parsedResult=JSON.parse(stringJsonResult);
        var lines = parsedResult;
        //console.log(lines);
       
        $("#depositRequestsTable tr:gt(0)").remove();
        for (var i = 0; i < lines.length; i++) 
        {
        	
        	 var tableRowHtml="<tr data-status='"+lines[i].status.statusName+"'><td>"+lines[i].relatedDeposit.id
   		  +"</td><td>"+lines[i].relatedDeposit.amount
		  +"</td><td>"+lines[i].expirationDate
		  +"</td><td id='test_"+i+"' data-exp-date='"+lines[i].expirationDate+"'>timer koyulacak"
		  +"</td><td>"+lines[i].requestDate
		  +"</td><td>"+lines[i].relatedDeposit.sentToUserName
		  +"</td><td>"+lines[i].status.statusName
		  +"</td><td>"+lines[i].expired;
		  
		  if(lines[i].status.statusName=="Pending"){
			  tableRowHtml+="</td><td onclick='acceptDeposit("+lines[i].id+");return false;'><button type='submit'  class='btn btn-outline-success'>Accept</button>" 
					  +"</td><td onclick='rejectDeposit("+lines[i].id+");return false;'><button type='submit'  class='btn btn-outline-success'>Reject</button>"
					  +"</td></tr>";
		  }
		  else
			  {
			  tableRowHtml+="<td><i class='fas fa-info-circle 3x'></i></td></tr>";
			  }
          
		  
          $("#depositRequestsTable").append(tableRowHtml);	    
          
          
        }
        
        console.log(result);
        
        var pendingReqs=[];
        for (var i = 0; i < lines.length; i++) 
        {
        	if(lines[i].status.statusName=='Pending')
        		pendingReqs.push(lines[i]);
        }
         
        $(".dot").text(pendingReqs.length);
        $.each($("#depositRequestsTable tr"),function(key,value)
      		  {
      		  //console.log($("#depositRequestsTable tr:gt('"+key+"')").find("td[data-exp-date]").data("exp-date"))
      		  beginTimer($("#depositRequestsTable tr:gt('"+key+"')").find("td[data-exp-date]").data("exp-date"),
      		  "test_"+key)

      		  });	        	       
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR.status + ' ' + jqXHR.responseText);
        }})	       
            	        	
   
	
}
var  acceptDeposit=function(requestId){
	
	$.ajax({
        type: "GET",
        url:  urlHead+"/acceptDepositRequest?requestId="+requestId,
        dataType: 'json',
        async: true,
        success: function(result) {
        if(result)
        	getUserDepositRequests();
        
        Swal.fire({
			  type: 'success',
			  title: 'Transaction completed!',
			  text: 'The deposit transaction completed successfully!'  			  
			})	
        }
        ,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR.status + ' ' + jqXHR.responseText);
        }})
}
getUserDepositRequests();
setInterval("getUserDepositRequests()", 80000);







   