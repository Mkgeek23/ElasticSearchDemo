# Elastic Search Demo

Demonstration application client - server using Elasticsearch.

### Technologies used:
<div>  
<a href="https://www.elastic.co/" target="_blank"><img style="margin: 10px" src="https://profilinator.rishav.dev/skills-assets/elasticsearch.png" alt="Elastic Search" height="50" /></a>  
<a href="https://www.java.com/" target="_blank"><img style="margin: 10px" src="https://profilinator.rishav.dev/skills-assets/java-original-wordmark.svg" alt="Java" height="50" /></a>   
<a href="https://www.elastic.co/kibana/" target="_blank"><img style="margin: 10px" src="https://profilinator.rishav.dev/skills-assets/kibana.png" alt="Kibana" height="50" /></a>  
</div>


## How it works
[Elasticsearch version 7.10 is required for the proper operation of the application]

The application has a server and a client part. Attempting to start the client when the server is not active will throw an exception and terminate the program. After starting the server, you can start the client application (the server is capable of serving multiple connected clients). After connecting, the client must log in to the server using the login and password. After logging in, the client can use the /search command and then be prompted to enter the phrase they are looking for. After the correct entry, the command will be sent to the server, where it will create a connection for a specific client with Elasticsearch, and then return a list (up to 10) of books that contain the searched phrase (or similar depending on the server settings).

In order to test the application, I used 20 different books in docx and pdf formats

## Screenshots
![App Screenshot](screenshots/screen1.png)
![App Screenshot](screenshots/screen2.png)