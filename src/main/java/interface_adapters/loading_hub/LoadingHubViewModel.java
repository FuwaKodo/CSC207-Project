package interface_adapters.loading_hub;

import interface_adapters.ViewModel;

/**
 * The ViewModel for the LoadingHub View.
 */
public class LoadingHubViewModel extends ViewModel<LoadingHubState> {
    public static final String TITLE_LABEL = "Pentagon-113";

    public LoadingHubViewModel() {
        super("loading_hub");
        setState(new LoadingHubState());
    }
}
