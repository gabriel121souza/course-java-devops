package br.com.spacegtech.integrationtests.swagger;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.spacegtech.config.TestConfigs;
import br.com.spacegtech.integrationtests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest  extends AbstractIntegrationTest {

    @Test
    @DisplayName("testShouldDisplaySwagger_UI_Page")
    void testShouldDisplaySwaggerUiPage(){
       var content = given()
                .basePath("/swagger-ui/index.html")
                .port(TestConfigs.SERVER_PORT)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

       assertTrue(content.contains("Swagger UI"));
    }
}
