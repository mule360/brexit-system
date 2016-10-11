Feature: Calculator

Scenario: Calculator Addition
Given The calculator is running
	And The first number is 123
	And The second number is 200
When I call the add function
Then The calculator returns 323
