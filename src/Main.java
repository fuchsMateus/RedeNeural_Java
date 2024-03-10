import entidades.RedeNeural;
import funcoes_ativacao.FuncaoSigmoid;
import funcoes_bias.XavierBias;
import funcoes_inicializacao.XavierGlorot;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {

        RedeNeural rn = new RedeNeural(new Integer[]{2, 2, 1},
                new FuncaoSigmoid(),
                new XavierGlorot(),
                new XavierBias());

        System.out.println(rn);
    }
}