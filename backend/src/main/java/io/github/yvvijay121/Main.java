package io.github.yvvijay121;

import io.github.yvvijay121.controllers.CitationMapController;
import io.github.yvvijay121.controllers.DOIController;
import io.javalin.Javalin;

public class Main {
    public static String EMAIL = "yvvijay121@gmail.com";

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);
        app.before(ctx -> System.out.println("Request: " + ctx.req.getRequestURI()));
        app.get("/", ctx -> ctx.status(400).result("Please do not use this endpoint directly."));
        app.get("/doi/<id>", DOIController::retrieve);
        app.get("/graph/<id>", CitationMapController::retrieveGraph);
    }
}