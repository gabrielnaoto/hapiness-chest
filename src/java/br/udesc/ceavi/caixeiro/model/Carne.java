package caixeiro.model;

import core.model.Entity;
import java.util.List;

/**
 * @author wagner
 * @version 1.0
 * @created 04-jun-2016 09:50:57
 */
public class Carne extends Entity<Carne> {

    private int diaVencimento;
    private int id;
    private List<Pagamento> pagamentos;

    public int getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(int diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public void adicionaPagamento(Pagamento pagamento) {
        this.pagamentos.add(pagamento);
    }

    public void removePagamento(Pagamento pagamento) {
        this.pagamentos.remove(pagamento);
    }

}//end Carne