package caixeiro.model;

/**
 * @author Ricardo
 * @version 1.0
 * @created 04-jun-2016 09:51:03
 */
public class Logradouro {

    private int id;
    private String descricao;
    private Bairro Bairro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Bairro getBairro() {
        return Bairro;
    }

    public void setBairro(Bairro Bairro) {
        this.Bairro = Bairro;
    }
    
}//end Logradouro