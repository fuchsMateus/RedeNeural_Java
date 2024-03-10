package entidades;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;


public class Peso implements Serializable {
    private Neuronio neuronioAssociado;
    private Double valorPeso;
    public Peso(Double valorPeso, Neuronio neuronioAssociado) {
        this.valorPeso = valorPeso;
        this.neuronioAssociado = neuronioAssociado;
    }

    public Double getValorPeso() {
        return valorPeso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Peso peso = (Peso) o;
        return Objects.equals(valorPeso, peso.valorPeso) && Objects.equals(neuronioAssociado, peso.neuronioAssociado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valorPeso, neuronioAssociado);
    }

    public void setValorPeso(Double valorPeso) {
        this.valorPeso = valorPeso;
    }

    public Neuronio getNeuronioAssociado() {
        return neuronioAssociado;
    }

    public void setNeuronioAssociado(Neuronio neuronioProxCamada) {
        this.neuronioAssociado = neuronioProxCamada;
    }

    @Override
    public String toString() {
        return String.format(Locale.US,"        {\"valorPeso\": %f, \"neuronioAssociadoId\": \"%s\"}",
                valorPeso, neuronioAssociado != null ? neuronioAssociado.getId() : "null");
    }


}

