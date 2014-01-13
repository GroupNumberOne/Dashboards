from DbHandler import DbHandler

db = DbHandler()

cityArray = db.getLocations()
        
cityArrayString = ''

#convert array into a string in order to insert it in JS code


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
    
            

for c in cityArray: 
    cityArrayString = cityArrayString + str(c) + ', '

cityArrayString = cityArrayString[:-2]

print cityArrayString