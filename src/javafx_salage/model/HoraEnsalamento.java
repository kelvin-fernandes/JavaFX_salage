package javafx_salage.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class HoraEnsalamento {
    private LocalTime hora_inicio;
    private LocalDate data_inicio;
    private Integer numero_sala;

    public HoraEnsalamento(LocalTime hora_inicio, LocalDate data_inicio, Integer numero_sala){
        this.hora_inicio = hora_inicio;
        this.data_inicio = data_inicio;
        this.numero_sala = numero_sala;
    }

    public LocalTime getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(LocalTime hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public LocalDate getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(LocalDate data_inicio) {
        this.data_inicio = data_inicio;
    }

    public Integer getNumero_sala() {
        return numero_sala;
    }

    public void setNumero_sala(Integer numero_sala) {
        this.numero_sala = numero_sala;
    }
}
