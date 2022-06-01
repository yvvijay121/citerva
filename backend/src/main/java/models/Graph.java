package models;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashSet;
import java.util.Set;

public class Graph {
    private Vertex root;
    private Set<Edge> edges;
    private Set<Vertex> vertices;

    public Graph(Vertex root) {
        this.root = root;
        this.vertices = new HashSet<>();
        this.edges = new HashSet<>();
        this.vertices.add(root);
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public ObjectNode toJson() {
        ObjectNode graphJson = new ObjectNode(null);
        graphJson.set("root", root.toJson());
        graphJson.put("vertices", vertices.toString());
        graphJson.put("edges", edges.toString());
        return graphJson;
    }
}
