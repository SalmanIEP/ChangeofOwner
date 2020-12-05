package Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChangeOfownerReadySteps {

    Response response;
    String RequestUrl;
    endpoints.ServiceEndpoint service;
    @Given("Readiness health probe API")
    public void readinessHealthProbeAPI() {
        RequestUrl =service.getUrlEndpoint("save-address");
    }

    @When("User consume the service to check Readiness status")
    public void userConsumeTheServiceToCheckReadinessStatus() throws Exception {
        Map<String,String> query=new HashMap<>();
        response = service.callHTTPGet("",query);
    }

    @Then("Current Readiness status is returned successfully")
    public void currentReadinessStatusIsReturnedSuccessfully() {

        String Response = response.body().asString();
        Assert.assertNotNull(Response);
        Assert.assertNotEquals(Response,"");
    }

    @When("User consume the service to check Readiness status with missing headers")
    public void userConsumeTheServiceToCheckReadinessStatusWithMissingHeaders() {
      //Get Method with missing headers endpointclas
    }

    @Then("Authentication Error must be returned")
    public void authenticationErrorMustBeReturned(List<Map<String, String>> ErrorDetails) {
        String code = response.path("code");
        String detail =response.path("detail");
        String serviceName = response.path("serviceName");
        assertThat((code),equalTo(ErrorDetails.get(0).get("error[0].code")));
        assertThat((detail),equalTo(ErrorDetails.get(0).get("error[0],detail")));
        assertThat((serviceName),equalTo(ErrorDetails.get(0).get("error[0].servicename")));
    }

    @When("User having no permission consume the service to check Readiness status")
    public void userHavingNoPermissionConsumeTheServiceToCheckReadinessStatus() {
      //this need to confirm how actual api acts
    }

    @Then("Forbidden error must be returned")
    public void forbiddenErrorMustBeReturned(List<Map<String, String>> ErrorDetails) {
        String code = response.path("code");
        String detail =response.path("detail");
        String serviceName = response.path("serviceName");
        assertThat((code),equalTo(ErrorDetails.get(0).get("error[0].code")));
        assertThat((detail),equalTo(ErrorDetails.get(0).get("error[0],detail")));
        assertThat((serviceName),equalTo(ErrorDetails.get(0).get("error[0].servicename")));
    }

    @Then("Internal Server error must be returned")
    public void internalServerErrorMustBeReturned(List<Map<String, String>> ErrorDetails) {
        String code = response.path("code");
        String detail =response.path("detail");
        String serviceName = response.path("serviceName");
        assertThat((code),equalTo(ErrorDetails.get(0).get("error[0].code")));
        assertThat((detail),equalTo(ErrorDetails.get(0).get("error[0],detail")));
        assertThat((serviceName),equalTo(ErrorDetails.get(0).get("error[0].servicename")));
    }

    @When("User Consume the service with wrong endpoint")
    public void userConsumeTheServiceWithWrongEndpoint() {
        RequestUrl =service.getUrlEndpoint("save-address")+"wrong";
    }
}
