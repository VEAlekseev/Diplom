# Diploma

### [План автоматизации](https://github.com/VEAlekseev/Diploma/blob/master/Plan.md)

### [Все найденные баги заведены в issues](https://github.com/VEAlekseev/Diploma/issues) 


#### Для работы с MySQL
1. Запустить контейнеры: MySQL, Node.js
    ```
    docker-compose -f docker-compose-msql.yml up -d --force-recreate
    ```

2. Запустить SUT
    ```
    java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar
    ```

3. Запустить тесты
    ```
    gradlew test -Dtest.db.url=jdbc:mysql://localhost:3306/app
    ```
   
4. После прогона тестов остановить контейнеры
    ```
    docker-compose -f docker-compose-ms.yml down
    ```
   
#### Для работы с Postgres
1. Запустить контейнеры: Postgres, Node.js
    ```
    docker-compose -f docker-compose-psql.yml up -d --force-recreate
    ```

2. Запустить SUT
    ```
    java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar artifacts/aqa-shop.jar
    ```

3. Запустить тесты
    ```
    gradlew test -Dtest.db.url=jdbc:postgresql://localhost:5432/app
    ```
4. После прогона тестов остановить контейнеры
    ```
    docker-compose -f docker-compose-ps.yml down
    ```
   
   ### Allure
   
   Для запуска тестов Allure выполнить команду 
   ```
   ./gradlew clean test allureReport
   ```
   Для просмотра результатов 
   ```
   ./gradlew allureServe
   ```
