package io.vertace.legacy.http;

import io.vertace.legacy.Vertace;
import io.vertace.legacy.core.VertaceException;
import io.vertace.legacy.core.factory.HttpRestRouterFactory;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public abstract class VertaceWeb extends Vertace {

    private String[] args;
    private Router router;
    private HttpServer httpServer;

    public String host() {
        return "localhost";
    }

    public int port() {
        return 1971;
    }

    @Override
    public void bootstrap(Future<Void> future) throws VertaceException {
        registerFactory(new HttpRestRouterFactory(this));
    }

    @Override
    protected void initialize(Future<Void> future) {
        router = Router.router(vertx);
        httpServer = vertx.createHttpServer().requestHandler(router::accept);
        super.initialize(future);
    }

    @Override
    protected void deploy(Future<Void> future) {
        httpServer = httpServer.listen(port(), host());
        System.out.println("Server running in port: " + port());
    }

    public HttpServer vertxHttpServer() {
        return httpServer;
    }

    public Router vertxRouter() {
        return router;
    }

}
