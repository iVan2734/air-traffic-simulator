package models;

public class Flight {
    private Airport src;
    private Airport dst;
    private int startHours;
    private int startMinutes;
    private int duration;

    public Flight(Airport src, Airport dst, int startHours, int startMinutes, int duration) {
        if(src==null){
            throw new IllegalArgumentException("Polazni aerodrom je obavezan!");
        }
        if(dst==null){
            throw new IllegalArgumentException("Dolazni aerodrom je obavezan!");
        }
        if(startHours<0 || startHours>23){
            throw new IllegalArgumentException("Nevalidan broj sati za polazak aviona!");
        }
        if(startMinutes<0 || startMinutes>59){
            throw new IllegalArgumentException("Nevalidan broj minuta za polazak aviona!");
        }
        if(duration<0){
            throw new IllegalArgumentException("Nevalidan broj minuta za trajanje leta!");
        }
        this.src = src;
        this.dst = dst;
        this.startHours = startHours;
        this.startMinutes = startMinutes;
        this.duration = duration;
    }

    public Airport getSrc() {
        return src;
    }

    public void setSrc(Airport src) {
        this.src = src;
    }

    public Airport getDst() {
        return dst;
    }

    public void setDst(Airport dst) {
        this.dst = dst;
    }

    public int getStartHours() {
        return startHours;
    }

    public void setStartHours(int startHours) {
        this.startHours = startHours;
    }

    public int getStartMinutes() {
        return startMinutes;
    }

    public void setStartMinutes(int startMinutes) {
        this.startMinutes = startMinutes;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
