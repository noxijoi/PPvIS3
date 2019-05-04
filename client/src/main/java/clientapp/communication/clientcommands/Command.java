package clientapp.communication.clientcommands;

import clientapp.communication.cervercommands.Message;
import clientapp.managedb.ContentController;

import java.io.Serializable;

public interface Command extends Serializable {
    Message execute(ContentController controller);

    String getDescription();
}
