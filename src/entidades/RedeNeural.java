package entidades;

import enums.TipoDeCamada;
import funcoes_ativacao.FuncaoAtivacao;
import funcoes_bias.FuncaoBias;
import funcoes_inicializacao.FuncaoInicializacao;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class RedeNeural implements Serializable {
    private List<Camada> camadas = new ArrayList<>();
    private FuncaoAtivacao funcaoAtivacao;
    private FuncaoInicializacao funcaoInicializacao;
    private FuncaoBias funcaoBias;
    private Integer[] topologia;


    public RedeNeural(String nomeArquivoModelo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivoModelo))) {
            RedeNeural modeloImportado = (RedeNeural) ois.readObject();

            this.camadas = modeloImportado.getCamadas();
            this.funcaoAtivacao = modeloImportado.getFuncaoAtivacao();
            this.funcaoInicializacao = modeloImportado.getFuncaoInicializacao();
            this.funcaoBias = modeloImportado.getFuncaoBias();
            this.topologia  = modeloImportado.getTopologia();
            System.out.println("Modelo importado com sucesso de " + nomeArquivoModelo);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao importar o modelo: " + e.getMessage());
        }
    }

    public RedeNeural(Integer[] topologia, FuncaoAtivacao funcaoAtivacao, FuncaoInicializacao funcaoInicializacao,
                      FuncaoBias funcaoBias) {
        this.funcaoAtivacao = funcaoAtivacao;
        this.funcaoInicializacao = funcaoInicializacao;
        this.funcaoBias = funcaoBias;
        this.topologia = topologia;
        inicializar(topologia);
    }
    private void inicializar(Integer [] topologia){
        //Camada de entrada
        Camada camadaEntrada = new Camada(TipoDeCamada.CAMADA_ENTRADA);
        for (int i = 0; i < topologia[0]; i++) {
            camadaEntrada.addNeuronio(new Neuronio(null, null));
        }

        this.camadas.add(camadaEntrada);

        //Camadas Ocultas e Saída
        for (int i = 0; i < topologia.length-1; i++) {
            Camada camada;
            if (i == topologia.length-2){
                camada = new Camada(TipoDeCamada.CAMADA_SAIDA);
            }
            else {camada = new Camada(TipoDeCamada.CAMADA_OCULTA);}

            double[][] matrizPesos = this.funcaoInicializacao.inicializarPesos(topologia[i], topologia[i+1]);

            for (double[] listPesos : matrizPesos) {
                List<Peso> pesos = new ArrayList<>();
                for (int k = 0; k < topologia[i]; k++) {
                    pesos.add(new Peso(listPesos[k], camadas.getLast().getNeuronios().get(k)));
                }
                camada.addNeuronio(new Neuronio(null, this.funcaoBias.gerarBias(), pesos));
            }

            this.camadas.add(camada);
        }
    }

    private void feedForward(List<Double> dadosEntradas){
        for (int i = 0; i < dadosEntradas.size(); i++) {
            this.camadas.get(0).getNeuronios().get(i).setValorNeuronio(dadosEntradas.get(i));
        }

        for (int i = 1; i < this.camadas.size(); i++) {
            for (Neuronio n: this.camadas.get(i).getNeuronios()) {
                double somatorioValor = n.getBias();
                for (Peso p : n.getPesos()) {
                    somatorioValor+= p.getValorPeso() * p.getNeuronioAssociado().getValorNeuronio();
                }
                n.setValorNeuronio(this.funcaoAtivacao.ativar(somatorioValor));
            }
        }
    }

    private void backPropagation(List<Double> saidasEsperadas, double taxaAprendizado) {
        int numCamadas = camadas.size();
        Camada camadaSaida = camadas.getLast();

        for (int i = 0; i < camadaSaida.getNeuronios().size(); i++) {
            Neuronio neuronio = camadaSaida.getNeuronios().get(i);
            double saidaObtida = neuronio.getValorNeuronio();
            double erro = saidasEsperadas.get(i) - saidaObtida;
            double gradiente = erro * funcaoAtivacao.derivar(saidaObtida);
            neuronio.setGradiente(gradiente);
        }

        for (int i = numCamadas - 2; i >= 0; i--) {
            Camada camadaAtual = camadas.get(i);
            Camada camadaProxima = camadas.get(i + 1);

            for (Neuronio neuronioAtual : camadaAtual.getNeuronios()) {
                double soma = 0.0;
                for (Neuronio neuronioProximo : camadaProxima.getNeuronios()) {
                    soma += neuronioProximo.getPesos().stream()
                            .filter(peso -> peso.getNeuronioAssociado().equals(neuronioAtual))
                            .findFirst().get().getValorPeso() * neuronioProximo.getGradiente();
                }
                double gradiente = soma * funcaoAtivacao.derivar(neuronioAtual.getValorNeuronio());
                neuronioAtual.setGradiente(gradiente);
            }
        }

        for (Camada camada : camadas) {
            if (camada.getTipoDeCamada() != TipoDeCamada.CAMADA_ENTRADA) {
                for (Neuronio neuronio : camada.getNeuronios()) {
                    double novoBias = neuronio.getBias() + taxaAprendizado * neuronio.getGradiente();
                    neuronio.setBias(novoBias);
                    for (Peso peso : neuronio.getPesos()) {

                        double novaPonderacao = peso.getValorPeso() + taxaAprendizado * neuronio.getGradiente() * peso.getNeuronioAssociado().getValorNeuronio();
                        peso.setValorPeso(novaPonderacao);
                    }
                }
            }
        }
    }

    public void train(int epochs, List<List<Double>> dadosTreinamento, double taxaAprendizado){
        for (int i = 0; i < epochs; i++) {
            boolean bk = false;
            System.out.println("EPOCH "+i);
            Collections.shuffle(dadosTreinamento);
            List<List<Double>> entradas = new ArrayList<>();
            List<List<Double>> saidasEsperadas = new ArrayList<>();
            for (List<Double> dado: dadosTreinamento) {
                entradas.add(dado.subList(0, topologia[0]));
                saidasEsperadas.add(dado.subList(topologia[0], dado.size()));
            }

            for (int j = 0; j < dadosTreinamento.size(); j++) {
                this.feedForward(entradas.get(j));
                this.backPropagation(saidasEsperadas.get(j), taxaAprendizado);
            }
        }
    }

    public void train(int epochs, String filePath, double taxaAprendizado) throws IOException {
        List<List<Double>> dadosTreinamento = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Double> row = Arrays.stream(line.split(","))
                        .map(Double::parseDouble)
                        .collect(Collectors.toList());
                dadosTreinamento.add(row);
            }
        }

        this.train(epochs, dadosTreinamento, taxaAprendizado);
    }

    public List<Double> predict(List<Double> entradas){
        this.feedForward(entradas);
        return this.camadas.getLast().getNeuronios().stream().map(Neuronio::getValorNeuronio).collect(Collectors.toList());
    }

    public void exportarModelo(String nomeArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(this);
            System.out.println("Modelo exportado com sucesso para " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao exportar o modelo: " + e.getMessage());
        }
    }

    public List<Camada> getCamadas() {
        return camadas;
    }

    public FuncaoAtivacao getFuncaoAtivacao() {
        return funcaoAtivacao;
    }

    public FuncaoInicializacao getFuncaoInicializacao() {
        return funcaoInicializacao;
    }

    public FuncaoBias getFuncaoBias() {
        return funcaoBias;
    }

    public Integer[] getTopologia() {
        return topologia;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{\n \"Camadas\": [\n");
        for (int i = 0; i < camadas.size(); i++) {
            sb.append(camadas.get(i).toString());
            if (i < camadas.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append(" ]\n}");
        return sb.toString();
    }

}
