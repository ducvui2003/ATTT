package nlu.fit.leanhduc.view.component.fileChooser;

import java.io.File;

public interface FileChooserEvent {
    default boolean onBeforeFileSelected() {
        return true;
    }

    void onFileSelected(File file);

    void onFileUnselected();

    void onError(String message);

}