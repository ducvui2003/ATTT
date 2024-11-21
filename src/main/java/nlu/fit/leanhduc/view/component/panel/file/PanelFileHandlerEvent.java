package nlu.fit.leanhduc.view.component.panel.file;

public interface PanelFileHandlerEvent {
    void onEncryptFile(String src, String dest);

    void onDecryptFile(String src, String dest);
}
