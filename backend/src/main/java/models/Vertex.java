package models;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class Vertex {
    private String id;
    private String title;
    private String doi;

    public Vertex(String id, String title, String doi) {
        this.id = id;
        this.title = title;
        this.doi = doi;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDoi() {
        return doi;
    }

    public ObjectNode toJson() {
        ObjectNode node = new ObjectNode(null);
        node.put("id", id);
        node.put("title", title);
        node.put("doi", doi);
        return node;
    }
}
