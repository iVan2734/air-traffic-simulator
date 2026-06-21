package models;

import java.util.Objects;

public class Airport {
    private String name;
    private String code;
    private float X;
    private float Y;

    public Airport(String code,String name, float x, float y) {
        if (code == null || !code.matches("[A-Z]{3}")){
            throw new IllegalArgumentException("Kod mora da postoji i da bude obavezno 3 velika slova!");
        }
        if(x<-180 || x>180){
            throw new IllegalArgumentException("X kordinata mora da pripada intervalu [-180,180]!");
        }
        if(y<-90 || y>90){
            throw new IllegalArgumentException("X kordinata mora da pripada intervalu [-90,90]!");
        }
        this.code=code;
        this.name = name;
        X = x;
        Y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(float y) {
        Y = y;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return Float.compare(X, airport.X) == 0 && Float.compare(Y, airport.Y) == 0 && Objects.equals(name, airport.name) && Objects.equals(code, airport.code);
    }
}
