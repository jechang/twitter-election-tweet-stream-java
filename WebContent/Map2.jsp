<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Heatmaps</title>
    <style>
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
      #map {
        height: 100%;
      }
#floating-panel {
  position: absolute;
  top: 10px;
  left: 25%;
  z-index: 5;
  background-color: #fff;
  padding: 5px;
  border: 1px solid #999;
  text-align: center;
  font-family: 'Roboto','sans-serif';
  line-height: 30px;
  padding-left: 10px;
}

      #floating-panel {
        background-color: #fff;
        border: 1px solid #999;
        left: 25%;
        padding: 5px;
        position: absolute;
        top: 10px;
        z-index: 5;
      }
    </style>
  </head>

  <body>
    <div id="floating-panel">
      <button onclick="toggleHeatmap()">Toggle Heatmap</button>
      <button onclick="changeGradient()">Change gradient</button>
      <button onclick="changeRadius()">Change radius</button>
      <button onclick="changeOpacity()">Change opacity</button>
    </div>
    <div id="map"></div>
    <div>
		<input type="text" id="messageinput"/>
	</div>
	<div id="floating-panel">
	    <button type="button" onclick="openSocket();" >Open</button>
		<button type="button" onclick="send();" >Send</button>
		<button type="button" onclick="closeSocket();" >Close</button>
	</div>
	
	<form id="filter" method="POST" action='#'>
        <select id='keywords' name='keywords'>
            <option value='all'>All</option>
            <option value='movies'>Movies</option>
            <option value='music'>Music</option>
            <option value='sports'>Sports</option>
            <option value='finance'>Finance</option>
            <option value='technology'>Technology</option>
            <option value='fashion'>Fashion</option>
            <option value='science'>Science</option>
            <option value='travel'>Travel</option>
            <option value='health'>Health</option>
            <option value='cricket'>Cricket</option>
            <option value='india'>India</option>
          </select>
          <input type="submit" value="Filter">
    </form>
    
	Server responses get written here
	<div id="messages"></div>
	
    <script>


var map, heatmap;

var livetweetsArray;
//var livetweetsArray;

//livetweetsArray.push(new google.maps.LatLng(48.8566667, 2.3509871)); // Paris
//livetweetsArray.push(new google.maps.LatLng(50.6371834, 3.0630174)); // Lille
//livetweetsArray = new google.maps.MVCArray();

function initMap() {
	
  map = new google.maps.Map(document.getElementById('map'), {
    zoom: 1,
    center: {lat: 37.775, lng: -122.434},
    mapTypeId: google.maps.MapTypeId.SATELLITE
  });
  
  var taxiData = [new google.maps.LatLng(37.782551, -122.445368), new google.maps.LatLng(37.782745, -122.444586)];

  livetweetsArray = new google.maps.MVCArray(taxiData);
  
  livetweetsArray.push(new google.maps.LatLng(50.6371834, 3.0630174));

  heatmap = new google.maps.visualization.HeatmapLayer({
    data: livetweetsArray,
    map: map
  });
  
  heatmap.setMap(map);
}

function updateMap() {
	livetweetsArray.push(new google.maps.LatLng(-24.6371834, 53.0630174));
}

function toggleHeatmap() {
  heatmap.setMap(heatmap.getMap() ? null : map);
}

function changeGradient() {
  var gradient = [
    'rgba(0, 255, 255, 0)',
    'rgba(0, 255, 255, 1)',
    'rgba(0, 191, 255, 1)',
    'rgba(0, 127, 255, 1)',
    'rgba(0, 63, 255, 1)',
    'rgba(0, 0, 255, 1)',
    'rgba(0, 0, 223, 1)',
    'rgba(0, 0, 191, 1)',
    'rgba(0, 0, 159, 1)',
    'rgba(0, 0, 127, 1)',
    'rgba(63, 0, 91, 1)',
    'rgba(127, 0, 63, 1)',
    'rgba(191, 0, 31, 1)',
    'rgba(255, 0, 0, 1)'
  ]
  heatmap.set('gradient', heatmap.get('gradient') ? null : gradient);
}

function changeRadius() {
  heatmap.set('radius', heatmap.get('radius') ? null : 20);
}

function changeOpacity() {
  heatmap.set('opacity', heatmap.get('opacity') ? null : 0.2);
}

// Heatmap data: 500 Points
function getPoints() {
  return [
    new google.maps.LatLng(37.782551, -122.445368),
    /*
    new google.maps.LatLng(37.782745, -122.444586),
    new google.maps.LatLng(37.782842, -122.443688),
    new google.maps.LatLng(37.782919, -122.442815),
    new google.maps.LatLng(37.782992, -122.442112),
    new google.maps.LatLng(37.783100, -122.441461),
    new google.maps.LatLng(37.783206, -122.440829),
    */
  ];
}

//another websocket example
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
        
        var lat = obj['latitude'];
        var lng = obj['longitude'];
 
        //var myLatLng = {lat: , lng: obj['longitude']};
        //writeResponse(myLatLng);
        var jsontext = JSON.stringify(myLatLng);
        writeResponse(jsontext);
        
        livetweetsArray.push(new google.maps.LatLng(24.0167, -53.4667));
        
        updateMap();

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
	
	var messageElemt = document.getElementById("messages");
    messages.innerHTML += "<br/>" + text;
}
//End another websocket example

    </script>
    <script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDLvSZuLTHdjoM20vW0TPIFor_S-isz7wo&signed_in=true&libraries=visualization&callback=initMap">
    </script>
  </body>
</html>