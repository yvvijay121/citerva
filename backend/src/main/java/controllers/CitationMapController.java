package controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
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
    public static void retrieveGraph(Context ctx) throws URISyntaxException, IOException, InterruptedException {
        String id = ctx.pathParam("id");
        HttpResponse<String> a = DOIController.getOpenAlexObject(id);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(a.body());
        String root = json.get("id").asText().replace("https://openalex.org/", "");
        String rootTitle = json.get("title").asText();
        String rootDoi = json.get("doi").asText();
        Vertex rootVertex = new Vertex(root, rootTitle, rootDoi);
        // get the cited articles from the object, and convert them to Stream<Vertex>
        Graph graph = new Graph(rootVertex);
        JsonNode citedArticles = json.get("referenced_works");
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
        objectMapper.convertValue(citedArticles, new TypeReference<List<String>>() {
        }).stream().map(s -> s.replace("https://openalex.org/", "")).map(s -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("https://api.openalex.org/works/" + s))
                        .GET()
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.uri());
                JsonNode jsonNode = objectMapper.readTree(response.body());
                return new Vertex(s, jsonNode.get("title").asText(), jsonNode.get("doi").asText());
            } catch (URISyntaxException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).forEach(v -> {
            graph.addVertex(v);
            graph.addEdge(new Edge("edge-" + UUID.randomUUID(), root, v.getId()));
        });
        ctx.json(graph);
    }
}
