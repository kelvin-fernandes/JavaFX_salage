package javafx_salage.model;

public class Professor {
    private String rgf_prof;
    private String nome_prof;

    public Professor(String rgf_prof, String nome_prof) {
        setRgf_prof(rgf_prof);
        setNome_prof(nome_prof);
    }

    public String getRgf_prof() {
        return rgf_prof;
    }

    public void setRgf_prof(String rgf_prof) {
        this.rgf_prof = rgf_prof;
    }

    public String getNome_prof() {
        return nome_prof;
    }

    public void setNome_prof(String nome_prof) {
        this.nome_prof = nome_prof;
    }

    @Override
    public String toString(){
        return getNome_prof();
    }
}
