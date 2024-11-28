package nlu.fit.leanhduc.view.component.panel.text;

public interface PanelTextHandlerEvent {


    default boolean canEncrypt(String plainText) {
        return true;
    }

    default boolean canDecrypt(String cipherText) {
        return true;
    }

    String onEncrypt(String plainText);

    String onDecrypt(String cipherText);
}
