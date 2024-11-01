package nlu.fit.leanhduc.view.component.fileChooser;

import java.io.File;

public interface FileChooserEvent {
    void onFileSelected(File file);

    void onFileUnselected();

    void onError(String message);
}
