package br.udesc.ceavi.produto.model.entidade.state;

public interface CestaState {
    CestaState verificaEstado();
    CestaState trocaEstado();
    
}
