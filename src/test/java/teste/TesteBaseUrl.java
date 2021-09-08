package teste;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;

public class TesteBaseUrl {

    @BeforeAll
    public static void setup(){

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        baseURI = "https://reqres.in/api/";

        RestAssured.requestSpecification =  new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();

        RestAssured.responseSpecification =  new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }
}