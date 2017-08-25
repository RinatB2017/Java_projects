
package test;

import java.io.*;

public class TestWrite {
    
    void test(String filename) throws FileNotFoundException, IOException {
            File flt = new File(filename);
            FileWriter fw = new FileWriter(flt);

            fw.append("1\n");
            fw.append("2\n");
            fw.append("3\n");
            fw.flush();
            fw.close();
            
            fw.close();
    }
}
