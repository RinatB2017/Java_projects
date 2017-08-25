package test;

import java.io.*;

public class TestRead {
    void test(String filename) throws FileNotFoundException, IOException {
        File flt = new File(filename);
        BufferedReader br = new BufferedReader (new InputStreamReader(new FileInputStream( flt ), "UTF-8"));
        String line;
        while((line = br.readLine()) != null)
        {
            System.out.println(line);
        }
        br.close();
    }
}
