package io.github.yvvijay121;

import io.github.yvvijay121.controllers.CitationMapController;
import io.github.yvvijay121.controllers.DOIController;
import io.javalin.Javalin;

public class Main {
    public static String EMAIL = "yvvijay121@gmail.com";

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);
        app.get("/", ctx -> ctx.status(400).result("How did you even get here?"));
        app.get("/api/", ctx -> ctx.status(400).result("Please do not use this endpoint directly."));
        app.get("/api/doi/<id>", DOIController::retrieve);
        app.get("/api/graph/<id>", CitationMapController::retrieveGraph);
    }
}