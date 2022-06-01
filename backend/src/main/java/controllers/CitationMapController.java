package controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Edge;
import models.Graph;
import models.Vertex;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

public class CitationMapController {
    // create a new method that retrieves the data from the OpenAlex API
    // and returns it as an HttpResponse
    public static Vertex getIDObject(String id) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.openalex.org/" + id))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.body());
        return new Vertex(jsonNode.get("id").asText(), jsonNode.get("title").asText(), jsonNode.get("doi").asText());
    }

    // create a new method that creates a graph from the cited articles from the object returned by the OpenAlex API
    public static String createGraph(String doi) throws URISyntaxException, IOException, InterruptedException {
        // get the object from the OpenAlex API
        HttpResponse<String> idObject = DOIController.getOpenAlexObject(doi);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(idObject.body());

        // create a new vertex for the root
        String root = json.get("id").asText();
        String rootTitle = json.get("title").asText();
        String rootDoi = json.get("doi").asText();
        Vertex rootVertex = new Vertex(root, rootTitle, rootDoi);

        // create a new graph
        Graph graph = new Graph(rootVertex);

        // get the cited articles from the object
        JsonNode citedArticles = json.get("referenced_works");

        // convert citedArticles to Stream<Vertex>
        // then for each Vertex in the Stream, create an Edge and add it to the graph; add the Vertex to the graph
        objectMapper
                .convertValue(citedArticles, new TypeReference<List<String>>() {
                })
                .stream()
                .map(s -> s.replace("https://openalex.org/", ""))
                .map(s -> {
                    try {
                        return getIDObject(s);
                    } catch (URISyntaxException | IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).forEach(v -> {
                    graph.addVertex(v);
                    graph.addEdge(new Edge("edge " + UUID.randomUUID(), root, v.id()));
                });

        // retrieve JSON from this url (https://api.openalex.org/works?filter=cites:W2015361846) and convert it to a JsonNode
//        HttpResponse<String> citedArticlesObject = DOIController.getOpenAlexObject("works?filter=cites:" + root);
//        JsonNode citedByArticles = objectMapper.readTree(citedArticlesObject.body()).get("results");
//
//        citedByArticles.iterator().forEachRemaining(
//                node -> {
//                    String openAlexID = node.get("id").asText();
//                    String title = node.get("title").asText();
//                    String articleDOID = node.get("doi").asText();
//                    graph.addVertex(new Vertex(openAlexID, title, articleDOID));
//                    graph.addEdge(new Edge("edge " + UUID.randomUUID(), openAlexID, root));
//                }
//        );

        return graph.toJson().toString();
    }

}
