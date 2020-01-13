# Дипломный проект профессии «Тестировщик»

##### Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

## Документы:
* ### [План автоматизации](https://github.com/VEAlekseev/Diploma/tree/master/Docs/Plan.md)

* ### [Все найденные баги заведены в issues](https://github.com/VEAlekseev/Diploma/issues) 

* ### [Отчет по итогам тестирования](https://github.com/VEAlekseev/Diploma/tree/master/Docs/Report.md)

* ### [Отчётные документы по итогам автоматизации](https://github.com/VEAlekseev/Diploma/tree/master/Docs/Summary.md)

#### Для работы с MySQL
1. Запустить контейнеры: MySQL, Node.js
    ```
    docker-compose -f docker-compose-msql.yml up -d
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
