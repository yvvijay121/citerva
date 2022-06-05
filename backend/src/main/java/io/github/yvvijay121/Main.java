package io.github.yvvijay121;

import io.github.yvvijay121.controllers.CitationMapController;
import io.github.yvvijay121.controllers.DOIController;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8000);
        app.get("/", ctx -> ctx.json("please don't use this endpoint"));
        app.get("/doi/<id>", DOIController::retrieve);
        app.get("/graph/<id>", CitationMapController::retrieveGraph);
    }
}