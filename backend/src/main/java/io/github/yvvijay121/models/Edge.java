package io.github.yvvijay121.models;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class Edge {
    private String id;
    private String sourceID;
    private String targetID;

    public Edge(String id, String sourceID, String targetID) {
        this.id = id;
        this.sourceID = sourceID;
        this.targetID = targetID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public String getTargetID() {
        return targetID;
    }

    public void setTargetID(String targetID) {
        this.targetID = targetID;
    }

    public ObjectNode toJson() {
        ObjectNode edge = new ObjectNode(null);
        edge.put("id", id);
        edge.put("source", sourceID);
        edge.put("target", targetID);
        return edge;
    }
}
