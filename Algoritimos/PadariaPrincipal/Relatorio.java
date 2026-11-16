
package PadariaPrincipal;

import java.util.ArrayList;
import java.util.Locale;

public class Relatorio {

    private static final int LARGURA = 72;

    private Relatorio() {
        
    }

    public static void imprimirEstoque(Padaria padaria) {
        titulo("RELATÓRIO DE ESTOQUE");

        System.out.println("Padaria: " + padaria.getNome());
        linha();

        System.out.printf("%-6s %-24s %-15s %10s %10s%n",
                "Código", "Produto", "Categoria", "Preço", "Estoque");

        linha();

        ArrayList<Produto> produtos = padaria.getProdutos();

        for (Produto produto : produtos) {
            System.out.printf("%-6d %-24s %-15s %10s %10d%n",
                    produto.getCodigo(),
                    limitar(produto.getNome(), 24),
                    limitar(produto.getCategoria(), 15),
                    moeda(produto.getPreco()),
                    produto.getQuantidadeEstoque());
        }

        linha();

        System.out.println("Quantidade de produtos: " + produtos.size());
        System.out.println("Valor total em estoque: "
                + moeda(padaria.valorTotalEmEstoque()));

        rodape();
    }

    public static void imprimirResultadoBusca(
            ArrayList<Produto> resultados, String termo) {

        titulo("RESULTADO DA BUSCA");

        System.out.println("Termo pesquisado: " + termo);
        linha();

        if (resultados == null || resultados.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
        } else {
            for (Produto produto : resultados) {
                System.out.println(produto);
            }

            System.out.println();
            System.out.println("Total encontrado: " + resultados.size());
        }

        rodape();
    }

    public static void imprimirVendasDaSemana(Padaria padaria) {
        titulo("RELATÓRIO DE VENDAS DA SEMANA");

        ArrayList<Produto> produtos = padaria.getProdutos();

        System.out.println("Padaria: " + padaria.getNome());
        linha();

        System.out.printf("%-22s", "Produto");

        for (String dia : Padaria.DIAS) {
            System.out.printf("%7s", dia);
        }

        System.out.printf("%9s%n", "Total");
        linha();

        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);

            System.out.printf("%-22s",
                    limitar(produto.getNome(), 22));

            int totalProduto = 0;

            for (int dia = 1; dia <= 7; dia++) {
                int quantidade = padaria.getVendas(i, dia);

                System.out.printf("%7d", quantidade);
                totalProduto += quantidade;
            }

            System.out.printf("%9d%n", totalProduto);
        }

        linha();

        System.out.printf("%-22s", "Total por dia");

        for (int dia = 1; dia <= 7; dia++) {
            System.out.printf("%7d",
                    padaria.totalVendidoNoDia(dia));
        }

        System.out.println();
        linha();

        System.out.println("Faturamento semanal: "
                + moeda(padaria.getFaturamentoTotal()));

        System.out.println("Produto mais vendido: "
                + nomeProduto(padaria.produtoMaisVendido()));

        int melhorDia = padaria.diaDeMaiorFaturamento();

        if (melhorDia >= 1 && melhorDia <= 7) {
            System.out.println("Dia de maior faturamento: "
                    + Padaria.DIAS[melhorDia - 1]
                    + " - "
                    + moeda(padaria.getFaturamentoDoDia(melhorDia)));
        } else {
            System.out.println("Dia de maior faturamento: Sem vendas");
        }

        rodape();
    }

    public static void imprimirEstoqueBaixo(Padaria padaria) {
        titulo("PRODUTOS COM ESTOQUE BAIXO");

        ArrayList<Produto> produtos =
                padaria.produtosComEstoqueBaixo();

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto com estoque baixo.");
        } else {
            System.out.println("Produtos com menos de "
                    + Padaria.LIMITE_ESTOQUE_BAIXO + " unidades:");
            linha();

            for (Produto produto : produtos) {
                System.out.println(
                        produto.getCodigo() + " - "
                        + produto.getNome()
                        + " | Estoque: "
                        + produto.getQuantidadeEstoque());
            }

            System.out.println();
            System.out.println("Total de produtos com estoque baixo: "
                    + produtos.size());
        }

        rodape();
    }

    public static void imprimirFechamento(Padaria padaria) {
        titulo("FECHAMENTO DA PADARIA");

        System.out.println("Estabelecimento: " + padaria.getNome());
        linha();

        System.out.println("Quantidade de produtos cadastrados: "
                + padaria.getProdutos().size());

        System.out.println("Valor total em estoque: "
                + moeda(padaria.valorTotalEmEstoque()));

        System.out.println("Faturamento da semana: "
                + moeda(padaria.getFaturamentoTotal()));

        System.out.println("Produto mais vendido: "
                + nomeProduto(padaria.produtoMaisVendido()));

        int melhorDia = padaria.diaDeMaiorFaturamento();

        if (melhorDia >= 1 && melhorDia <= 7) {
            System.out.println("Melhor dia: "
                    + Padaria.DIAS[melhorDia - 1]);
        } else {
            System.out.println("Melhor dia: Sem vendas");
        }

        ArrayList<Produto> estoqueBaixo =
                padaria.produtosComEstoqueBaixo();

        System.out.println("Produtos com estoque baixo: "
                + estoqueBaixo.size());

        System.out.println("Operações registradas: "
                + padaria.getHistorico().size());

        rodape();
    }

    public static void imprimirHistorico(Padaria padaria) {
        titulo("HISTÓRICO DE OPERAÇÕES");

        ArrayList<String> historico = padaria.getHistorico();

        if (historico.isEmpty()) {
            System.out.println("Nenhuma operação registrada.");
        } else {
            for (String operacao : historico) {
                System.out.println(operacao);
            }

            linha();
            System.out.println("Total de operações: " + historico.size());
        }

        rodape();
    }

    private static void titulo(String texto) {
        linha();
        System.out.println(texto);
        linha();
    }

    private static void linha() {
        System.out.println("=".repeat(LARGURA));
    }

    private static void rodape() {
        linha();
        System.out.println();
    }

    private static String moeda(double valor) {
        return String.format(
                Locale.forLanguageTag("pt-BR"),
                "R$ %,.2f",
                valor);
    }

    private static String limitar(String texto, int tamanho) {
        if (texto == null) {
            return "";
        }

        if (texto.length() <= tamanho) {
            return texto;
        }

        return texto.substring(0, tamanho - 3) + "...";
    }

    private static String nomeProduto(Produto produto) {
        if (produto == null) {
            return "Nenhum";
        }

        return produto.getNome();
    }
}
