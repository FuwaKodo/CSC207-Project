package interface_adapters.favoritesIA;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Manages the view model for favorite stocks, handling state and
 * notifying listeners of changes.
 */
public class FavoriteStockViewModel {
    /** Event name for favorite stocks state changes. */
    public static final String FAVORITES_CHANGED = "FAVORITES_CHANGED";

    /** Supports property change notifications. */
    private final PropertyChangeSupport support;

    /** Current state of favorite stocks. */
    private FavoriteStockState state;

    /**
     * Initializes a new FavoriteStockViewModel with an empty state.
     */
    public FavoriteStockViewModel() {
        this.support = new PropertyChangeSupport(this);
        this.state = new FavoriteStockState();
    }

    /**
     * Updates the current favorite stocks state.
     *
     * @param state New state to set
     */
    public void setState(FavoriteStockState state) {
        this.state = state;
    }

    /**
     * Retrieves the current favorite stocks state.
     *
     * @return Current state
     */
    public FavoriteStockState getState() {
        return state;
    }

    /**
     * Notifies listeners that the favorite stocks state has changed.
     */
    public void firePropertyChanged() {
        support.firePropertyChange(FAVORITES_CHANGED, null, this.state);
    }

    /**
     * Adds a listener to receive state change notifications.
     *
     * @param listener Listener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
