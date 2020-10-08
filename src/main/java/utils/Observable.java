package utils;

import java.util.ArrayList;

/**
 * Une classe ce définissant comme observable, peut notifier les instances qui l'observe lors de changements.
 *
 * @param <T> le type de cette class
 */
public abstract class Observable<T> {
    // TODO maybe use a thread to dispatch events ?
    /**
     * La liste des observers surveillant cette classe
     */
    private final ArrayList<Observer<T>> observers;

    private T old;

    public Observable() {
        this.observers = new ArrayList<>();
        this.old = (T) this;
    }

    /**
     * Ajoute un observer à la liste afin d'être notifier en cas de changement.
     *
     * @param o l'instance d'observer
     */
    public void observe(Observer<T> o) {
        observers.add(o);
    }

    /**
     * Notifie les observers que la classe à changer.
     * L'appel de cette fonction ce fait à la description de la classe qui ce dis Observable
     */
    @SuppressWarnings("unchecked")
    protected void notifyObservers() {
        for (Observer<T> o : observers) {
            o.update(old, (T)this);
        }
        old = (T) this;
    }
}
