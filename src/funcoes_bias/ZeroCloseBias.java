package funcoes_bias;

import java.util.Random;

public class ZeroCloseBias implements FuncaoBias{

    private final Random rand;

    public ZeroCloseBias() {
        this.rand = new Random();
    }

    @Override
    public Double gerarBias() {
        return rand.nextDouble() * 0.1 - 0.05;
    }
}
