package javafx_salage.viewmodel;

import javafx_salage.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EnsalamentoVM {
    //ensalamento
    private Integer id_ensal;
    private String data_inicio;
    private String data_final;
    private String hora_inicio;
    private ArrayList<Hora> horas;
    private List<Boolean> dias;

    //Sala
    private Integer numero_sala;
    private String descricao_sala;

    //Disciplina
    private String id_disc;
    private String nome_turma;

    //Professor
    private String rgf_prof;
    private String nome_prof;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public EnsalamentoVM(Integer id_ensal){
        setId_ensal(id_ensal);
    }

    public EnsalamentoVM(Integer id_ensal, String hora_inicio){
        setId_ensal(id_ensal);
        setHora_inicio(hora_inicio);
    }

    public EnsalamentoVM(Integer id_ensal, String data_inicio, String hora_inicio, Integer numero_sala, String descricao_sala, String id_disc, String nome_turma, String rgf_prof, String nome_prof) {
        setId_ensal(id_ensal);
        setData_inicio(data_inicio);
        setHora_inicio(hora_inicio);
        setNumero_sala(numero_sala);
        setDescricao_sala(descricao_sala);
        setId_disc(id_disc);
        setNome_turma(nome_turma);
        setRgf_prof(rgf_prof);
        setNome_prof(nome_prof);
    }

    public Integer getId_ensal() {
        return id_ensal;
    }

    public void setId_ensal(Integer id_ensal) {
        this.id_ensal = id_ensal;
    }

    public String getData_inicio() {
        LocalDate localDate = LocalDate.parse(data_inicio);
        return dateTimeFormatter.format(localDate);
    }

    public String getData_inicioWOformat() {
        return data_inicio;
    }

    public void setData_inicio(String data_inicio) {
        this.data_inicio = data_inicio;
    }

    public String getData_final() {
        LocalDate localDate = LocalDate.parse(data_final);
        return dateTimeFormatter.format(localDate);
    }

    public String getData_finalWOformat(){
        return data_final;
    }

    public void setData_final(String data_final) {
        this.data_final = data_final;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public ArrayList<Hora> getHoras() {
        return horas;
    }

    public void setHoras(ArrayList<Hora> horas) {
        this.horas = horas;
    }

    public List<Boolean> getDias() {
        return dias;
    }

    public void setDias(List<Boolean> dias) {
        this.dias = dias;
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
}
