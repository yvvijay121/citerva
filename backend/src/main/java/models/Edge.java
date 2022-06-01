package models;

import com.fasterxml.jackson.databind.node.ObjectNode;

public record Edge(String id, String sourceID, String targetID) {
    public ObjectNode toJson() {
        ObjectNode edge = new ObjectNode(null);
        edge.put("id", id);
        edge.put("source", sourceID);
        edge.put("target", targetID);
        return edge;
    }
}
