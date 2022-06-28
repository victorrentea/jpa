# JPA Training 

### Assignment
First, start up the database by running `StartDatabase.java`. You can then connect to it from your favourite DB manager, using:
- connection string `jdbc:h2:tcp://localhost:9092/~/test`
- user `sa`
- password `sa`
- driver H2

## Troubleshooting
1. If after importing in your IDE, the compiler complains it cannot find some classes beginning with Q... (eg QTeacher), please run `mvn install` from terminal to generate the QueryDSL classes. 