package nlu.fit.leanhduc.view.component.fileChooser;

import nlu.fit.leanhduc.config.MetadataConfig;
import nlu.fit.leanhduc.view.section.DigitalSignatureSection;

import java.io.File;

public class FileChooserLoadKeySignature extends FileChooserButton implements FileChooserEvent {
    DigitalSignatureSection section;

    public FileChooserLoadKeySignature(DigitalSignatureSection section, String text) {
        super(text, MetadataConfig.getINSTANCE().getUploadIcon());
        this.section = section;
    }

    @Override
    public void onFileSelected(File file) {

    }

    @Override
    public void onFileUnselected() {

    }

    @Override
    public void onError(String message) {

    }
}
