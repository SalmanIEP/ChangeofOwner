package Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import templates.FieldValues;
import templates.MergeFrom;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CaseSteps {
    Response response;
    String CreateCaseUrl;
    String state;
    endpoints.ServiceEndpoint service;
    @Given("a case request contains the following details:")
    public void aCaseRequestContainsTheFollowingDetails(List<Map<String, String>> CaseDetails) throws IOException {
        service  = new endpoints.ServiceEndpoint();
        CreateCaseUrl = service.getUrlEndpoint("save-address");
        state = MergeFrom.template("templates/Body_ChnageofOwner.json")
                .withDefaultValuesFrom(FieldValues.in("templates/changeofOwner.properties"))
                .withFieldsFrom(CaseDetails.get(0));

    }

    @When("the request is sent")
    public void theRequestIsSent() {

        response = service.callHTTPPost(CreateCaseUrl, state);
    }

    @Then("a case is created successfully")
    public void aCaseIsCreatedSuccessfully() {
        assertThat((response.getStatusCode()),equalTo(201));
        String caseTitle = response.path("caseTitle");
        String caseId =response.path("caseId");
        String caseFolderId = response.path("caseFolderId");
        Assert.assertNotNull(caseTitle);
        Assert.assertNotNull(caseId);
        Assert.assertNotNull(caseFolderId);
    }

    @Then("missing parameter error validation is displayed")
    public void missingParameterErrorValidationIsDisplayed(List<Map<String, String>> ErrorDetails) {
        assertThat((response.getStatusCode()),equalTo(400));
        String code = response.path("code");
        String detail =response.path("detail");
        String serviceName = response.path("serviceName");
        assertThat((code),equalTo(ErrorDetails.get(0).get("code")));
        assertThat((detail),equalTo(ErrorDetails.get(0).get("detail")));
        assertThat((serviceName),equalTo(ErrorDetails.get(0).get("servicename")));

    }

    @Then("case type not found message is displayed")
    public void caseTypeNotFoundMessageIsDisplayed(List<Map<String, String>> ErrorDetails) {
        assertThat((response.getStatusCode()),equalTo(404));
        String code = response.path("code");
        String detail =response.path("detail");
        String serviceName = response.path("serviceName");
        assertThat((code),equalTo(ErrorDetails.get(0).get("code")));
        assertThat((detail),equalTo(ErrorDetails.get(0).get("detail")));
        assertThat((serviceName),equalTo(ErrorDetails.get(0).get("servicename")));
    }

    @Then("invalid parameter error validation is displayed")
    public void invalidParameterErrorValidationIsDisplayed(List<Map<String, String>> ErrorDetails) {
        assertThat((response.getStatusCode()),equalTo(404));
        String code = response.path("code");
        String detail =response.path("detail");
        String serviceName = response.path("serviceName");
        assertThat((code),equalTo(ErrorDetails.get(0).get("code")));
        assertThat((detail),equalTo(ErrorDetails.get(0).get("detail")));
        assertThat((serviceName),equalTo(ErrorDetails.get(0).get("servicename")));
    }
}
