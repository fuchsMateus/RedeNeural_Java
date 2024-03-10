package entidades;

import java.io.Serializable;
import java.util.*;

public class Neuronio implements Serializable {
    private Double valorNeuronio;
    private UUID id;
    private Double bias;

    private Double gradiente;
    private List<Peso> pesos;

    public Neuronio(Double valorNeuronio, Double bias) {
        this.valorNeuronio = valorNeuronio;
        this.bias = bias;
        this.id = UUID.randomUUID();
    }

    public Neuronio(Double valorNeuronio, Double bias, List<Peso> pesos) {
        this.valorNeuronio = valorNeuronio;
        this.bias = bias;
        this.pesos = pesos;
        this.id = UUID.randomUUID();
    }

    public Double getValorNeuronio() {
        return valorNeuronio;
    }

    public void setValorNeuronio(Double valorNeuronio) {
        this.valorNeuronio = valorNeuronio;
    }

    public Double getBias() {
        return bias;
    }

    public void setBias(Double bias) {
        this.bias = bias;
    }

    public List<Peso> getPesos() {
        return pesos;
    }

    public UUID getId() {
        return id;
    }

    public Double getGradiente() {
        return gradiente;
    }

    public void setGradiente(Double gradiente) {
        this.gradiente = gradiente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Neuronio neuronio = (Neuronio) o;
        return Objects.equals(id, neuronio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setPesos(List<Peso> pesos) {
        this.pesos = pesos;
    }

    public Boolean addPeso(Peso peso){
        return this.pesos.add(peso);
    }

    public Boolean removePeso(Peso peso){
        return this.pesos.remove(peso);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("    {\n");
        sb.append("      \"id\": \"").append(id).append("\",\n");
        sb.append("      \"valorNeuronio\": ").append(valorNeuronio).append(",\n");
        if (bias != null) {
            sb.append("      \"bias\": ").append(bias).append(",\n");
        }
        if (gradiente != null) {
            sb.append("      \"gradiente\": ").append(gradiente).append(",\n");
        }
        if (pesos != null && !pesos.isEmpty()) {
            sb.append("      \"pesos\": [\n");
            for (int i = 0; i < pesos.size(); i++) {
                sb.append("        ").append(pesos.get(i).toString());
                if (i < pesos.size() - 1) sb.append(",");
                sb.append("\n");
            }
            sb.append("      ]\n");
        }
        sb.append("    }");
        return sb.toString();
    }


}
