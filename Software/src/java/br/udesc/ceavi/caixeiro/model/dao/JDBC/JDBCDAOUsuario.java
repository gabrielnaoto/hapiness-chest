package br.udesc.ceavi.caixeiro.model.dao.JDBC;

import br.udesc.ceavi.caixeiro.model.Usuario;
import br.udesc.ceavi.caixeiro.model.dao.iDaoUsuario;
import br.udesc.ceavi.core.model.dao.DAOGeneric;
import static br.udesc.ceavi.core.model.dao.DAOGeneric.OPERATOR_EQUAL;
import br.udesc.ceavi.core.model.dao.Relation;
import br.udesc.ceavi.core.util.BeanUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Samuel Felício Adriano
 */
public class JDBCDAOUsuario extends DAOGeneric<Usuario> implements iDaoUsuario {

    @Override
    public Usuario getNewEntity() {
        return new Usuario();
    }

    public Usuario getUsuario(String login, String senha) {
        StringBuilder query = new StringBuilder("SELECT *");
        query.append("\nFROM ")
                .append(this.getTableNameComplete())
                .append("\nWHERE TRUE")
                .append("\nAND login " + OPERATOR_EQUAL + " ?")
                .append("\nAND senha " + OPERATOR_EQUAL + " ?");

        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query.toString());

            statement.setString(1, login);
            statement.setString(2, senha);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Usuario usuario = new Usuario();

                for (Relation relation : getRelationships().getAllRelations()) {
                    BeanUtils.callSetter(usuario, relation.getModelName(), getValueFromStatement(result, relation));
                }
                return usuario;
            }
            return null;
        } catch (SQLException exception) {
            catchSQLException(exception);
            return null;
        }
    }

    @Override
    public boolean isLoginCadastrado(String login) {
        StringBuilder query = new StringBuilder("SELECT *");
        query.append("\nFROM ")
                .append(this.getTableNameComplete())
                .append("\nWHERE TRUE")
                .append("\nAND login " + OPERATOR_EQUAL + " ?");

        try {
            PreparedStatement statement = connection.getConnection().prepareStatement(query.toString());

            statement.setString(1, login);

            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (SQLException exception) {
            catchSQLException(exception);
            return false;
        }
    }

}
