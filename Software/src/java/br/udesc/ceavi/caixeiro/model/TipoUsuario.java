package br.udesc.ceavi.caixeiro.model;

/**
 *
 * @author Samuel Felício Adriano
 */
public enum TipoUsuario {

     ADMINISTRADOR(1)
    ,FORNECEDOR   (2)
    ,CLIENTE      (3);


    private final int tipo;


    private TipoUsuario(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }


    public static TipoUsuario getTipoUsuarioByTipo(int tipo) {
        if(tipo == ADMINISTRADOR.getTipo()) {
            return ADMINISTRADOR;
        }

        if(tipo == FORNECEDOR.getTipo()) {
            return FORNECEDOR;
        }

        if(tipo == CLIENTE.getTipo()) {
            return CLIENTE;
        }

        throw new RuntimeException("Tipo inválido");
    }

}