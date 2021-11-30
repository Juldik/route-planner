package eu.julda.routeplanner.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Holding route calculation response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteResponseDto {
    private List<String> route;
}
