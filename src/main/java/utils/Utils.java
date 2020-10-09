package utils;

import java.io.IOException;
import java.net.URL;

/**
 * Class contenant des fonctions utilitaires.
 */
public final class Utils {

    /**
     * Récupère un fichier depuis le dossier resources.§
     *
     * @param pathToFile le chemin relatif du fichier dans le dossier resources
     * @return une instance d'{@link URL} pointant vers le fichier
     * @throws IOException si le fichier n'existe pas
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
