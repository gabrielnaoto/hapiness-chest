var geocoder;
var map;
var marker;

function initialize() {
    debugger;
    var latlng = new google.maps.LatLng(-18.8800397, -47.05878999999999);
    map = new google.maps.Map(
             document.getElementById("mapa")
            ,{
               zoom: 5
              ,center: latlng
              ,mapTypeId: google.maps.MapTypeId.ROADMAP
            }
    );

    geocoder = new google.maps.Geocoder();

    marker = new google.maps.Marker({
        map: map,
        draggable: true
    });

    marker.setPosition(latlng);
}

$(document).ready(function () {
    debugger;
    initialize();
});