
package PadariaPrincipal;

import java.util.ArrayList;

public class RelatorioMiniPadaria {

    public static void imprimirEstoque(Padaria padaria) {
        Relatorio.imprimirEstoque(padaria);
    }

    public static void imprimirResultadoBusca(ArrayList<Produto> resultados, String termo) {
        Relatorio.imprimirResultadoBusca(resultados, termo);
    }

    public static void imprimirVendasDaSemana(Padaria padaria) {
        Relatorio.imprimirVendasDaSemana(padaria);
    }

    public static void imprimirEstoqueBaixo(Padaria padaria) {
        Relatorio.imprimirEstoqueBaixo(padaria);
    }

    public static void imprimirFechamento(Padaria padaria) {
        Relatorio.imprimirFechamento(padaria);
    }

    public static void imprimirHistorico(Padaria padaria) {
        Relatorio.imprimirHistorico(padaria);
    }
}
