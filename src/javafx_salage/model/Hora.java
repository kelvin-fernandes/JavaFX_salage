package javafx_salage.model;

import java.time.LocalTime;

public class Hora {
    private String hora_inicio;

    public Hora(String hora_inicio){
        setHora_inicio(hora_inicio);
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String toString(){
        return getHora_inicio();
    }
}
