# Diploma

### [План автоматизации](https://github.com/VEAlekseev/Diploma/blob/master/Plan.md)

### [Все найденные баги заведены в issues](https://github.com/VEAlekseev/Diploma/issues) 


#### Для работы с MySQL
1. Запустить контейнеры: MySQL, Node.js
    ```
    docker-compose -f docker-compose-msql.yml up -d
    ```
1. Запустить сервис gate-simulator. Для этого из папки с сервисом выполнить команду 
    ```
    npm start
    ```

1. Запустить SUT
    ```
    java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar
    ```

1. Запустить тесты с формированием отчета Allure
    ```
    gradlew test -Ddb.url=jdbc:mysql://localhost:3306/app clean test allureReport

    ```
   Для открытия отчета в браузере выполнить команду 
      ```
      gradlew allureServe
      ```
   
1. После прогона тестов остановить контейнеры
    ```
    docker-compose -f docker-compose-msql.yml down
    ```
   
#### Для работы с Postgres
1. Запустить контейнеры: Postgres, Node.js
    ```
    docker-compose -f docker-compose-psql.yml up -d
    ```
1. Запустить сервис gate-simulator. Для этого из папки с сервисом выполнить команду 
    ```
    npm start
    ```
   
1. Запустить SUT
    ```
    java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar artifacts/aqa-shop.jar
    ```

1. Запустить тесты с формированием отчета Allure
    ```
    gradlew test -Ddb.url=jdbc:postgresql://localhost:5432/app clean test allureReport
    ```
   Для открытия отчета в браузере выполнить команду 
      ```
      gradlew allureServe
      ```
1. После прогона тестов остановить контейнеры
    ```
    docker-compose -f docker-compose-psql.yml down
    ```