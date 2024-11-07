package nlu.fit.leanhduc.view.component.fileChooser;

import java.io.File;

public interface FileChooserEvent {
    boolean onBeforeFileSelected();

    void onFileSelected(File file);

    void onFileUnselected();

    void onError(String message);

    default void autoAddExtension(File file) {
        if (!file.getName().contains(".")) {
            file.renameTo(new File(file.getAbsolutePath() + ".txt"));
        }
    }
}
