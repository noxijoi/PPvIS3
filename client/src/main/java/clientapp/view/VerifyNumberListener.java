package clientapp.view;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

//не объединять по типу
public class VerifyNumberListener implements VerifyListener {
    @Override
    public void verifyText(VerifyEvent verifyEvent) {
        verifyEvent.doit = false;
        char typedChar = verifyEvent.character;
        if (Character.isDigit(typedChar)) {
            verifyEvent.doit = true;
        }
        if (typedChar == '\b') {
            verifyEvent.doit = true;
        }

    }
}
