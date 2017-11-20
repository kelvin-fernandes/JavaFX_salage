package javafx_salage.model;

public class Ensalamento {
    private Integer id_ensal;
    private String data_inicio;
    private String data_final;
    private String hora_inicio;
    private Integer numero_sala;
    private String rgf_prof;
    private String id_disc;

    public Ensalamento(Integer id_ensal, String data_inicio, String data_final, Integer numero_sala, String rgf_prof, String id_disc){
        setId_ensal(id_ensal);
        setData_inicio(data_inicio);
        setData_final(data_final);
        setNumero_sala(numero_sala);
        setRgf_prof(rgf_prof);
        setId_disc(id_disc);
    }

    public Integer getId_ensal() {
        return id_ensal;
    }

    public void setId_ensal(Integer id_ensal) { this.id_ensal = id_ensal; }

    public String getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(String data_inicio) {
        this.data_inicio = data_inicio;
    }

    public String getData_final() {
        return data_final;
    }

    public void setData_final(String data_final) {
        this.data_final = data_final;
    }

    public String getHora_inicio(){
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio){
        this.hora_inicio = hora_inicio;
    }

    public Integer getNumero_sala() {
        return numero_sala;
    }

    public void setNumero_sala(Integer numero_sala) {
        this.numero_sala = numero_sala;
    }

    public String getRgf_prof() {
        return rgf_prof;
    }

    public void setRgf_prof(String rgf_prof) {
        this.rgf_prof = rgf_prof;
    }

    public String getId_disc() {
        return id_disc;
    }

    public void setId_disc(String id_disc) {
        this.id_disc = id_disc;
    }
}
