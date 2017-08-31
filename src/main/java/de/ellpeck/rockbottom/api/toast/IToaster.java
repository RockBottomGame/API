package de.ellpeck.rockbottom.api.toast;

public interface IToaster{

    void displayToast(Toast toast);

    void cancelToast(Toast toast);

    void cancelAllToasts();
}
