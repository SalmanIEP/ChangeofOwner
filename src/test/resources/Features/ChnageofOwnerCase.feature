Feature: Create Case

  Scenario Outline: Create a Case
    Given a case request contains the following details:
      | caseType   | newOwnerFirstName    | newOwnerLastName   | IPRightNumber   | businessDomain   | userId   |
      | <caseType> | <newOwnerFirstName> | <newOwnerLastName> | <IPRightNumber> | <businessDomain> | <userId> |
    When the request is sent
    Then a case is created successfully

    Examples:
      | caseType          | newOwnerFirstName  | newOwnerLastName  | IPRightNumber | businessDomain | userId |
      | ChangeOfOwnerCase | TestFirstNameOne   | TestLastNameOne   | 12345         | Patents        | 12345  |
      | ChangeOfOwnerCase | TestFirstNameTwo   | TestLastNameTwo   | 12345         | Designs        | 12345  |
      | ChangeOfOwnerCase | TestFirstNameThree | TestLastNameThree | 12345         | Trademarks     | 12345  |


  Scenario Outline: Create Case with missing parameter
    Given a case request contains the following details:
      | caseType   | newOwnerFirstName    | newOwnerLastName   | IPRightNumber   | businessDomain   | userId   |
      | <caseType> | <newOwnerFirstName> | <newOwnerLastName> | <IPRightNumber> | <businessDomain> | <userId> |
    When the request is sent
    Then missing parameter error validation is displayed
    |code|detail|servicename|
    |code|detail|servicename|

    Examples:
      | caseType          | newOwnerFirstName  | newOwnerLastName  | IPRightNumber | businessDomain | userId |
      | ChangeOfOwnerCase |                    | TestLastNameOne   | 12345         | Patents        | 12345  |
      | ChangeOfOwnerCase | TestFirstNameThree |                   | 12345         | Designs        | 12345  |
      | ChangeOfOwnerCase | TestFirstNameThree | TestLastNameOne   |               | Trademarks     | 12345  |
      | ChangeOfOwnerCase | TestFirstNameTwo   | TestLastNameTwo   | 12345         |                | 12345  |
      | ChangeOfOwnerCase | TestFirstNameThree | TestLastNameThree | 12345         | Trademarks     |        |



  Scenario Outline: Create Case with case type not found
    Given a case request contains the following details:
      | caseType   | newOwnerFirstName    | newOwnerLastName   | IPRightNumber   | businessDomain   | userId   |
      | <caseType> | <newOwnerFirstName> | <newOwnerLastName> | <IPRightNumber> | <businessDomain> | <userId> |
    When the request is sent
    Then case type not found message is displayed
      |code|detail|servicename|
      |code|detail|servicename|
    Examples:
      | caseType          | newOwnerFirstName  | newOwnerLastName | IPRightNumber | businessDomain | userId |
      | zxc               | TestFirstNameOne   | TestLastNameOne  | 12345         | Patents        | 12345  |

  Scenario Outline: Create Case with invalid parameters
    Given a case request contains the following details:
      | caseType   | newOwnerFirstName    | newOwnerLastName   | IPRightNumber   | businessDomain   | userId   |
      | <caseType> | <newOwnerFirstName > | <newOwnerLastName> | <IPRightNumber> | <businessDomain> | <userId> |
    When the request is sent
    Then invalid parameter error validation is displayed
      |code|detail|servicename|
      |code|detail|servicename|
    Examples:
      | caseType          | newOwnerFirstName  | newOwnerLastName | IPRightNumber | businessDomain | userId |
      | ChangeOfOwnerCase | TestFirstNameOne   | TestLastNameOne  | 12345         | Patents        | 12345  |
      | ChangeOfOwnerCase | 123                | TestLastNameTwo  | 12345         | Designs        | 12345  |
      | ChangeOfOwnerCase | TestFirstNameThree | 123              | 12345         | Trademarks     | 12345  |
      | ChangeOfOwnerCase | TestFirstNameThree | 123              | xyz           | Trademarks     | 12345  |
      | ChangeOfOwnerCase | TestFirstNameThree | 123              | 12345         | xyz            | 12345  |
      | ChangeOfOwnerCase | TestFirstNameThree | 123              | 12345         | Designs        | xyz    |