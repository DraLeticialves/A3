
package PadariaPrincipal;

public class Aplicacao {
	

	public static void main(String[] args) {
	} 
    private static int proximoCodigo = 1;

    private int codigo;
    private String nome;
    private String categoria;
    private double preco;
    private int quantidadeEstoque;

    public Aplicacao(String nome, String categoria, double preco, int quantidadeEstoque) {
        this.codigo = proximoCodigo++;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }


    public int getQuantidade() {
        return quantidadeEstoque;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public void adicionarEstoque(int quantidade) {
        if (quantidade > 0) {
            quantidadeEstoque += quantidade;
        }
    }

    public boolean retirarEstoque(int quantidade) {
        if (quantidade <= 0 || quantidade > quantidadeEstoque) {
            return false;
        }

        quantidadeEstoque -= quantidade;
        return true;
    }

    public boolean removerEstoque(int quantidade) {
        return retirarEstoque(quantidade);
    }

    public boolean estoqueBaixo(int limite) {
        return quantidadeEstoque < limite;
    }

    public double valorEmEstoque() {
        return preco * quantidadeEstoque;
    }

    @Override
    public String toString() {
        return "Código: " + codigo
                + " | Nome: " + nome
                + " | Categoria: " + categoria
                + " | Preço: R$ " + String.format("%.2f", preco)
                + " | Estoque: " + quantidadeEstoque;
    }
}
