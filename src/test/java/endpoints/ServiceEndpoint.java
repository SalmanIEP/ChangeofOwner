package endpoints;

import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;

import java.util.HashMap;
import java.util.Map;
public class ServiceEndpoint {
    private static final String CHANGE_OWNER = "/ipo/changeofowner/cases";
    private static final String LIVE = "/health/live";
    private static final String READY = "/health/ready";
    EnvironmentVariables environmentVariables;

    String urlEndpoint;

    public String getUrlEndpoint(String endpointRequired) {
        if (endpointRequired.equalsIgnoreCase("Change-Of-Owner")){
            urlEndpoint = urlForEndpoint(CHANGE_OWNER);
        }
        else  if (endpointRequired.equalsIgnoreCase("live")){
            urlEndpoint = urlForEndpoint(LIVE);
        }
        else  if (endpointRequired.equalsIgnoreCase("ready")){
            urlEndpoint = urlForEndpoint(READY);
        }
        return urlEndpoint;
    }

    private String apiBase(){
        return EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("IPO-Change-Of-Owner-API.base");
    }

    private String urlForEndpoint (String endpoint) {
        return apiBase()+ endpoint ;
    }

    public Response callHTTPPost(String endPoint,String state){
        Response response;
        response = SerenityRest.given().relaxedHTTPSValidation().contentType("application/json").header("Content-Type", "application/json").headers(getMandatoryIPOHeaders()).header("Accept","*/*").body(state).post(endPoint);
        return response;
    }

    public Response callHTTPGet(String endPoint, Map<String,String> query) throws Exception{

        String server = null;
        int port = 0 ;
        Response response;
        String useProxy;
        String clientId;
        String clientSecret;
        useProxy = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("IPO-Change-Of-Owner-API.useProxy");
        clientId = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("IPO-Change-Of-Owner-API.X-IBM-Client-Id");
        clientSecret = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("IPO-Change-Of-Owner-API.X-IBM-Client-Secret");
        if (useProxy.equals("yes")) {
            SerenityRest.reset();
            server = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("IPO-Change-Of-Owner-API.server");
            port= Integer.parseInt(EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("IPO-Change-Of-Owner-API.port"));
            System.out.println(server);
            System.out.println(port);
            System.out.println(endPoint);
            SerenityRest.proxy(server,port);
            SerenityRest.setDefaultProxy(server,port);
            System.out.println("Endpoint : "+endPoint);
            System.out.println("queryParams : "+query);
            //  response = SerenityRest.given().proxy(server,port).relaxedHTTPSValidation().queryParams(query).get(endPoint);
            response = SerenityRest.given().proxy(server,port).relaxedHTTPSValidation().header("X-IBM-Client-Id",clientId).headers(getMandatoryIPOHeaders()).header("X-IBM-Client-Secret",clientSecret).queryParams(query).get(endPoint);
            //  System.out.println("Response : "+response.body());
            System.out.println("Response : "+response.statusCode());
            return response;


        }else
        {
            System.out.println("Endpoint : "+endPoint);
            System.out.println("queryParams : "+query);
            //response = SerenityRest.given().relaxedHTTPSValidation().queryParams(query).get(endPoint);
            response = SerenityRest.given().relaxedHTTPSValidation().header("X-IBM-Client-Id",clientId).headers(getMandatoryIPOHeaders()).header("X-IBM-Client-Secret",clientSecret).queryParams(query).get(endPoint);
            return response;
        }
    }

    private Map<String, String> getMandatoryIPOHeaders() {
        Map<String, String> ipoHeaders = new HashMap<>();
        ipoHeaders.put("X-Ipo-Business-Tran-Id", "TransId-FindAddress");
        ipoHeaders.put("X-Ipo-Domain", "AddressLookup");
        ipoHeaders.put("X-Ipo-Reference-Id", "8ef28a00-80da-4726-8373-16aa5177999b");
        ipoHeaders.put("X-Ipo-Date-Time", "2018-10-27T10:46:21.000Z");
        return ipoHeaders;
    }
}