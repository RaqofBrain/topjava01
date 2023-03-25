curl --location 'http://localhost:8080/topjava/rest/meals'

curl --location 'http://localhost:8080/topjava/rest/meals/100003'

curl --location --request GET 'http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31&endDate=2020-01-31&startTime=10%3A35&endTime=22%3A00' \
--header 'Content-Type: application/javascript' \
--data '{
"id": 100003,
"dateTime": "2020-01-31T10:35:00",
"description": "123",
"calories": 1500
}'

curl --location --request DELETE 'http://localhost:8080/topjava/rest/meals/100003'

curl --location 'http://localhost:8080/topjava/rest/meals/' \
--header 'Content-Type: application/json' \
--data '{
"dateTime": "2020-01-31T01:13:00",
"description": "привет",
"calories": 510
}'

curl --location --request PUT 'http://localhost:8080/topjava/rest/meals/100004' \
--header 'Content-Type: application/json' \
--data '{
"dateTime": "2020-01-31T10:35:00",
"description": "123",
"calories": 1500
}'