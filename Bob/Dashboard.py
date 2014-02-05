import cherrypy
from DbHandler import DbHandler
import os,inspect
from datetime import *

class Server():
    cherrypy.log.error_log.propagate = False
    cherrypy.log.access_log.propagate = False
    db = DbHandler()
    
    def dashboard(self,name=None):
        if name == "bob":
            return self.dashboardBob()
        else:
            return "No dashboard selected"
    dashboard.exposed = True
    
    def dashboardBob(self):
        dates = []
        numbers = []
        crawldata = self.db.getCrawlData(7)
        check_date = date.today()-timedelta(days=6)
        checked = 0
        while checked < 7:
            found = False
            for i in crawldata:
                if i[0] == check_date:
                    dates.append(i[0].strftime("%d-%m-%Y"))
                    numbers.append(str(int(i[1])))
                    found = True
            if not found:
                dates.append(check_date.strftime("%d-%m-%Y"))
                numbers.append("0")
            checked += 1
            check_date += timedelta(days=1)
            
        datestring = "['" + "','".join(dates) + "']"
        numberstring = "[" + ",".join(numbers) + "]"
        
        #return numberstring
        
        return """  <head>
                        <title>Dashboard van Bob</title>
                        <script src="/static/Chart.js"></script>
                    </head>
                    <body>
                    <canvas id="statChart" width="400" height="400"></canvas>
                    <script>
                        var ctx = document.getElementById("statChart").getContext("2d");
                        var data = {
                            labels : %s,
                            datasets : [
                                {
                                    fillColor : "rgba(151,187,205,0.5)",
                                    strokeColor : "rgba(151,187,205,1)",
                                    pointColor : "rgba(151,187,205,1)",
                                    pointStrokeColor : "rgba(151,187,205,0)",
                                    data : %s
                                }
                            ]
                        }
                        var myNewChart = new Chart(ctx).Line(data);
                    </script>
                    </body>""" % (datestring,numberstring)
    
path = os.path.dirname(os.path.abspath(inspect.getfile(inspect.currentframe())))
config = {'global':
                {'tools.staticdir.root':path,
                 'server.socket_port': 8009, 
                 'server.socket_host':'0.0.0.0'
                },
          '/static':
                {'tools.staticdir.on': True,
                 'tools.staticdir.dir': 'static',
                }
        }
cherrypy.quickstart(Server(),config=config)
