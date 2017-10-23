package test6;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import sun.rmi.runtime.Log;

public class Test6 {

    public static void main(String[] args) {
        try {
            URL url = new URL("http://192.168.0.1");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            //con.setRequestMethod("POST");
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Charset", "UTF-8");
            con.connect();

            System.out.println("Output:");
            System.out.println("getResponseMessage [" + con.getResponseMessage() + "]");

            BufferedReader reader= new BufferedReader(new InputStreamReader(con.getInputStream()));
            
            System.out.println("Begin");
            String line = null;
            while ((line=reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("End");
        }
        catch (MalformedURLException e)
        {
            System.err.println(e.toString());
        }
        catch (ProtocolException e)
        {
            System.err.println(e.toString());
        }
        catch (IOException e)
        {
            System.err.println(e.toString());
        }
    }
    
}
