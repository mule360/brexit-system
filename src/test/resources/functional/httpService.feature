Feature: Http Service

Scenario: GET /test returns value test
Given The Http Service is running
When The service is called with a GET method
Then The service returns an HTTP response of 200
And The payload contains the value "Test"

