# YesImdbProject
Download the jar file from the repository
Run the following command "java -jar <location of the jar file> (Requires java runtime to be installed on the machine)
This is a spring boot application and it comes with tomcat inbuilt. The default port that is used is 8080. So make sure that this port is not being used while running the project.
  
Functionality
This project exposes three end points:
  
  1. Returns a list of movies based on the search result. (E.g : localhost:8080/search/hanks or you can also use the full name(tom hanks) of the person you are looking to get the movies of. The application is case insensitive so feel free to use capitalization).

2. Returns a list of movies that both the search results are a part of(E.g : localhost/common/hanks/spielberg)

3. Returns a list of movies that released in the year entered in the search (E.g: localhost/year/1998)
  
  
P.S - You can use full names or second names for the search or a combination of full names and second names for the "common API".
