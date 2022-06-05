package io.github.yvvijay121.models;

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

    public Vertex getRoot() {
        return root;
    }

    public void setRoot(Vertex root) {
        this.root = root;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public void setEdges(Set<Edge> edges) {
        this.edges = edges;
    }

    public Set<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(Set<Vertex> vertices) {
        this.vertices = vertices;
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
