package caixeiro.model;

import core.model.Entity;
import java.util.Date;

/**
 * @author wagner
 * @version 1.0
 * @created 04-jun-2016 09:51:03
 */
public class Pagamento extends Entity<Pagamento> {

    private Date data;
    private int id;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}//end Pagamento