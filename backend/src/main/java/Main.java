import controllers.CitationMapController;
import controllers.DOIController;
import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8000);
        app.get("/", ctx -> {
            throw new BadRequestResponse();
        });
        app.get("/doi/<id>", DOIController::retrieve);
        app.get("/graph/<id>", CitationMapController::retrieveGraph);
    }
}