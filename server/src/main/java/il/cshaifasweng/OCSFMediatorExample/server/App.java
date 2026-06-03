package il.cshaifasweng.OCSFMediatorExample.server;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
	
	private static SimpleServer server;
    public static void main( String[] args ) throws IOException
    {
        int port = 3000;
        server = new SimpleServer(port);

        System.out.println("Starting server on port " + port + "...");
        server.listen();
        System.out.println("Server is now listening!");
    }
}
