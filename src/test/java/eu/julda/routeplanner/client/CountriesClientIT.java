package eu.julda.routeplanner.client;

import java.util.List;
import java.util.stream.Collectors;
import eu.julda.routeplanner.model.CountryDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for client receiving json with borders of countries
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"test", "IT"})
@Slf4j
class CountriesClientIT {

    @Autowired
    private CountriesClient countriesClient;


    @Test
    void getCountries() {
        //given
        List<String> expectedCountryAbbreviations = List.of("CZE","ITA", "AUT");

        //when
        List<CountryDto> countries =  countriesClient.getCountries();

        //then
        Assertions.assertThat(countries).isNotEmpty();
        Assertions.assertThat(countries)
                  .extracting(CountryDto::getCountryCode).containsAll(expectedCountryAbbreviations);
    }
}