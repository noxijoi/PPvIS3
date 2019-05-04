package serverapp.communication.clientcommands;

import serverapp.communication.cervercommands.Message;
import serverapp.managedb.ContentController;

import java.io.Serializable;

public interface Command extends Serializable {
    Message execute(ContentController controller);

    String getDescription();
}
