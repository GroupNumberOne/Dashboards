import urllib

url = "https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&language=nl"


raw_result = urllib.urlopen(url).read()
#status = GetStatus(raw_result)
print raw_result