package br.udesc.ceavi.caixeiro.model.dao;

import br.udesc.ceavi.caixeiro.model.Usuario;
import br.udesc.ceavi.core.persistence.Persistible;

/**
 *
 * @author Samuel Felício Adriano
 */
public interface iDaoUsuario extends Persistible<Usuario> {

    public Usuario getUsuario(String login, String senha);

    public boolean isLoginCadastrado(String login);
}