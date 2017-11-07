package javafx_salage.viewmodel;

import javafx_salage.model.*;

public class EnsalamentoVM {
    public Ensalamento ensalamento;
    public Sala sala;
    public Disciplina disciplina;
    public Professor professor;

    public EnsalamentoVM(){

    }
    public EnsalamentoVM(Ensalamento ensalamento, Sala sala, Disciplina disciplina, Professor professor) {
        this.ensalamento = ensalamento;
        this.sala = sala;
        this.disciplina = disciplina;
        this.professor = professor;
    }
}
