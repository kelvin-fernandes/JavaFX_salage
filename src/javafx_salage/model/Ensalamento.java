package javafx_salage.model;

import java.time.LocalDate;

public class Ensalamento {
    private LocalDate data_inicio;
    private Integer numero_sala;
    private String rgf_prof;
    private String id_disc;
    private Sala sala;
    private Disciplina disciplina;
    private Professor professor;

    public Ensalamento(LocalDate data_inicio, Integer numero_sala, String rgf_prof, String id_disc){
        this.data_inicio = data_inicio;
        this.numero_sala = numero_sala;
        this.rgf_prof = rgf_prof;
        this.id_disc = id_disc;
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

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
