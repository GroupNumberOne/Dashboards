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
                                  var mapOptions = {
                                    zoom: 8,
                                    center: new google.maps.LatLng(52.132633,5.291266)
                                  }
                                  var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
                                                                    
                                  var x = 0
                                  var city;
                                  var lat;
                                  var lng;
                                  
                                  for (x = 0; x < cityArray.length; x++) {
                                      city = cityArray[x][0];
                                      lat = cityArray[x][1];
                                      lng = cityArray[x][2];
                                      var latlng = new google.maps.LatLng(lat,lng);
                                      var marker = new google.maps.Marker({
                                          map: map,
                                          position: latlng,
                                          title: city,
                                          animation: google.maps.Animation.DROP
                                      });
                                  }
                            }
                    
                            google.maps.event.addDomListener(window, 'load', initialize);
                    
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