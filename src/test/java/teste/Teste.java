package teste;

import dominio.Usuario;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class Teste extends TesteBaseUrl{

    String USERS_ENDPOINT = "users";
    String RECURSOS_ENDPOINT = "unknown";

    @Test
    public void testeListarTodosOsUsuarios(){

        RestAssured.given()
        .when()
                .get(USERS_ENDPOINT)
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body( matchesJsonSchemaInClasspath("listarUsuarios.json"));
    }

    @Test
    public void testJsonSchema() {
        RestAssured.given()
                .when()
                .queryParam("page", 2)
                    .get("https://reqres.in/api/users")
                .then()
                    .assertThat()
                    .statusCode(200)
                .body( matchesJsonSchemaInClasspath("testeJasonSchema.json"));

    }

    @Test
    public void testeListarUsuariosDeUmaPaginaEspecifica(){

        String query = "2";

        given()
                .queryParam("page", query)
                .log().all()
        .when()
                .get(USERS_ENDPOINT)
       .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("page", is(2));
    }

    @Test
    public void testeListarUsuariosEspecifico(){

        int path = 2;
        Usuario usr = given()
                .pathParam("id", path)
                .log().all()
        .when()
                .get(USERS_ENDPOINT)
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
        .extract()
                .body().jsonPath().getObject("data", Usuario.class);

        assertThat(usr.getId(), is(2));
        assertThat(usr.getEmail(), containsString("@reqres.in"));
        assertThat(usr.getFirstName(), is("Janet"));
        assertThat(usr.getLastName(), is("Weaver"));
        assertThat(usr.getAvatar(), is(notNullValue()));

    }

    @Test
    public void testeUsuarioNaoEncontrado(){

        int path = 23;
        given()
                .pathParam("id", path)
                .log().all()
        .when()
                .get(USERS_ENDPOINT)
        .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void testeRecursoTodosUsuariosPaginaPadrao() {

        given()
                .log().all()
        .when()
                .get(RECURSOS_ENDPOINT)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data.size()", is(6));
    }

    @Test
    public void testeRecursoDeUmUsuariosEspecifico() {

        given()
                .log().all()
                .when()
                .get(RECURSOS_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data.size()", is(6));
    }

}
