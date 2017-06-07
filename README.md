# PivotusVenturesWeatherServer
WeatherServer Application
A server application written in Java that aggregates information from two apis :-
'http://api.openweathermap.org/data/2.5/weather?q={city}&APPID={key}&units=imperial', and
'http://api.wunderground.com/api/{key}/conditions/q/CA/{city}.json'
and present the response in form of such a JSON object :-
{ temp: 20.3,
  std: 0.2,
  all: [
    20.1,
    20.5
  ]
}
# Dependencies
In order to run the application Tomcat v9.0 should be installed in your system.

# How To Run
1. Clone the repo or download the zip folder
2. Extract it and open in your favourite IDE.
3. Run it with Tomcat v9.0 running as the referenced server.
4. In order to observe the JSOn Response, hit either the curl command or the Postman using following url :-
    http://localhost:8080/WeatherAppServer/api/weather?city=Los+Angeles

Based on the above request, a following response will be observed :-
{
  "all": [
    63.81,
    63.3
  ],
  "temp": 63.555,
  "std": 0.25500000000000256
}

# Testing
Tried testing the application with manual test cases but couldnt execute the test cases correctly as everytime, there occured a substantial difference within the values.

# Thoughts about Scalable Design
To make the application scalable to close to 100 apis(i.e. different Weather api to aggregate data from) , the design that I could think of is that an API interface can be created that every new API has to implement.
The Interface will have just the fields such as String url and String APP Key.
A list of all APIs can be created which will keep a track of the total APIs that need to hit to aggregate data. For example if we have abc-API in the application, we can just implement the functionality using the interface and then add that API to the list to be called by the application. 
This is how I think the application can be made more scalable




