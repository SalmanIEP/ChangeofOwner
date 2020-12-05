Feature: Change of owner Api Readiness probe
   #200
  Scenario:Success response
    Given Readiness health probe API
    When User consume the service to check Readiness status
    Then Current Readiness status is returned successfully

   #401
  Scenario: Authentication Error
    Given Readiness health probe API
    When User consume the service to check Readiness status with missing headers
    Then Authentication Error must be returned
      |code|detail|servicename|
      |code|detail|servicename|

    #403
  Scenario: Forbidden Error
    Given Readiness health probe API
    When  User having no permission consume the service to check Readiness status
    Then  Forbidden error must be returned
      |code|detail|servicename|
      |code|detail|servicename|

   #500
  Scenario: Internal Server Error
    Given Readiness health probe API
    When  User Consume the service with wrong endpoint
    Then  Internal Server error must be returned
      |code|detail|servicename|
      |code|detail|servicename|

