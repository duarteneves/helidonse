package pt.dneves.cloud.helidon.se;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;

public class ShopAny {

	private static final Logger logger = Logger.getLogger(ShopAny.class.getName());
	
	public static void main(String[] args) {
		
		
		Routing routing = Routing.builder()
	            .any((request, response) -> response.send("ShopAny response!" + "\n"))
	            .build();

		try {
			
			WebServer webServer = WebServer
			        .create(routing)
			        .start()
			        .toCompletableFuture()
			        .get(10, TimeUnit.SECONDS);
			
			logger.info("Server started at: http://localhost:" + webServer.port());
			
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			logger.severe("Error " + e.getMessage());
		}
		
	}
	
}
