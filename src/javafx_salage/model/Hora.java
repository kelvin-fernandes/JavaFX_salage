package javafx_salage.model;

import java.time.LocalTime;

public class Hora {
    private LocalTime hora_inicio;

    public Hora(LocalTime hora_inicio){
        this.hora_inicio = hora_inicio;
    }

    public LocalTime getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(LocalTime hora_inicio) {
        this.hora_inicio = hora_inicio;
    }
}
