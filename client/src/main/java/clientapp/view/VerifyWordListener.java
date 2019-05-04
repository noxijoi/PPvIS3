package clientapp.view;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

public class VerifyWordListener implements VerifyListener {
    @Override
    public void verifyText(VerifyEvent verifyEvent) {
        verifyEvent.doit = false;
        char typedChar = verifyEvent.character;
        if (Character.isAlphabetic(typedChar)){
            verifyEvent.doit = true;
        }
        if (typedChar == '\b') {
            verifyEvent.doit = true;
        }
    }
}
