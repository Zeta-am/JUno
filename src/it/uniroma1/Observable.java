package it.uniroma1;

/**
 * * This class represents an observable object, or "data"
 * in the model-view paradigm. It can be subclassed to represent an
 * object that the application wants to have observed.
 * <p>
 * An observable object can have one or more observers. An observer
 * may be any object that implements interface {@code Observer}. After an
 * observable instance changes, an application calling the
 * {@code Observable}'s {@code notifyObservers} method
 * causes all of its observers to be notified of the change by a call
 * to their {@code update} method.
 * @author Andrea Musolino
 */
public interface Observable {
    void addObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObserver();
}
