package javafx_salage.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;

public class Usuario {
    private SimpleStringProperty login_usu;
    private SimpleStringProperty senha_usu;
    private SimpleIntegerProperty id_ace;

    public Usuario(String login_usu, String senha_usu, Integer id_ace) {
        this.login_usu = new SimpleStringProperty(login_usu);
        this.senha_usu = new SimpleStringProperty(senha_usu);
        this.id_ace = new SimpleIntegerProperty(id_ace);
    }

    public String getLogin_usu() {
        return login_usu.get();
    }

    public String getSenha_usu() {
        return senha_usu.get();
    }

    public int getId_ace() {
        return id_ace.get();
    }
}
