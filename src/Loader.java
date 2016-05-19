import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matus on 20.03.2016.
 */
public class Loader {

    private InputStream is;
    private String file;

    public Loader(String file){
        this.file = file;
    }

    public List<String> load() throws IOException {
        BufferedReader br = null;
        String line;
        List<String> list = new ArrayList<String>();
        Path filePath = Paths.get(file);
        try{
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "UTF-8"));
            br.mark(4);
            if ('\ufeff' != br.read()) br.reset();
            while((line = br.readLine()) != null){
                list.add(line);
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return list;
    }
}