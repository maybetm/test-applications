package Application.Settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

abstract class AbstractSettings extends Properties {

    String path;
    FileInputStream fis;

    public void setPath (String path) {
        this.path = path;
    }

    @Override
    public String getProperty(String key) {
        try {
            fis = new FileInputStream(path);
            load(fis);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.getProperty(key);
    }


}
