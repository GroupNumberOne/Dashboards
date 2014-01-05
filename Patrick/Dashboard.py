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
        
        x = 0
        for c in cityArray: #This is to lose excess space at either end of the strings.
            text = ''
            for s in str(c).split():
                text = text + s + " "
            cityArray[x] = "\""+text.lower()[2:-4]+"\""
            x=x+1
            
        cityArray = sorted(set(cityArray)) #remove duplicates
        
        cityArrayString = ''
        
        for c in cityArray: #convert array into a string
            cityArrayString = cityArrayString + c + ', '
        
        cityArrayString = cityArrayString[:-2]
        
        return """  <html>
                      <head>
                        <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
                        <meta charset="utf-8">
                        <title>Dashboard van Patrick</title>
                        <style>
                          html, body, #map-canvas {
                            height: 900px;
                            width: 800px;
                            margin: 0px;
                            padding: 0px
                        </style>
                        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&language=nl"></script>
                        <script>
                        
                            var city
                            
                            var map
                            
                            var cityArray = new Array();
                            
                            cityArray = [%s];
                            
                            function initialize() {
                                var infowindow = new google.maps.InfoWindow();
                                var geocoder = new google.maps.Geocoder();
                                  var mapOptions = {
                                    zoom: 8,
                                    center: new google.maps.LatLng(52.132633,5.291266)
                                  }
                                  var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
                                  
                                  var timer = 0
                                  
                                  x=0
                                  var location;
                                  for (x = 0; x < cityArray.length; x++) {
                                        geocoder.geocode( {'address': cityArray[x],'region':'NL'}, function(x){return function(results, status) {
                                            if (status == google.maps.GeocoderStatus.OK) {
                                              var marker = new google.maps.Marker({
                                                  map: map,
                                                  position: results[0].geometry.location,
                                                  title: cityArray[x],
                                                  animation: google.maps.Animation.DROP
                                              });
                                            } else if (status === google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {    
                                                setTimeout(function() {
                                                }, 2000);
                                            } else {
                                              alert('Geocode was not successful for the following reason: ' + status);
                                            }
                                        }
                                        }(x));
                                  }
                            }
                    
                            google.maps.event.addDomListener(window, 'load', initialize);
                            
                            mark();
                            
                            function mark(){
                                for (x = 0; x < cityArray.length; x++) {
                                    geocodeAndMark(x);
                                }
                            }
                            
                            function geocodeAndMark(x){
                                geocoder.geocode( {'address': cityArray[x],'region':'NL'}, function(results, status) {
                                    if (status == google.maps.GeocoderStatus.OK) {
                                      var marker = new google.maps.Marker({
                                          map: map,
                                          position: results[0].geometry.location,
                                          title: cityArray[x],
                                          animation: google.maps.Animation.DROP
                                      });
                                    } else if (status === google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {    
                                        setTimeout(function() {
                                            geocodeAndMark(x);
                                        }, 2000);
                                    } else {
                                      alert('Geocode was not successful for the following reason: ' + status);
                                    }
                                }
                                );
                            }
                    
                        </script>
                      </head>
                      <body>
                          <div id="map-canvas"></div>
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