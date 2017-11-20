package javafx_salage.model;

public class Usuario {
    private String login_usu;
    private String senha_usu;
    private Integer id_ace;

    public Usuario(String login_usu, String senha_usu, Integer id_ace) {
        setLogin_usu(login_usu);
        setSenha_usu(senha_usu);
        setId_ace(id_ace);
    }

    public String getLogin_usu() {
        return login_usu;
    }

    public void setLogin_usu(String login_usu) {
        this.login_usu = login_usu;
    }

    public String getSenha_usu() {
        return senha_usu;
    }

    public void setSenha_usu(String senha_usu) {
        this.senha_usu = senha_usu;
    }

    public Integer getId_ace() {
        return id_ace;
    }

    public void setId_ace(Integer id_ace) {
        this.id_ace = id_ace;
    }
}
