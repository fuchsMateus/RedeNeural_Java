package entidades;

import enums.TipoDeCamada;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Camada implements Serializable {

    public Camada(TipoDeCamada tipoDeCamada) {
        this.tipoDeCamada = tipoDeCamada;
    }
    private List<Neuronio> neuronios =  new ArrayList<>();

    private TipoDeCamada tipoDeCamada;

    public void setNeuronios(List<Neuronio> neuronios) {
        this.neuronios = neuronios;
    }

    public Boolean addNeuronio(Neuronio neuronio){
        return this.neuronios.add(neuronio);
    }

    public List<Neuronio> getNeuronios() {
        return neuronios;
    }
    public Boolean removeNeuronio(Neuronio neuronio){
        return this.neuronios.remove(neuronio);
    }

    public TipoDeCamada getTipoDeCamada() {
        return tipoDeCamada;
    }

    public void setTipoDeCamada(TipoDeCamada tipoDeCamada) {
        this.tipoDeCamada = tipoDeCamada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Camada camada = (Camada) o;
        return Objects.equals(neuronios, camada.neuronios) && tipoDeCamada == camada.tipoDeCamada;
    }

    @Override
    public int hashCode() {
        return Objects.hash(neuronios, tipoDeCamada);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"tipo\": \"").append(tipoDeCamada).append("\",\n");
        sb.append("  \"neuronios\": [\n");
        for (int i = 0; i < neuronios.size(); i++) {
            sb.append("    ").append(neuronios.get(i).toString());
            if (i < neuronios.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("  ]\n");
        sb.append("}");
        return sb.toString();
    }

}
