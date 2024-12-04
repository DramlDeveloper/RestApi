package rest.api.rest_service.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    private PropertiesUtil(){
        throw new RuntimeException("This is a utility class and cannot be instantiated");
    }

    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties(){

        try(var inputStream = PropertiesUtil.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}