package br.udesc.ceavi.produto.model.entidade.state;

public class CestaEmAndamento implements CestaState {

    @Override
    public CestaState verificaEstado() {
        System.out.println("A cesta está em Andamento");
        return this;
    }

    @Override
    public CestaState trocaEstado() {
        return new CestaFinalizada();
    }

}
