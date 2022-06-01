package models;

import com.fasterxml.jackson.databind.node.ObjectNode;

public record Vertex(String id, String title, String doi) {
    public ObjectNode toJson() {
        ObjectNode node = new ObjectNode(null);
        node.put("id", id);
        node.put("title", title);
        node.put("doi", doi);
        return node;
    }
}
