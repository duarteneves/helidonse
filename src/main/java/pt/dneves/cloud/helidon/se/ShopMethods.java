package pt.dneves.cloud.helidon.se;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import io.helidon.common.http.Http;
import io.helidon.webserver.Handler;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;
import pt.dneves.cloud.helidon.se.manager.ProdutctManager;
import pt.dneves.cloud.helidon.se.model.Product;

public class ShopMethods {

	private static final Logger logger = Logger.getLogger(ShopMethods.class.getName());
	
	
	public static void main(String[] args) {
		
		
		Handler handlerGetProduct = (request, response) -> {
			
			int id = Integer.parseInt(request.path().param("id"));
			Product product = ProdutctManager.getProduct(id);
			
			response.status(Http.Status.OK_200);
			response.headers().put("Content-Type", "text/plain; charset=UTF-8");
			response.send(product.toString());
			
		};
		
		Routing routing = Routing.builder()
	            .get("/product/{id}", handlerGetProduct)
	            // .post("/product", handlerPostProduct )
	            // .put("/product/{id}", handlerPutProduct) 
	            .build();

		
		try {
			
			WebServer webServer = WebServer
			        .create(routing)
			        .start()
			        .toCompletableFuture()
			        .get(10, TimeUnit.SECONDS);
			
			
			logger.info("Server started at: http://localhost:" + webServer.port());
			logger.info(webServer.isRunning() ? "Server is running!" : "Server is not running!");
			
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			logger.severe("Error " + e.getMessage());
		}
		
	}
	
}
