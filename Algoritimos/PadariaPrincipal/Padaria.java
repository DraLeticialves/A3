
package PadariaPrincipal;

import java.util.ArrayList;

public class Padaria {

    public static final int MAX_PRODUTOS = 20;
    public static final int LIMITE_ESTOQUE_BAIXO = 5;

    public static final String[] DIAS = {
        "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom"
    };

    private String nome;
    private ArrayList<Produto> produtos;
    private ArrayList<String> historico;
    private int[][] vendas;
    private double[] faturamentoPorDia;

    public Padaria(String nome) {
        this.nome = nome;
        produtos = new ArrayList<>();
        historico = new ArrayList<>();
        vendas = new int[MAX_PRODUTOS][7];
        faturamentoPorDia = new double[7];
    }

    public String getNome() {
        return nome;
    }

    public String cadastrarProduto(String nome, String categoria,
                                   double preco, int quantidade) {

        if (nome == null || nome.trim().isEmpty()) {
            return "ERRO: o nome não pode ficar vazio.";
        }

        if (existeProdutoComNome(nome)) {
            return "ERRO: já existe um produto chamado " + nome + ".";
        }

        if (produtos.size() >= MAX_PRODUTOS) {
            return "ERRO: limite de 20 produtos atingido.";
        }

        if (preco <= 0) {
            return "ERRO: o preço deve ser maior que zero.";
        }

        if (quantidade < 0) {
            return "ERRO: a quantidade inicial não pode ser negativa.";
        }

        String categoriaFinal = (categoria == null || categoria.trim().isEmpty())
                ? "Sem categoria" : categoria.trim();

        Produto produto = new Produto(nome.trim(), categoriaFinal, preco, quantidade);
        produtos.add(produto);

        registrarNoHistorico("Cadastro: " + produto.getNome()
                + " (código " + produto.getCodigo() + ")");

        return "OK: produto cadastrado com o código "
                + produto.getCodigo() + ".";
    }

    public Produto buscarPorCodigo(int codigo) {
        int indice = indiceDoProduto(codigo);
        return indice == -1 ? null : produtos.get(indice);
    }

    public ArrayList<Produto> buscarPorNome(String trecho) {
        ArrayList<Produto> encontrados = new ArrayList<>();

        if (trecho == null) {
            return encontrados;
        }

        String busca = trecho.toLowerCase();

        for (Produto produto : produtos) {
            if (produto.getNome().toLowerCase().contains(busca)) {
                encontrados.add(produto);
            }
        }

        return encontrados;
    }

    public ArrayList<Produto> getProdutos() {
        return new ArrayList<>(produtos);
    }

    public int getQuantidadeDeProdutos() {
        return produtos.size();
    }

    public String registrarVenda(int codigo, int unidades, int dia) {

        if (!diaValido(dia)) {
            return "ERRO: dia inválido. Digite um número de 1 a 7.";
        }

        if (unidades <= 0) {
            return "ERRO: a quantidade vendida deve ser maior que zero.";
        }

        int indice = indiceDoProduto(codigo);

        if (indice == -1) {
            return "ERRO: produto de código " + codigo + " não encontrado.";
        }

        Produto produto = produtos.get(indice);

        if (unidades > produto.getQuantidadeEstoque()) {
            return "ERRO: estoque insuficiente. Disponível: "
                    + produto.getQuantidadeEstoque() + " unidade(s).";
        }

        double total = unidades * produto.getPreco();

        produto.retirarEstoque(unidades);
        vendas[indice][dia - 1] += unidades;
        faturamentoPorDia[dia - 1] += total;

        registrarNoHistorico("Venda: " + unidades + " x "
                + produto.getNome() + " (" + DIAS[dia - 1]
                + ") = " + moeda(total));

        return "OK: venda registrada. Total: " + moeda(total)
                + ". Estoque restante: " + produto.getQuantidadeEstoque() + ".";
    }

    public String reporEstoque(int codigo, int unidades) {

        if (unidades <= 0) {
            return "ERRO: a quantidade reposta deve ser maior que zero.";
        }

        Produto produto = buscarPorCodigo(codigo);

        if (produto == null) {
            return "ERRO: produto de código " + codigo + " não encontrado.";
        }

        produto.adicionarEstoque(unidades);

        registrarNoHistorico("Reposição: " + unidades
                + " unidade(s) de " + produto.getNome());

        return "OK: estoque de " + produto.getNome()
                + " agora é " + produto.getQuantidadeEstoque() + ".";
    }

    public String alterarPreco(int codigo, double novoPreco) {

        if (novoPreco <= 0) {
            return "ERRO: o preço deve ser maior que zero.";
        }

        Produto produto = buscarPorCodigo(codigo);

        if (produto == null) {
            return "ERRO: produto de código " + codigo + " não encontrado.";
        }

        produto.setPreco(novoPreco);

        registrarNoHistorico("Alteração de preço: "
                + produto.getNome() + " para " + moeda(novoPreco));

        return "OK: preço de " + produto.getNome()
                + " alterado para " + moeda(novoPreco) + ".";
    }

    public int getVendas(int posicaoProduto, int dia) {

        if (posicaoProduto < 0 || posicaoProduto >= produtos.size()
                || !diaValido(dia)) {
            return 0;
        }

        return vendas[posicaoProduto][dia - 1];
    }

    public int totalVendidoDoProduto(int posicaoProduto) {

        if (posicaoProduto < 0 || posicaoProduto >= produtos.size()) {
            return 0;
        }

        int total = 0;

        for (int dia = 0; dia < 7; dia++) {
            total += vendas[posicaoProduto][dia];
        }

        return total;
    }

    public int totalVendidoNoDia(int dia) {

        if (!diaValido(dia)) {
            return 0;
        }

        int total = 0;

        for (int i = 0; i < produtos.size(); i++) {
            total += vendas[i][dia - 1];
        }

        return total;
    }

    public double getFaturamentoDoDia(int dia) {

        if (!diaValido(dia)) {
            return 0;
        }

        return faturamentoPorDia[dia - 1];
    }

    public double getFaturamentoTotal() {

        double total = 0;

        for (double valor : faturamentoPorDia) {
            total += valor;
        }

        return total;
    }

    public Produto produtoMaisVendido() {

        if (produtos.isEmpty()) {
            return null;
        }

        int maior = 0;
        Produto maisVendido = null;

        for (int i = 0; i < produtos.size(); i++) {
            int total = totalVendidoDoProduto(i);

            if (total > maior) {
                maior = total;
                maisVendido = produtos.get(i);
            }
        }

        return maisVendido;
    }

    public int diaDeMaiorFaturamento() {

        double maior = 0;
        int diaMaior = 0;

        for (int i = 0; i < faturamentoPorDia.length; i++) {

            if (faturamentoPorDia[i] > maior) {
                maior = faturamentoPorDia[i];
                diaMaior = i + 1;
            }
        }

        return diaMaior;
    }

    public ArrayList<Produto> produtosComEstoqueBaixo() {

        ArrayList<Produto> baixos = new ArrayList<>();

        for (Produto produto : produtos) {

            if (produto.estoqueBaixo(LIMITE_ESTOQUE_BAIXO)) {
                baixos.add(produto);
            }
        }

        return baixos;
    }

    public double valorTotalEmEstoque() {

        double total = 0;

        for (Produto produto : produtos) {
            total += produto.valorEmEstoque();
        }

        return total;
    }

    public ArrayList<String> getHistorico() {
        return new ArrayList<>(historico);
    }

    private int indiceDoProduto(int codigo) {

        for (int i = 0; i < produtos.size(); i++) {

            if (produtos.get(i).getCodigo() == codigo) {
                return i;
            }
        }

        return -1;
    }

    private boolean existeProdutoComNome(String nome) {

        for (Produto produto : produtos) {

            if (produto.getNome().equalsIgnoreCase(nome.trim())) {
                return true;
            }
        }

        return false;
    }

    private boolean diaValido(int dia) {
        return dia >= 1 && dia <= 7;
    }

    private void registrarNoHistorico(String descricao) {

        String numero = String.format("%03d", historico.size() + 1);

        historico.add(numero + " | " + descricao);
    }

    private String moeda(double valor) {
        return String.format(
                java.util.Locale.forLanguageTag("pt-BR"),
                "R$ %.2f", valor
        );
    }
}
