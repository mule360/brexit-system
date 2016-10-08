#############################################################################
#Author:		ralph.masilamani@mule360.io
##
#############################################################################

Feature: Areas API
	AS-A-CLIENT:		Of the Areas API Rest Service
	I-WANT-TO:			Test the Rest Service methods
	SO-THAT-I:				Can verify the service implements the API

Scenario: GET /api/areas succeeds
Given The Areas Rest Service is running
When The service endpoint /api/areas  is called with the GET method
Then The service returns an HTTP response of 200
And The content type is "application/json"
And The content is a JSON array
And The content contains 399 values

Scenario: GET /api/areas/{identity} succeeds
Given The Areas Rest Service is running
And The URI parameter "identity" is "117"
When The service endpoint /api/areas/identity is called with the GET method
Then The service returns an HTTP response of 200
And The content type is "application/json"
And The content is a JSON object
And The Area object contains 
		|_class|io.dd.brexit.model.Area|
		|areaSize|21574981.58999999|
		|areaTypeCode|LBO|
		|areaTypeName|London borough|
		|country|England|
		|electorate|167820|
		|femalePopulation|141787|
		|gss|E09000030|
		|identity|117|
		|latitudeMaximum|51.544632740648865|
		|latitudeMiddle|51.515687169175585|
		|latitudeMinimum|51.484448635185025|
		|leaveVotes|35224|
		|longitudeMaximum|0.009886230454418358|
		|longitudeMiddle|-0.03463999651847128|
		|longitudeMinimum|-0.08016930267471568|
		|malePopulation|153449|
		|mapItId|2506|
		|name|Tower Hamlets Borough Council|
		|ons|00BG|
		|regionCode|E12000007|
		|regionName|London|
		|registered|108421|
		|remainVotes|73011|
		|totalPopulation|295236|
		|unitId|11185|
		|validVotes|108235|
