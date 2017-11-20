package javafx_salage.model;

public class Acesso {
    private Integer id_ace;
    private String descricao_ace;

    public Acesso(Integer id_ace, String descricao_ace){
        setId_ace(id_ace);
        setDescricao_ace(descricao_ace);
    }

    public Integer getId_ace() {
        return id_ace;
    }

    public void setId_ace(Integer id_ace) {
        this.id_ace = id_ace;
    }

    public String getDescricao_ace() {
        return descricao_ace;
    }

    public void setDescricao_ace(String descricao_ace) {
        this.descricao_ace = descricao_ace;
    }
}
