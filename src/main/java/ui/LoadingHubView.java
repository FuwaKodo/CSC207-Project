package ui;

import interface_adapters.loading_hub.LoadingHubViewModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

/**
 * The View for the LoadingHub Use Case.
 */
public class LoadingHubView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "LoadingHubView";

    private final LoadingHubViewModel loadingHubViewModel;

    public LoadingHubView(LoadingHubViewModel loadingHubViewModel) {
        this.loadingHubViewModel = loadingHubViewModel;
        loadingHubViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(LoadingHubViewModel.TITLE_LABEL);
        title.setAlignmentX(CENTER_ALIGNMENT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
