package interface_adapters;

/**
 * A class for displaying the name of the current view in String. It is "" by default.
 */
public class ViewManagerModel extends ViewModel<String> {

    public ViewManagerModel() {
        super("View Manager");
        setState("");
    }
}
