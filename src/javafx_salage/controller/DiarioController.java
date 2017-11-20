package javafx_salage.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx_salage.DAO.EnsalamentoDAO;
import javafx_salage.model.Hora;
import javafx_salage.viewmodel.EnsalamentoVM;

import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class DiarioController implements Initializable{

    @FXML
    private TableView<EnsalamentoVM> tableEnsalamento;

    @FXML
    private TableColumn<EnsalamentoVM, Integer> colSala;

    @FXML
    private TableColumn<EnsalamentoVM, String> colDataInicio;

    @FXML
    private TableColumn<EnsalamentoVM, String> colPeriodicidade;

    @FXML
    private TableColumn<EnsalamentoVM, String> colDisciplina;

    @FXML
    private TableColumn<EnsalamentoVM, String> colProfessor;

    private EnsalamentoDAO ensalamentoDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ensalamentoDAO = new EnsalamentoDAO();
        initializeDailyEnsalamentoTable();
    }

    private void setItemsTable(){
        tableEnsalamento.getItems().clear();
        colSala.setCellValueFactory(new PropertyValueFactory<>("numero_sala"));
        colDataInicio.setCellValueFactory(new PropertyValueFactory<>("data_inicio"));
        colPeriodicidade.setCellValueFactory(new PropertyValueFactory<>("horas"));
        colDisciplina.setCellValueFactory(new PropertyValueFactory<>("nome_turma"));
        colProfessor.setCellValueFactory(new PropertyValueFactory<>("nome_prof"));
    }

    private void initializeDailyEnsalamentoTable(){
        try {
            setItemsTable();
            ObservableList<EnsalamentoVM> ensalamentos = ensalamentoDAO.getDaily();

            if (ensalamentos != null) {
                ObservableList<EnsalamentoVM> ensalamentosNovos = FXCollections.observableArrayList();
                int id_ensal, contadorEnsalamento = 0;
                EnsalamentoVM ensalamentoInicial;
                if (ensalamentos.size() > 1) {
                    for (int i = 0; i < ensalamentos.size() - 1; i++) {
                        id_ensal = ensalamentos.get(i).getId_ensal();
                        boolean proximoDiferente = false;

                        if (ensalamentos.get(i + 1).getId_ensal() != id_ensal) {
                            ensalamentoInicial = ensalamentos.get(i - contadorEnsalamento);
                            ensalamentosNovos.add(ensalamentoInicial);
                            proximoDiferente = true;
                            contadorEnsalamento = 0;
                        }
                        if (i + 1 == ensalamentos.size() - 1) {
                            if (ensalamentos.get(i + 1).getId_ensal() == id_ensal) { //caso o último registro não seja diferente do penúltimo registro (ou seja, mesmo ensalamento)
                                if (contadorEnsalamento > 0) { //verifica o último ensalamento caso este tenha mais de 2 registros
                                    ensalamentoInicial = ensalamentos.get(i - contadorEnsalamento);
                                } else { //caso o último ensalamento tenha 2 registros
                                    ensalamentoInicial = ensalamentos.get(i);
                                }
                                ensalamentosNovos.add(ensalamentoInicial);
                            }
                            else
                                ensalamentosNovos.add(ensalamentos.get(i + 1));
                        }
                        else if (!proximoDiferente) {
                            contadorEnsalamento++;
                        }
                    }
                }

                ObservableList<EnsalamentoVM> horasWIdEnsal = EnsalamentoDAO.getHorasWId_ensal();


                for (EnsalamentoVM e : ensalamentos) {
                    ArrayList<Hora> h = new ArrayList<>();
                    for (EnsalamentoVM ehoras : horasWIdEnsal) {
                        if (ehoras.getId_ensal() == e.getId_ensal())
                            h.add(new Hora(ehoras.getHora_inicio()));
                    }
                    e.setHoras(h);
                }

                tableEnsalamento.setPlaceholder(new Label("Não há ensalamentos no dia de hoje - " + LocalDate.now().toString()));
                tableEnsalamento.setItems(ensalamentosNovos);
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    @FXML
    private void btnReloadAction(){
        initializeDailyEnsalamentoTable();
    }
}
