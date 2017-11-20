package javafx_salage.model;

public class Disciplina {
    private String id_disc;
    private String nome_turma;
    private Integer qtd_alunos;

    public Disciplina(String id_disc, String nome_turma) {
        setId_disc(id_disc);
        setNome_turma(nome_turma);
    }

    public Disciplina(String id_disc, String nome_turma, int qtd_alunos) {
        setId_disc(id_disc);
        setNome_turma(nome_turma);
        setQtd_alunos(qtd_alunos);
    }

    public String getId_disc() {
        return id_disc;
    }

    public void setId_disc(String id_disc) {
        this.id_disc = id_disc;
    }

    public String getNome_turma() {
        return nome_turma;
    }

    public void setNome_turma(String nome_turma) {
        this.nome_turma = nome_turma;
    }

    public Integer getQtd_alunos() {
        return qtd_alunos;
    }

    public void setQtd_alunos(Integer qtd_alunos) {
        this.qtd_alunos = qtd_alunos;
    }

    @Override
    public String toString(){
        return getNome_turma();
    }
}
