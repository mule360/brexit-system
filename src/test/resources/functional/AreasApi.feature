#############################################################################
#Author: ralph.masilamani@mule360.io
##
#############################################################################

Feature: Areas API
	AS-A-CLIENT:		Of the Areas API Rest Service
	I-WANT-TO:			Test the Rest Service methods
	SO-THAT-I:				Can verify the service implements the API

Scenario: GET /api/areas succeeds
Given The Areas Rest Service is running
When The service is called with the GET method
Then The service returns an HTTP response of 200
And The content type is "application/json"
And The payload contains 399 values

