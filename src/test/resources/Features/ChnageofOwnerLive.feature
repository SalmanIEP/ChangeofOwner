Feature: Change of owner Api Live ness health probe
   #200
  Scenario:Success response
    Given Liveness health probe API
    When User call the service to check live status
    Then Current live status is returned successfully

       #200
  Scenario: Authentication Error
    Given Liveness health probe API
    When User call the service to check live status with missing headers
    Then Authentication error must be returned
      |code|detail|servicename|
      |code|detail|servicename|
    #403
  Scenario: Forbidden Error
    Given Liveness health probe API
    When  User having no permission call the service to check live status
    Then  Forbidden Error must be returned
      |code|detail|servicename|
      |code|detail|servicename|
  #500
  Scenario: Internal Server Error
    Given Liveness health probe API
    When  User COnsume the service with wrong endpoint
    Then  Internal Server Error must be returned
      |code|detail|servicename|
      |code|detail|servicename|