package funcoes_inicializacao;

import java.util.Random;

public class XavierGlorot implements FuncaoInicializacao{


    @Override
    public double[][] inicializarPesos(int inputSize, int outputSize) {
        Random random = new Random();
        double[][] weights = new double[outputSize][inputSize];
        double limit = Math.sqrt(6.0 / (inputSize + outputSize));

        for (int i = 0; i < outputSize; i++) {
            for (int j = 0; j < inputSize; j++) {
                weights[i][j] = random.nextDouble() * 2 * limit - limit;
            }
        }

        return weights;
    }
}
