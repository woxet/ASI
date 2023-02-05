package agenda.synchro.ressources;

import androidx.annotation.NonNull;
import com.owlike.genson.Genson;

import java.util.Date;
public class RDV {
    private int idRDV;
    private String name;
    private String date;
    private String time;
    private String location;

    public RDV() {
        this.setIdRDV(-1);
        this.setName("unknown");
        this.setDate("unknown");
        this.setTime("unknown");
        this.setLocation("unknown");
    }

    public RDV(int idRDV, String name, String date, String time, String location){
        this.setIdRDV(idRDV);
        this.setName(name);
        this.setDate(date);
        this.setTime(time);
        this.setLocation(location);
    }

    public RDV(String name, String date, String time, String location){
        this.setName(name);
        this.setDate(date);
        this.setTime(time);
        this.setLocation(location);
    }

    public RDV(int idRDV, String name, String time){
        this.idRDV = idRDV;
        this.name = name;
        this.time = time;
    }

    public int getIdRDV() {return idRDV;}

    public void setIdRDV(int idRDV) {
        this.idRDV = idRDV;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @NonNull
    @Override
    public String toString(){
        return getTime() + " - " + getName();
    }

    public String toJSON(){
        Genson genson = new Genson();
        return genson.serialize(this);
    }
}
