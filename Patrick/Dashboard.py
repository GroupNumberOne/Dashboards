import cherrypy
from DbHandler import DbHandler
import os,inspect

class Server():
    cherrypy.log.error_log.propagate = False
    cherrypy.log.access_log.propagate = False
    
    db = DbHandler()
    
    def dashboard(self,name=None):
        if name == "patrick":
            return self.dashboardPatrick()
        else:
            return "No dashboard selected"
    dashboard.exposed = True
    
    def dashboardPatrick(self):
        
        cityArray = self.db.getLocations()
        
        cityArrayString = ''
        
        #make the city names neatly capitalized
        
        for c in cityArray:
            string = c[0]
            chars = list(c[0])
            if string.find(' ')>0:
                loc = string.find(' ')
                chars[loc+1] = chars[loc+1].upper()
            elif string.find('-')>0:
                loc = string.find('-')
                chars[loc+1] = chars[loc+1].upper()
            chars[0] = chars[0].upper()
            c[0] = ''.join(chars)
        
        #convert array into a string in order to insert it in JS code
        
        for c in cityArray: 
            cityArrayString = cityArrayString + str(c) + ', '
        
        cityArrayString = cityArrayString[:-2]
        
        
        return """  <html>
                      <head>
                        <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
                        <meta charset="utf-8">
                        <title>Dashboard van Patrick</title>
                        <style>
                          #map-canvas {
                            height: 700px;
                            width: 600px;
                            margin: 0px;
                            padding: 50px }
                          #map-canvas, #ui {
                            display: inline-block;
                          }
                          #ui{
                          position:fixed;
                          top:100px;
                          margin:100px;
                          }
                        </style>
                        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&language=nl"></script>
                        <script>
                        
                            var city;
                            
                            var map;
                            
                            var cityArray = new Array();
                            
                            cityArray = [%s];
                            
                            var markersArray = new Array();
                            
                            function initialize() {
                                var infowindow = new google.maps.InfoWindow();
                                  var mapOptions = {
                                    zoom: 8,
                                    center: new google.maps.LatLng(52.132633,5.291266)
                                  }
                                  map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
                                                                    
                                  var x = 0;
                                  var city;
                                  var lat;
                                  var lng;
                                  
                                  for (x = 0; x < cityArray.length; x++) {
                                      city = cityArray[x][0];
                                      lat = cityArray[x][1];
                                      lng = cityArray[x][2];
                                      placeMarker(map,city,lat,lng);
                                  }
                            }
                    
                            google.maps.event.addDomListener(window, 'load', initialize);
                            
                            function placeMarkersInRange(){
                                oForm = document.getElementById("range_form");
                                main = oForm.elements["city"].value;
                                range = oForm.elements["radius"].value;
                                index = -1;
                                
                                for(x=0; x<cityArray.length; x++){
                                    if(cityArray[x][0].toLowerCase() == main.toLowerCase()){
                                        index = x;
                                    }
                                }
                                
                                if(index == -1){
                                    alert("Stad niet gevonden. Check uw spelling of probeer een andere plaats");
                                }
                                else{
                                    removeMarkers();
                                    
                                    mainLat = cityArray[index][1];
                                    mainLon = cityArray[index][2];
                                    
                                    for(x=0; x<cityArray.length; x++){
                                        targetName = cityArray[x][0];
                                        targetLat = cityArray[x][1];
                                        targetLon = cityArray[x][2];
                                        distance = calculateDistance(mainLat,mainLon,targetLat,targetLon);
                                        if(distance <= range){
                                            placeMarker(map,targetName,targetLat,targetLon);
                                        }
                                    }
                                }
                            }
                            
                            function placeMarker(map,name,lat,lng){
                                var latlng = new google.maps.LatLng(lat,lng);
                                var marker = new google.maps.Marker({
                                    map: map,
                                    position: latlng,
                                    title: name,
                                    animation: google.maps.Animation.DROP
                                });
                                markersArray.push(marker);                            
                            }
                            
                            function removeMarkers(){
                                for(m in markersArray){ 
                                    markersArray[m].setMap(null);  
                                }                              
                            }
                            
                            function calculateDistance(lat1,lon1,lat2,lon2){
                                var R = 6371; // km
                                var dLat = (lat2-lat1).toRad();
                                var dLon = (lon2-lon1).toRad();
                                var lat1 = lat1.toRad();
                                var lat2 = lat2.toRad();
                                
                                var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                                        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
                                var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
                                var d = R * c; //returns distance in km
                                d =  Math.round(d);
                                return d;
                            }
                            
                            // Converts numeric degrees to radians
                            if (typeof(Number.prototype.toRad) === "undefined") {
                              Number.prototype.toRad = function() {
                                return this * Math.PI / 180;
                              }
                            }
                    
                        </script>
                      </head>
                      <body>
                          <div id="map-canvas"></div>
                          <div id="ui">
                              <p>
                                U kunt ook bij u in de buurt zoeken.
                                
                                Voer hier uw woonplaats in.
                              </p>
                              <form id="range_form">
                                <input type="text" name="city"><br>
                                <br>
                                <input type="radio" name="radius" value="5">5 km<br>
                                <input type="radio" name="radius" value="10">10 km<br>
                                <input type="radio" name="radius" value="20">20 km<br>
                                <input type="radio" name="radius" value="50">50 km<br>
                                <br>
                                <input onClick=placeMarkersInRange() type="button" value="Vind plaatsen met vacatures of cv's in de buurt">
                              </form>
                          </div>
                      </body>
                    </html>""" % (cityArrayString)

    
path = os.path.dirname(os.path.abspath(inspect.getfile(inspect.currentframe())))
print(path)
config = {'global':
                {'tools.staticdir.root':path,
                 'server.socket_port': 8008, 
                 'server.socket_host':'0.0.0.0'
                },
          '/static':
                {'tools.staticdir.on': True,
                 'tools.staticdir.dir': 'static'
                }
        }
cherrypy.quickstart(Server(),config=config)