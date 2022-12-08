package pt.dneves.cloud.helidon.se;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import io.helidon.common.http.Http;
import io.helidon.config.Config;
import io.helidon.webserver.Handler;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;
import pt.dneves.cloud.helidon.se.manager.ProdutctManager;
import pt.dneves.cloud.helidon.se.model.Product;

public class ShopConfig {

	private static final Logger logger = Logger.getLogger(ShopConfig.class.getName());
	
	
	public static void main(String[] args) {
		
		
		// handlers for routing
		
		Handler handlerGetProduct = (request, response) -> {
			
			int id = Integer.parseInt(request.path().param("id"));
			Product product = ProdutctManager.getProduct(id);
			
			response.status(Http.Status.OK_200);
			response.headers().put("Content-Type", "text/plain; charset=UTF-8");
			response.send(product.toString());
			
		};
		

		// routing
		
		Routing routing = Routing.builder()
				.get("/product/{id}", handlerGetProduct)
	            .build();
		

		// configuration
		
        // load the default configuration using the create() method.
        Config config = Config.create();

		
		// web server
		
		try {
			
			WebServer webServer = WebServer.builder(routing)
					.config(config.get("server"))
					.build();

			webServer.start()
			        .toCompletableFuture()
			        .get(10, TimeUnit.SECONDS);
			
			logger.info("INFO: Server started at: http://localhost:" + webServer.port());
			
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			logger.severe("Error " + e.getMessage());
		}
		
	}
	
}
