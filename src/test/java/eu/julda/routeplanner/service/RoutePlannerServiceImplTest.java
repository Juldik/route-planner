package eu.julda.routeplanner.service;

import java.util.List;
import java.util.stream.Stream;
import eu.julda.routeplanner.client.CountriesClient;
import eu.julda.routeplanner.model.CountryDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Route planner service test
 */
@ExtendWith({MockitoExtension.class})
@ActiveProfiles(profiles = {"test"})
class RoutePlannerServiceImplTest {
    @Mock
    private CountriesClient countriesClient;

    @InjectMocks
    private RoutePlannerServiceImpl routePlannerService;

    @SuppressWarnings("unused") //used as method source
    static Stream<Arguments> calculateRoute() {
        return Stream.of(
                Arguments.of("CZE", "AUT", List.of("CZE", "AUT")), //neibours
                Arguments.of("CZE", "ITA", List.of("CZE", "AUT", "ITA")),
                Arguments.of("CZE", "CZE", List.of("CZE")), //staying in the same country
                Arguments.of("ITA", "CZE", List.of("ITA", "AUT", "CZE")) //testing directions opposite way
        );
    }

    @ParameterizedTest
    @MethodSource
    void calculateRoute(String origin, String destination, List<String> expectedPath) {
        //given
        Mockito.when(countriesClient.getCountries()).thenReturn(getDefaultCountries());

        //when
        List<String> path = routePlannerService.calculateRoute(origin, destination);

        //then
        assertThat(path).containsExactlyElementsOf(expectedPath);
    }

    @ParameterizedTest
    @CsvSource(value = {"CZE,ITX", //not existing destination
            "CZZ,ITA", //not existing origin
            "IKM,CLS", //not existing both
            "DEU,POL"})
        //not existing way
    void calculateRoute_notExistingOriginOrDestinationOrRoute(String origin, String destination) {
        //given
        Mockito.when(countriesClient.getCountries()).thenReturn(getDefaultCountries());

        //when + then
        assertThatThrownBy(() -> routePlannerService.calculateRoute(origin, destination));
    }

    private List<CountryDto> getDefaultCountries() {
        return List.of(
                new CountryDto("CZE", List.of("AUT", "POL", "SVK")),
                new CountryDto("AUT", List.of("ITA", "CZE")),
                new CountryDto("ITA", List.of("AUT")),
                new CountryDto("POL", List.of("CZE")),
                new CountryDto("SVK", List.of("CZE")),
                new CountryDto("DEU", List.of("USA")),
                new CountryDto("USA", List.of("DEU"))
        );
    }
}