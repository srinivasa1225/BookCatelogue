# Getting Started

Run Application 

./mvnw clean install

./mvnw spring-boot:run

URL's

AddBook : POST http://{{hostname}}:9090/api/bookcatalogue/book/v1/addBook
UpdateBook : POST http://{{hostname}}:9090/api/bookcatalogue/book/v1/updateBook/{isbn}
DeleteBook : DELETE http://{{hostname}}:9090/api/bookcatalogue/book/v1/deleteBook/{isbn}
Search Links
 GET http://{{hostname}}:9090/api/bookcatalogue/book/v1/getByIsbn/{isbn}
 GET http://{{hostname}}:9090/api/bookcatalogue/book/v1/getByTitle?title=title
 GET http://{{hostname}}:9090/api/bookcatalogue/book/v1/getByTitle?author=author