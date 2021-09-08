package teste;

import dominio.Usuario;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class Teste extends TesteBaseUrl{

    String url = baseURI + basePath;
    String LISTAR_USUARIOS_ENDPOINT = "users";

    @Test
    public void testeListarTodosOsUsuarios(){

        given()
                .log().all()
        .when()
                .get(LISTAR_USUARIOS_ENDPOINT)
        .then()
                .log().all()
                .statusCode(200)
                .body("total", is(12), "per_page", is(6), "total_pages", is(2));
    }

    @Test
    public void testeListarUsuariosDeUmaPaginaEspecifica(){

        String query = "2";

        given()
                .queryParam("page", query)
                .log().all()
        .when()
                .get(LISTAR_USUARIOS_ENDPOINT)
       .then()
                .log().all()
                .statusCode(200)
                .body("page", is(2));
    }

    @Test
    public void testeListarUsuariosEspecifico(){

        int path = 2;
        Usuario usr = given()
                .basePath(LISTAR_USUARIOS_ENDPOINT)
                .pathParam("id", path)
                .log().all()
        .when()
                .get("{id}")
        .then()
                .log().all()
                .statusCode(200)
        .extract()
                .body().jsonPath().getObject("data", Usuario.class);

        assertThat(usr.getId(), is(2));
        assertThat(usr.getEmail(), containsString("@reqres.in"));
        assertThat(usr.getFirstName(), is("Janet"));
        assertThat(usr.getLastName(), is("Weaver"));
        assertThat(usr.getAvatar(), is(notNullValue()));

    }
}
