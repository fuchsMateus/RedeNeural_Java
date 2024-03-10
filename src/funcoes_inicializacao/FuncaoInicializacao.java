package funcoes_inicializacao;

import entidades.Camada;
import entidades.Peso;

import java.io.Serializable;
import java.util.List;

public interface FuncaoInicializacao extends Serializable {
    public double[][] inicializarPesos(int inputSize, int outputSize);
}
