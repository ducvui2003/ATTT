package nlu.fit.leanhduc.view.component.panel.text;

public interface PanelTextHandlerEvent {
    String onEncrypt(String plainText);

    String onDecrypt(String cipherText);
}
