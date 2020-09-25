package utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public final class Utils {

    /**
     * Retrieve a file from the resources folder.
     *
     * @param pathToFile The relative path of the file inside the resources folder.
     * @return An abstract representation of the requested file.
     * @throws IOException The file might not exist or fail to load.
     */
    public static URL getResource(String pathToFile) throws IOException {
        URL resource = Utils.class.getClassLoader().getResource(pathToFile);
        if (resource == null) {
            throw new IOException("File '" + pathToFile + "' not found in resources folder.");
        } else {
            return resource;
        }
    }
}
