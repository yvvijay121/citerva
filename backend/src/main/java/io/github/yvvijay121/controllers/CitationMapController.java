package io.github.yvvijay121.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.github.yvvijay121.models.Edge;
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
        String root = json.get("id").asText().replace("https://openalex.org/", "");
        String rootTitle = json.get("title").asText();
        String rootDoi = json.get("doi").asText();
        Vertex rootVertex = new Vertex(root, rootTitle, rootDoi);

        // get the cited articles from the object, and convert them to Stream<Vertex>
        Graph graph = new Graph(rootVertex);
        JsonNode citedArticles = json.get("referenced_works");
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();

        String citedArticlesString = objectMapper.convertValue(citedArticles, new TypeReference<List<String>>() {
        }).stream().map(s -> s.replace("https://openalex.org/", "")).collect(Collectors.joining("|"));

        ctx.json(citedArticlesString);
    }
}
