package funcoes_bias;

import java.util.Random;

public class XavierBias implements FuncaoBias {
    private final Random rand;

    public XavierBias() {
        this.rand = new Random();
    }

    @Override
    public Double gerarBias() {
        return rand.nextGaussian() * Math.sqrt(2.0);
    }
}
