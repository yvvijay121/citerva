import controllers.CitationMapController;
import controllers.DOIController;
import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;
import models.Graph;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8000);
        app.get("/", ctx -> {
            throw new BadRequestResponse();
        });
        app.get("/doi/<id>", DOIController::retrieve);
        app.get("/graph/<id>", ctx -> {
            String id = ctx.pathParam("id");
            try {
                ctx.result(CitationMapController.createGraph(id));
            } catch (URISyntaxException | IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}