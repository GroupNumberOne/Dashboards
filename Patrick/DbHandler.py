import psycopg2
import psycopg2.extras
import logging

import SQL as sql

class DbHandler:    

    isConn = False    
    
    host = '145.24.222.158'
    dbname = 'INFPRJ01-56'
    user = 'postgres'
    password = 'GroeP1'

    x = 5 # max of 5 conn retries
    conn = None
    cursor = None
    
    def __init__(self):
        global conn,cursor#,host,dbname,user,password
        while not DbHandler.isConn and self.x != 0:
            try:
                conn_string = 'host=145.24.222.158 dbname=INFPRJ01-56 user=postgres password=GroeP1'
                conn = psycopg2.connect(conn_string)
                cursor = conn.cursor(cursor_factory=psycopg2.extras.DictCursor)
                DbHandler.isConn = True
            except:
                logging.info('Can\'t connect to the database')
                time.sleep(3)
                self.x= self.x- 1        
            
    def getLocations(self):
        global cursor
        try:
            cursor.execute(sql.cv_locations)
            list1 = cursor.fetchall()
            cursor.execute(sql.vac_locations)
            list2 = cursor.fetchall()
            return list1 + list2
        except Exception,e:
            logging.info('Can\'t execute query')
            logging.debug(e)