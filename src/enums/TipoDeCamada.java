package enums;

import java.io.Serializable;

public enum TipoDeCamada implements Serializable {
    CAMADA_ENTRADA("entidades.Camada de Entrada"),
    CAMADA_OCULTA("entidades.Camada Oculta"),
    CAMADA_SAIDA("entidades.Camada de Saída");
    
    private final String descricao;
    
    TipoDeCamada(String descricao) {
    this.descricao = descricao;
    }

    @Override
    public String toString() {
    return descricao;
    }


}
