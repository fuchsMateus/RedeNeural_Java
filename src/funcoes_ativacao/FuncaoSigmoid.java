package funcoes_ativacao;

public class FuncaoSigmoid implements FuncaoAtivacao{
    @Override
    public Double ativar(Double x) {
        return 1.0/(1.0+ Math.pow(Math.E, (-x)));
    }

    @Override
    public Double derivar(Double valorAtivado) {
        return valorAtivado * (1 - valorAtivado);
    }
}
