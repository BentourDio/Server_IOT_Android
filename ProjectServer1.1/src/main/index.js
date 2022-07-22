//global variables
var map;
let iot1;
let iot2;
let android;
//variables for rectangle
var x1;
var x2;
var x3;
var x4;
var y1;
var y2;
let rectangle;
////////////////


//initMap is called by the link with API key
function initMap() {
  //map options
  var options = {
    center: { lat: 37.96790421900921, lng: 23.76626294807113 },
    zoom: 18,
  };

  map = new google.maps.Map(document.getElementById("map"), options);


  //setMarker(37.97332944, 23.72166378);
  //some default values to initialize the map
  iot1 = new IOT("IOT1",0,0,0,0,37.96809452684323,23.76630586399502,87,map);
  iot2 = new IOT("IOT2", 0,0,0,0,37.96799937191987,23.766603589104385,89,map);
  android = new Android("ANDR",37.96790421900921,23.76626294807113,99,map);
}

// document.addEventListener('DOMContentLoaded', function(){
//   startLiveUpdate();
// });

//         lat: 37.97332944, lng: 23.72166378

function updateIOT1(gas,smoke,temp,uv,x,y){
  iot1.updateValuesInfoW(smoke,gas,temp,uv,x,y);
}

function updateIOT2(gas,smoke,temp,uv,x,y){
  iot2.updateValuesInfoW(smoke,gas,temp,uv,x,y);
}
function updateAndroid(x,y,battery){
  android.updateValuesAndroid(x,y,battery);
}

function createRectangle(){

  // Define the LatLng coordinates for the polygon's path.


  x1 = iot1.x;
  y1 = iot1.y;
  x3 = iot2.x;
  y3 = iot2.y;
  x2 = (x1 + x3 + y1 - y3) / 2;
  y2 = (- x1 + x3 + y1 + y3) / 2;
  x4 = (x1 + x3 - y1 + y3) / 2;
  y4 = (x1 - x3 + y1 + y3) / 2;

  var points = new google.maps.MVCArray();

  points.push(new google.maps.LatLng(x1, y1));
  points.push(new google.maps.LatLng(x2, y2));
  points.push(new google.maps.LatLng(x3, y3));
  points.push(new google.maps.LatLng(x4, y4));

  // Construct the polygon.
    rectangle = new google.maps.Polygon({
    path: points,
    strokeColor: "#FF0000",
    strokeOpacity: 0.1,
    strokeWeight: 2,
    fillColor: "#FF0000",
    fillOpacity: 0.35
  });

  rectangle.setMap(map);
}

function destroyRectangle(){

  rectangle.setMap(null);
}
function destroyCircleIOT(){
  iot1.circle.setMap(null);
  iot2.circle.setMap(null);
}
function setDangerCirleIOT(dangerLevel,iot){
  

  if (dangerLevel == 1){
    iot.circle = new google.maps.Circle({
      strokeColor: "#00FF00",
      strokeOpacity: 0.3,
      strokeWeight: 2,
      fillColor: "#00FF00",
      fillOpacity: 0.5,
      map,
      center: new google.maps.LatLng(iot.x, iot.y),
      radius: 8,
    });
  }

  

  if (dangerLevel == 2){
    iot.circle = new google.maps.Circle({
      strokeColor: "#FF0000",
      strokeOpacity: 0.3,
      strokeWeight: 2,
      fillColor: "#FF0000",
      fillOpacity: 0.5,
      map,
      center: new google.maps.LatLng(iot.x, iot.y),
      radius: 8,
    });
  }
}

function setIconMediumDanger(iot){
  iot.marker.setIcon("http://maps.google.com/mapfiles/ms/icons/orange-dot.png");
}

function setIconHighDanger(iot){
  iot.marker.setIcon("http://maps.google.com/mapfiles/ms/icons/caution.png");
}

function setIconNoDanger(){
  iot1.marker.setIcon("http://maps.google.com/mapfiles/ms/icons/blue.png");
  iot2.marker.setIcon("http://maps.google.com/mapfiles/ms/icons/blue.png");
}
