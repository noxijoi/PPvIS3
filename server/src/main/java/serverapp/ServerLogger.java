package serverapp;

import org.eclipse.swt.widgets.Text;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ServerLogger  implements Runnable{
    private Text text;

    public ServerLogger(){

    }
    public ServerLogger(Text text){
        this.text = text;
    }

    public synchronized void log(String message){
        Date date  = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        //text.append(dateFormat.format(date)+ " "+ message + "\n");
    }

    public void setText(Text text) {
        this.text = text;
    }

    @Override
    public void run(){

    }
}
