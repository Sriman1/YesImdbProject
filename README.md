# YesImdbProject
Download the jar file(searchDemo-0.0.1-SNAPSHOT.jar) from the repository
Run the following command "java -jar {location of the jar file}" to run the application on your machine (Requires java runtime to be installed on the machine)
This is a spring boot application and it comes with tomcat inbuilt. The default port is 8080. So make sure that this port is not being used while running the project.
After the spring boot application starts up, It takes around 7 to 11 seconds before your can start making the API calls.
In this time the in memory data structure is populated by scraping and parsing the IMDB web pages.

  
Functionality:
This application exposes three end points:
  
1. Search API for one person - Returns a list of movies along with the year of release based on the search field. (E.g : localhost:8080/search/hanks or you can also use the full name(tom hanks) of the person you are looking to get the movies of. The application is case insensitive so feel free to use capitalization).

2. Search API for common movies of both people -  Returns a list of movies along with the year of release that both the search fields/names are a part of(E.g : localhost:8080/search/hanks/spielberg)

3. Year API - Returns a list of movies that released in the year entered in the search (E.g: localhost/year/1998)
  
The APIs return an empty list if there are no matching results.
P.S - You can use full names or second names for the search or a combination of full names and second names for the "common API".


