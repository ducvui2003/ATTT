package nlu.fit.leanhduc.component.fileChooser;

import java.io.File;

public interface FileChooserEvent {
    void onFileSelected(File file);

    void onFileUnselected();

    void onError(String message);
}
