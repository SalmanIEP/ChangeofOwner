package endpoints;

import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import java.util.Map;
public class ServiceEndpoint {
    private static final String SAVE_ADDRESS = "/ipo/saveaddress/address";
    private static final String CONFIG_PROP = "IPO-Save-Address-API";
    EnvironmentVariables environmentVariables;
    String urlEndpoint;

    public String getUrlEndpoint(String endpointRequired) {
        if (endpointRequired.equalsIgnoreCase("save-address")) {
            urlEndpoint = urlForEndpoint(SAVE_ADDRESS);
        }
        return urlEndpoint;
    }

    private String apiBase() {
        return EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(CONFIG_PROP  + ".base");
    }

    private String urlForEndpoint(String endpoint) {
        return apiBase() + endpoint;
    }

    public Response callHTTPPost(String endPoint, String state) {
        String server = null;
        int port = 0;
        Response response;
        String useProxy;
        String clientId;
        String clientSecret ;
        useProxy = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(CONFIG_PROP + ".useProxy");
        clientId = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(CONFIG_PROP + ".X-IBM-Client-Id");
        clientSecret = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(CONFIG_PROP + ".X-IBM-Client-Secret");
        if (useProxy.equals("yes")) {
            SerenityRest.reset();
            server = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(CONFIG_PROP + ".server");
            port = Integer.parseInt(EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(CONFIG_PROP + ".port"));
            System.out.println("Proxy Server" + server);
            System.out.println("Proxy Port" + port);
            System.out.println("Request URL" + endPoint);
            SerenityRest.proxy(server, port);
            SerenityRest.setDefaultProxy(server, port);
            SerenityRest.enableLoggingOfRequestAndResponseIfValidationFails();
            response = SerenityRest.given().relaxedHTTPSValidation().header("X-IBM-Client-Id", clientId).header("X-IBM-Client-Secret", clientSecret).header("Content-Type", "application/json").header("Accept", "*/*").header("X-Ipo-Business-Tran-Id", "12345").header("X-Ipo-Reference-Id", "54321").header("X-Ipo-Domain", "Patents").header("X-Ipo-Date-Time", "2020-06-11T11:12:10.111Z").body(state).post(endPoint);
            System.out.println("Serenity Response body" + response.body().toString());
            System.out.println("Serenity Response status code" + response.statusCode());
            System.out.println("Serenity Response content type" + response.getContentType());
            System.out.println("Serenity Response content " + response.body().asString());
            return response;
        } else {
            System.out.println("Request URL" + endPoint);
            response = SerenityRest.given().relaxedHTTPSValidation().contentType("application/json").header("Content-Type", "application/json").header("Accept", "*/*").header("X-Ipo-Business-Tran-Id", "12345").header("X-Ipo-Reference-Id", "54321").header("X-Ipo-Domain", "Patents").header("X-Ipo-Date-Time", "2020-06-11T11:12:10.111Z").body(state).post(endPoint);
            System.out.println("Serenity Response body" + response.body().toString());
            System.out.println("Serenity Response status code" + response.statusCode());
            System.out.println("Serenity Response content type" + response.getContentType());
            System.out.println("Serenity Response content " + response.body().asString());
            return response;
        }
    }

    public Response callHTTPGet(String endPoint, Map<String, String> query) throws Exception {
        String server = null;
        int port = 0;
        Response response;
        String useProxy;
        useProxy = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(CONFIG_PROP + ".useProxy");
        if (useProxy.equals("yes")) {
            SerenityRest.reset();
            server = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(CONFIG_PROP + ".server");
            port = Integer.parseInt(EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(CONFIG_PROP + ".port"));
            System.out.println(server);
            System.out.println(port);
            System.out.println(endPoint);
            SerenityRest.proxy(server, port);
            SerenityRest.setDefaultProxy(server, port);
            response = SerenityRest.given().proxy(server, port).relaxedHTTPSValidation().queryParams(query).get(endPoint);
            return response;
        } else {
            System.out.println(endPoint);
            response = SerenityRest.given().relaxedHTTPSValidation().queryParams(query).get(endPoint);
            return response;
        }
    }
}