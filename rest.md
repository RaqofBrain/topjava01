GET /topjava/rest/meals - get list of all meals for logged in user

    curl --location 'http://localhost:8080/topjava/rest/meals'

GET /topjava/rest/meals/{id} - get meal with specified id

    curl --location 'http://localhost:8080/topjava/rest/meals/100003'

GET /topjava/rest/meals/filter?startDate=&endDate=&startTime=&endTime= - get filtered list of meals by date and time

    curl --location 'http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31&endDate=2020-01-31&startTime=10%3A35&endTime=22%3A00'

DELETE /topjava/rest/meals/{id} - delete meal with specified id

    curl --location --request DELETE 'http://localhost:8080/topjava/rest/meals/100003'

POST /topjava/rest/meals/ - create new meal

    curl --location 'http://localhost:8080/topjava/rest/meals/' \
    --header 'Content-Type: application/json' \
    --data \
    '{
        "dateTime": "2020-01-31T01:13:00",
        "description": "Очень ранний завтрак =)",
        "calories": 510
    }'

PUT /topjava/rest/meals/{id} - update meal with specified id

    curl --location --request PUT 'http://localhost:8080/topjava/rest/meals/100004' \
    --header 'Content-Type: application/json' \
    --data \
    '{
        "dateTime": "2020-01-31T10:35:00",
        "description": "123",
        "calories": 1500
    }'