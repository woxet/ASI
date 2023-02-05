package agenda.synchro.ressources;

import java.text.SimpleDateFormat;

public class Ressources {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat reverseDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    private static String ip = "http://192.168.1.10:8080/";
    //private static String ip = "http://10.1.24.72:8080/";
    private static String path = "ASI/rdv/";

    public static String getIP(){
        return ip;
    }

    public static String getPath(){
        return path;
    }
}
