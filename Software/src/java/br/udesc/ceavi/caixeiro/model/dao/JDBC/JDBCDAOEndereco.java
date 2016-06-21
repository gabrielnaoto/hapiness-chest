package br.udesc.ceavi.caixeiro.model.dao.JDBC;

import br.udesc.ceavi.caixeiro.model.dao.iDaoEndereco;
import br.udesc.ceavi.caixeiro.model.Endereco;
import br.udesc.ceavi.caixeiro.model.Entrega;
import br.udesc.ceavi.caixeiro.model.RelacionamentoEndereco;
import br.udesc.ceavi.core.model.dao.DAOGeneric;
import br.udesc.ceavi.core.model.dao.EntityIterable;
import br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Samuel Felício Adriano
 */
public class JDBCDAOEndereco extends DAOGeneric<Endereco> implements iDaoEndereco {

    @Override
    public Endereco getNewEntity() {
        return new Endereco();
    }

    @Override
    public void limpaEnderecos() {
        String sub = "select endereco_id from produto.cliente  union  select endereco_id from caixeiro.centrodistribuicao";
        String comando1 = "delete from caixeiro.relacionamento_endereco\n"
                + " where endereco_saida_id not in (" + sub + ")\n"
                + "    or endereco_chegada_id not in (" + sub + ");";

        String comando2 = "delete from produto.endereco\n"
                + " where id not in (" + sub + ");";

        execute(comando1);
        execute(comando2);

    }

}
