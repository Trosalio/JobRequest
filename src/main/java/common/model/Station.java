package common.model;

import java.io.Serializable;

public class Station implements Serializable{
    private String code;
    private String name;

    public Station(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return String.format("%s - %s", code, name);
    }
}
