
# URL-Shortener

A small URL-Shortener project in Spring Boot.
## Information about the project

The project followed a the instructions below:
-   Design and implement an API for short URL creation
-   Implement forwarding of short URLs to the original ones
-   There should be some form of persistent storage
-   The application should be distributed as one or more Docker images
Including the additional requirement:
-   Design and implement an API for gathering different statistics


### Installing

Clone or download the repository:
```
$ git clone https://github.com/jeantravassos/url-shortener.git
```

It's required to have Docker installed

```
$ cd url-shortener && docker-compose up
```


## APIs

Explain how to run the automated tests for this system


| Action | URI | Request Method | Body |
| --- | --- | --- | --- |
| Shortened a URL | http://localhost:8080/url | POST | {"url":"http://www.neueda.com"} |
| Redirect | http://localhost:8080/url/{shortCode} | GET | - |
| Get statistics | http://localhost:8080/statistics/{shortCode} | GET | - |


## Built With

* [Spring boot](https://spring.io/projects/spring-boot)
* [H2](https://www.h2database.com/html/main.html)
* [Project Lombok](https://projectlombok.org/)
* [Swagger](https://swagger.io/)
* [Mockito](https://site.mockito.org/)
* [MockMVC](https://spring.io/guides/gs/testing-web/)
* [Maven](https://maven.apache.org/)
* [Docker](https://www.docker.com/)

## Author

* **Jean Travassos** - *Senior Software Engineer* - [LinkedIn]([https://www.linkedin.com/in/jeantravassos/](https://www.linkedin.com/in/jeantravassos/))

