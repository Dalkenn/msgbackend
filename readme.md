How to start the project:
./mvnw spring-boot:run

There is a request folder. If you use Intellij Idea, you can use this basic http file to send requests to the API.
There is also swagger-ui available at the default address: http://localhost:8080/swagger-ui/index.html

Implemented:
- Create new user with a user name. User names are unique.
- A user can send a message to another using the user's id. You cannot send a message to yourself.
- The messages are persisted in DB.
- It's possible to see the message that a user sent or received.
- Test for the User controller have been implemented
- End-to-end test of the User controller
- Unit tests on the messageService


Not implemented:
- I can view all messages received from a particular user.
- Sending messages through a messaging system.
- End-to-end test on the MessagesController
- Unit tests on the UserService

To do:
- Code quality
- Caching
- Test coverage
- Sanitize inputs of the controllers
- Proper implementation of filtering, pagination and sorting on the messages endpoints
- Logging
- Prometheus metrics

To build a docker image:
./mvnw spring-boot:build-image


