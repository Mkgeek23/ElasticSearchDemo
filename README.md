# Elastic Search Demo

Aplikacja demonstracyjna klient - serwer z użyciem Elasticsearch. 

### Wykorzystane technologie:
<div>  
<a href="https://www.elastic.co/" target="_blank"><img style="margin: 10px" src="https://profilinator.rishav.dev/skills-assets/elasticsearch.png" alt="Elastic Search" height="50" /></a>  
<a href="https://www.java.com/" target="_blank"><img style="margin: 10px" src="https://profilinator.rishav.dev/skills-assets/java-original-wordmark.svg" alt="Java" height="50" /></a>   
<a href="https://www.elastic.co/kibana/" target="_blank"><img style="margin: 10px" src="https://profilinator.rishav.dev/skills-assets/kibana.png" alt="Kibana" height="50" /></a>  
</div>


## Działanie aplikacji
[Do prawidłowego działania aplikacji wymagany jest Elasticsearch]

Aplikacja posiada część serwerową oraz kliencką. Próba uruchomienia klienta, gdy serwer nie będzie aktywny spowoduje wyrzucenie wyjątku oraz zakończenie programu. Po uruchomieniu serwera można uruchomić aplikację kliencką (serwer jest w stanie obsługiwać wielu podłączonych klientów). Klient po podłączeniu musi się zalogować na serwer przy pomocy loginu oraz hasła. Po zalogowaniu klient może użyć komendy /search, aby następnie zostać poproszonym o wpisanie frazy, której szuka. Po poprawnym wpisaniu, komenda zostanie przesłana na serwer, gdzie utworzy on połączenie dla konkretnego klienta z Elasticsearch, a następnie zwróci mu listę (maksymalnie 10) książek, które zawierają szukaną frazę (lub podobną w zależności od ustawień serwera).

W celu przetestowania aplikacji użyłem 20 różnych książek w formatach docx oraz pdf

## Screenshots
![App Screenshot](screenshots/screen1.png)
![App Screenshot](screenshots/screen2.png)