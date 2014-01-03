from DbHandler import DbHandler

db = DbHandler()

cityArray = db.getLocations()

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
print(cityArrayString)