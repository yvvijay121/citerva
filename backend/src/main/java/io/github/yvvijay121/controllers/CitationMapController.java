package io.github.yvvijay121.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.github.yvvijay121.models.Graph;
import io.github.yvvijay121.models.Vertex;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.yvvijay121.Main.EMAIL;

public class CitationMapController {
    public static void retrieveGraph(Context ctx) throws URISyntaxException, IOException, InterruptedException {
        // get the root object from the DOI
        String id = ctx.pathParam("id");
        HttpResponse<String> a = DOIController.getOpenAlexWorkObject(id);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(a.body());

        // create the root vertex
        String rootID = json.get("id").asText().replace("https://openalex.org/", "");
        String rootTitle = json.get("title").asText();
        String rootDoi = json.get("doi").asText();
        Vertex rootVertex = new Vertex(rootID, rootTitle, rootDoi);

        // create the graph
        Graph graph = new Graph(rootVertex);

        // get the cited articles
        JsonNode citedArticles = json.get("referenced_works");
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();

        String citedArticlesString = objectMapper.convertValue(citedArticles,
                new TypeReference<List<String>>() {
                })
                .stream()
                .map(s -> s.replace("https://openalex.org/", ""))
                .collect(Collectors.joining("%7C"));

        String citedArticlesURI = "https://api.openalex.org/works?filter=openalex:" + citedArticlesString
                + "&per-page=50";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(citedArticlesURI))
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode citedArticlesJson = objectMapper.readTree(response.body());

        for(JsonNode articleNode : citedArticlesJson.get("results")){
            String nodeid = articleNode.get("id").asText().replace("https://openalex.org/", "");
            String nodetitle = articleNode.get("title").asText();
            String nodedoi = articleNode.get("doi").asText();
            Vertex nodevertex = new Vertex(nodeid, nodetitle, nodedoi);
            graph.addVertex(nodevertex);
        }
    }
}
