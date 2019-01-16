
package parseurlexample;

import java.net.MalformedURLException;
import java.net.URL;

public class ParseURLExample {

    public static void main(String[] args) {
        // Create a URL
        try {             
            URL url = new URL("https://www.youtube.com/watch?v=VBXGvCvECIg");

            String protocol = url.getProtocol();
            String host = url.getHost();
            int port = url.getPort();
            String path = url.getPath();
            String query = url.getQuery();

            System.out.println("URL created: " + url);
            System.out.println("protocol: " + protocol);
            System.out.println("host:  " + host);
            System.out.println("port:  " + port);
            System.out.println("path:  " + path);
            System.out.println("query: " + query);
        }
        catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
    }
}
