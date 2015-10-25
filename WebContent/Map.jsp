<!-- 
<!DOCTYPE html>
<html>

  <head>
    <style type="text/css">
      html, body { height: 100%; margin: 0; padding: 0; }
      #map { height: 100%; }
    </style>
  </head>
  <body onload="initMap()">
    <div id="map"></div> 
	<div>
		<input type="text" id="messageinput"/>
	</div>
	<div>
	    <button type="button" onclick="openSocket();" >Open</button>
		<button type="button" onclick="send();" >Send</button>
		<button type="button" onclick="closeSocket();" >Close</button>
	</div>
	Server responses get written here
	<div id="messages"></div>
    <script type="text/javascript">
 	<script>

     var map;
     
    function initMap() {
    	  var myLatLng = {lat: -25.363, lng: 131.044};
    	  var myLatLng2 = {lat: -24.0167, lng: -53.4667};

    	  // Create a map object and specify the DOM element for display.
    	  map = new google.maps.Map(document.getElementById('map'), {
    	    center: myLatLng,
    	    scrollwheel: false,
    	    zoom: 3
    	  });

    	  // Create a marker and set its position.
    	  var marker = new google.maps.Marker({
    	    map: map,
    	    position: myLatLng,
    	    title: 'Hello World!'
    	  });
    	  
    
 
/*
var myLatlng = new google.maps.LatLng(-25.363882,131.044922);
var mapOptions = {
  zoom: 4,
  center: myLatlng
}
var map = new google.maps.Map(document.getElementById("map"), mapOptions);

var marker = new google.maps.Marker({
    position: myLatlng,
    title:"Hello World!"
});
*/

// To add the marker to the map, call setMap();
//marker.setMap(map);

// another websocket example
 var webSocket;
 var messages = document.getElementById("messages");

 function openSocket(){
     // Ensures only one connection is open at a time
     if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
        writeResponse("WebSocket is already opened.");
         return;
     }
     // Create a new instance of the websocket
     webSocket = new WebSocket("ws://localhost:8080/TwitterMap/echo");
      
     /**
      * Binds functions to the listeners for the websocket.
      */
     webSocket.onopen = function(event){
         // For reasons I can't determine, onopen gets called twice
         // and the first time event.data is undefined.
         // Leave a comment if you know the answer.
         if(event.data === undefined)
             return;

         writeResponse(event.data);
     };

     webSocket.onmessage = function(event){
         writeResponse(event.data);
         // Parsing JSON ojbect with coordinates
         var obj = JSON.parse(event.data);
         writeResponse(obj['status']);
         writeResponse(obj['latitude']);
         writeResponse(obj['longitude']);
         
         var myLatLng3 = {lat: obj['latitude'], lng: obj['longitude']};
         writeResponse(myLatLng3);
         var jsontext = JSON.stringify(myLatLng3);
         writeResponse(jsontext);

         //var myLatLng3 = '{'+'"lat" : "' + obj['latitude'] +'" ,' + '"lng"  : "' + obj['longitude'] + '"}';
        // var myLatLng3 = {lat: -24.0167, lng: -53.4667};
         var myLatlng = new google.maps.LatLng(-25.363882,131.044922);
         var marker = new google.maps.Marker({
 		    map: map,
 		    position: {lat: obj['latitude'], lng: obj['longitude']},
 		    title: 'Hello World!'
 		  });
         
         //marker.setMap(map);
         
         
         //console.log(data['status']);
         // End parsing
     };

     webSocket.onclose = function(event){
         writeResponse("Connection closed");
     };
 }

 /**
  * Sends the value of the text input to the server
  */
 function send(){
     var text = document.getElementById("messageinput").value;
     webSocket.send(text);
 }

 function closeSocket(){
     webSocket.close();
 }

 function writeResponse(text){
     messages.innerHTML += "<br/>" + text;
 }
// End another websocket example


    </script>
    <script async defer
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDLvSZuLTHdjoM20vW0TPIFor_S-isz7wo&callback=initMap">
    </script>
  </body>
</html>