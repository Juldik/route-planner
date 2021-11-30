package eu.julda.routeplanner.resource;

import java.util.List;
import eu.julda.routeplanner.model.RouteResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Final integration test for the whole scenario.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"test", "IT"})
@Slf4j
class RoutePlannerResourceIT {

    public static final String TEST_HOST = "http://localhost:";
    public static final String CALCULATION_PATH = "/routing/{origin}/{destination}";

    @LocalServerPort
    int randomServerPort;

    @Test
    void calculateRoute() {
        //given
        List<String> expectedPath = List.of("CZE", "AUT", "ITA");
        RestTemplate restTemplate = new RestTemplate();
        //when
        RouteResponseDto routeResponseDto =
                restTemplate.getForObject(getCalculationPath(), RouteResponseDto.class, "CZE", "ITA");
        //then
        assertThat(routeResponseDto.getRoute()).containsExactlyElementsOf(expectedPath);
    }

    @Test
    void calculateRoute_notFound() {
        //given + when + then
        assertThrows(HttpClientErrorException.class, () ->
                new RestTemplate().getForEntity(getCalculationPath(), Object.class, "CZE", "USA"));
    }

    private String getCalculationPath() {
        return TEST_HOST + randomServerPort + CALCULATION_PATH;
    }
}