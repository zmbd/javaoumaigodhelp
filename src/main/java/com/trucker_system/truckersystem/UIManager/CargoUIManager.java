package com.trucker_system.truckersystem.UIManager;

import com.trucker_system.truckersystem.model.Cargo;
import javafx.scene.control.ListView;

public class CargoUIManager {
    public void updateListViews(Cargo cargo, ListView<String> mainList, ListView<String> secondaryList) {
        if (cargo == null) {
            removeCargoFromMainList(mainList);
            return;
        }

        updateCargoInLists(cargo, mainList, secondaryList);
    }

    private void removeCargoFromMainList(ListView<String> mainList) {
        int selectedIndex = mainList.getSelectionModel().getSelectedIndex();
        mainList.getSelectionModel().clearSelection();
        mainList.getItems().remove(selectedIndex);
    }

    private void updateCargoInLists(Cargo cargo, ListView<String> mainList, ListView<String> secondaryList) {
        int selectedIndex = mainList.getSelectionModel().getSelectedIndex();

        if (canMoveToSecondaryList(cargo, secondaryList)) {
            moveCargoToSecondaryList(cargo, mainList, secondaryList, selectedIndex);
            return;
        }

        mainList.getItems().add(cargo.getClient());
    }

    private boolean canMoveToSecondaryList(Cargo cargo, ListView<String> secondaryList) {
        return secondaryList != null && cargo.getTrucker() != null;
    }

    private void moveCargoToSecondaryList(Cargo cargo, ListView<String> mainList,
                                          ListView<String> secondaryList, int selectedIndex) {
        secondaryList.getItems().add(cargo.getClient());
        mainList.getItems().remove(selectedIndex);
    }
}