package javafx_salage.model;

public class Sala {
    private Integer numero_sala;
    private String descricao_sala;
    private Integer capacidade_sala;

    public Sala(int numero_sala, String descricao_sala, int capacidade_sala){
        this.numero_sala = numero_sala;
        this.descricao_sala = descricao_sala;
        this.capacidade_sala = capacidade_sala;
    }

    public Integer getNumero_sala() {
        return numero_sala;
    }

    public void setNumero_sala(Integer numero_sala) {
        this.numero_sala = numero_sala;
    }

    public String getDescricao_sala() {
        return descricao_sala;
    }

    public void setDescricao_sala(String descricao_sala) {
        this.descricao_sala = descricao_sala;
    }

    public Integer getCapacidade_sala() {
        return capacidade_sala;
    }

    public void setCapacidade_sala(Integer capacidade_sala) {
        this.capacidade_sala = capacidade_sala;
    }
}
