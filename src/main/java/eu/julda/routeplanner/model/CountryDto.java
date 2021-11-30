package eu.julda.routeplanner.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Hold necessary information about country.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {
    @JsonProperty("cca3")
    private String countryCode;
    private List<String> borders;
}
