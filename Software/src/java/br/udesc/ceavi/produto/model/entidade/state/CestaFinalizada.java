package br.udesc.ceavi.produto.model.entidade.state;

public class CestaFinalizada implements CestaState{

    @Override
    public CestaState verificaEstado() {
        System.out.println("Cesta Finalizada");
        return this;
    }

    @Override
    public CestaState trocaEstado() {
      return this;
    }
    
}
