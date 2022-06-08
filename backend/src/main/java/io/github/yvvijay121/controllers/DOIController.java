package io.github.yvvijay121.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.http.Context;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static io.github.yvvijay121.Main.EMAIL;

public class DOIController {

    // create a new method that retrieves the data from the OpenAlex API
    // and returns it as an HttpResponse
    public static HttpResponse<String> getOpenAlexObject(String doi) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.openalex.org/works/doi:" + doi))
                .header("User-Agent", "mailto:" + EMAIL)
                .GET()
                .build();
        return client.send(request, BodyHandlers.ofString());
    }

    // create a new method that retrieves the data from the Unpaywall API
    // and returns it as an HttpResponse
    public static HttpResponse<String> getUnpaywallObject(String doi) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.unpaywall.org/v2/" + doi + "?email=" + EMAIL))
                .GET()
                .build();
        return client.send(request, BodyHandlers.ofString());
    }

    public static void retrieve(Context ctx) {
        String doi = ctx.pathParam("id");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpResponse<String> openAlex = getOpenAlexObject(doi);
            HttpResponse<String> unpaywall = getUnpaywallObject(doi);
            ObjectNode openAlexJson = objectMapper.readTree(openAlex.body()).deepCopy();
            // Modification 1. Convert the inverted index to the original abstract, and add the abstract to the JSON
            JsonNode articleAbstract = openAlexJson.get("abstract_inverted_index");
            Map<String, List<Integer>> result = objectMapper.convertValue(articleAbstract, new TypeReference<>() {
            });
            String articleAbstractString = convertInvertedIndexToString(result);
            // Modification 2. Add the open access information to the JSON
            JsonNode unpaywallJson = objectMapper.readTree(unpaywall.body());
            openAlexJson.put("abstract", articleAbstractString);
            openAlexJson.remove("abstract_inverted_index");
            openAlexJson.set("unpaywall", unpaywallJson);
            openAlexJson.remove("open_access");

            ctx.json(openAlexJson);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            ctx.result(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private static String convertInvertedIndexToString(Map<String, List<Integer>> result) {
        TreeMap<Integer, String> map = new TreeMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, List<Integer>> entry : result.entrySet()) {
            for (Integer integer : entry.getValue()) {
                map.put(integer, entry.getKey());
            }
        }
        for (int i = 0; i < map.size(); i++) {
            stringBuilder.append(map.get(i));
            if (i < map.size() - 1) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }
}
