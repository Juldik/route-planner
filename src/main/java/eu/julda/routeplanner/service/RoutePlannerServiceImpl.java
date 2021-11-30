package eu.julda.routeplanner.service;

import java.util.List;
import eu.julda.routeplanner.NoRouteFoundException;
import eu.julda.routeplanner.client.CountriesClient;
import eu.julda.routeplanner.model.CountryDto;
import lombok.RequiredArgsConstructor;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.stereotype.Service;

/**
 *
 */
@RequiredArgsConstructor
@Service
public class RoutePlannerServiceImpl implements RoutePlannerService {

    private final CountriesClient countriesClient;

    @Override
    public List<String> calculateRoute(String origin, String destination) {
        Graph<String, DefaultEdge> countriesGraph = constructGraph();
        if (!countriesGraph.containsVertex(origin) || !countriesGraph.containsVertex(destination)){
            throw new NoRouteFoundException(origin, destination);
        }
        DijkstraShortestPath<String, DefaultEdge> dijkstraShortestPath = new DijkstraShortestPath<>(countriesGraph);
        SingleSourcePaths<String, DefaultEdge> originPaths = dijkstraShortestPath.getPaths(origin);
        GraphPath<String, DefaultEdge> calculatedPath = originPaths.getPath(destination);

        if (calculatedPath == null) {
            throw new NoRouteFoundException(origin, destination);
        }
        return calculatedPath.getVertexList();
    }

    private Graph<String, DefaultEdge> constructGraph() {
        List<CountryDto> countriesWithBorders = countriesClient.getCountries();
        Graph<String, DefaultEdge> countriesGraph = new DefaultDirectedGraph<>(DefaultEdge.class);

        countriesWithBorders.forEach(c -> countriesGraph.addVertex(c.getCountryCode()));
        countriesWithBorders.forEach(origin -> origin.getBorders().forEach(
                destination -> countriesGraph.addEdge(origin.getCountryCode(), destination)));
        return countriesGraph;
    }

}
