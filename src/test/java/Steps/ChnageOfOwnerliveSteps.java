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

public class ChnageOfOwnerliveSteps {
    Response response;
    String RequestUrl;
    endpoints.ServiceEndpoint service;
    @Given("Liveness health probe API")
    public void livenessHealthProbeAPI() {
        RequestUrl =service.getUrlEndpoint("save-address");
    }

    @When("User call the service to check live status")
    public void userCallTheServiceToCheckLiveStatus() throws Exception {
        Map<String,String> query=new HashMap<>();
        response = service.callHTTPGet("",query);
    }

    @Then("Current live status is returned successfully")
    public void currentLiveStatusIsReturnedSuccessfully() {
        String Response = response.body().asString();
        Assert.assertNotNull(Response);
        Assert.assertNotEquals(Response,"");
    }

    @When("User call the service to check live status with missing headers")
    public void userCallTheServiceToCheckLiveStatusWithMissingHeaders() {
        // call httpget method having missing headers
    }

    @Then("Authentication error must be returned")
    public void authenticationErrorMustBeReturned(List<Map<String, String>> ErrorDetails) {
        String code = response.path("code");
        String detail =response.path("detail");
        String serviceName = response.path("serviceName");
        assertThat((code),equalTo(ErrorDetails.get(0).get("error[0].code")));
        assertThat((detail),equalTo(ErrorDetails.get(0).get("error[0],detail")));
        assertThat((serviceName),equalTo(ErrorDetails.get(0).get("error[0].servicename")));
    }

    @When("User having no permission call the service to check live status")
    public void userHavingNoPermissionCallTheServiceToCheckLiveStatus() {
    }

    @Then("Forbidden Error must be returned")
    public void forbiddenErrorMustBeReturned(List<Map<String, String>> ErrorDetails) {
        String code = response.path("code");
        String detail =response.path("detail");
        String serviceName = response.path("serviceName");
        assertThat((code),equalTo(ErrorDetails.get(0).get("error[0].code")));
        assertThat((detail),equalTo(ErrorDetails.get(0).get("error[0],detail")));
        assertThat((serviceName),equalTo(ErrorDetails.get(0).get("error[0].servicename")));
    }

    @When("User COnsume the service with wrong endpoint")
    public void userCOnsumeTheServiceWithWrongEndpoint() {
        RequestUrl =service.getUrlEndpoint("save-address")+"wrong";
    }

    @Then("Internal Server Error must be returned")
    public void internalServerErrorMustBeReturned(List<Map<String, String>> ErrorDetails) {
        String code = response.path("code");
        String detail =response.path("detail");
        String serviceName = response.path("serviceName");
        assertThat((code),equalTo(ErrorDetails.get(0).get("error[0].code")));
        assertThat((detail),equalTo(ErrorDetails.get(0).get("error[0],detail")));
        assertThat((serviceName),equalTo(ErrorDetails.get(0).get("error[0].servicename")));
    }
}
