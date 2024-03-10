package funcoes_ativacao;

import java.io.Serializable;

public interface FuncaoAtivacao extends Serializable {
    public Double ativar(Double x);
    Double derivar(Double valorAtivado);
}
