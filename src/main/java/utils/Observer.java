package utils;

/**
 * Contract permettant à une classe d'observer les changements d'une instance.
 *
 * @param <T> le type des instances que l'on observera.
 */
public interface Observer<T> {
    /**
     * Fonction appeler lors ce que l'objet est mise à jour.
     *
     * @param o l'objet en question.
     */
    void update(T o);
}
