import java.util.ArrayList;
import java.util.Scanner;

class Produto{
    private final String nome;
    private final String unidade;
    private int quantidade;
    private final int codigo;
    private double preco;
    /* [ CONSTRUTOR ] */
    public Produto(int codigo, String nome, String un, int qtd, double preco){
        this.codigo = codigo; this.nome = nome; this.unidade = un; this.quantidade = qtd; 
        this.preco = preco;
    }
    /* [ GETTERS ] */
    public int getCodigo(){ return this.codigo; }
    public String getProduto(){ return this.nome; }
    public String getUnidade(){ return this.unidade; }
    public int getQuantidade(){ return this.quantidade; }
    public double getPreco(){ return this.preco; }
    /* [ SETTERS ] */
    // Acho desnecessario o setter para o nome e unidade do produto ja que a ideia é que seja permanente
    public void setQuantidade(int newQuantidade){ this.quantidade = newQuantidade; }
    public void setPreco(double newPreco){ this.preco = newPreco; }
}
class Estoque{
    private final ArrayList<Produto> produtos = new ArrayList<>();
    public void addProduto(Produto prod){ this.produtos.add(prod); }
    public void getRelacaoProdutos(){ 
        for (Produto prod : this.produtos){
            System.out.print("Codigo: " + prod.getCodigo() + " | Produto: " + prod.getProduto());
            System.out.print(" | Tipo de Unidade: " + prod.getUnidade() + " | Quantidade: " + prod.getUnidade());
            System.out.println(" | Preço: " + prod.getPreco());
        }
     }
    public boolean removeProduto(int codigo){ 
        for (Produto prod : this.produtos){ if(prod.getCodigo() == codigo){ produtos.remove(prod); } }
        return false;
    }
    public boolean EntradaEstoque(int codigo, int quantidade){ 
        Produto produto = SearchProductById(codigo);
        if(produto != null){ produto.setQuantidade(produto.getQuantidade() + quantidade); return true; }
        return false;
    }
     public boolean SaidaEstoque(int codigo, int quantidade){ 
        Produto produto = SearchProductById(codigo);
        if(produto != null){ produto.setQuantidade(produto.getQuantidade() - quantidade); return true; }
        return false;
    }
    public boolean alteraPreco(int codigo, double preco){
        Produto produto = SearchProductById(codigo);
        if(produto != null){ produto.setPreco(preco); return true; }
        return false;
     }
    /* [ SEARCH FOR PRODUCTS ] */
    // Os dois tipos de buscas mais comuns (nome e codigo de cadastro)
    public Produto SearchProductById(int codigo){ 
        for (Produto prod : this.produtos){ if (prod.getProduto().equals(codigo)){ return prod; } }
        return null;
    }
    public Produto SearchProductByName(String name){ 
        for (Produto prod : this.produtos){ if (prod.getProduto().equalsIgnoreCase(name)){ return prod; } }
        return null;
    }
}
public class Main {
    public static void main(String[] args){
        try (Scanner scan = new Scanner(System.in)) {
            Estoque estoque = new Estoque();
            OUTER:
            while (true) {
                System.out.println("[1] - Listar Produtos    | [2] - Adicionar Produto");
                System.out.println("[3] - Remover Produto    | [4] - Pesquisar Produto");
                System.out.println("[5] - Entrada de Estoque | [6] - Saida de Estoque");
                System.out.print("[7] - Alterar preço      | [8] - Sair \n-> ");
                int decision = scan.nextInt();
                switch (decision) {
                    case 1 -> estoque.getRelacaoProdutos();
                    case 2 -> {
                        System.out.print("Codigo: ");
                        int codigo = scan.nextInt();
                        scan.nextLine();
                        System.out.print("Nome do produto: ");
                        String name = scan.nextLine();
                        System.out.print("Tipo de unidade: ");
                        String unid = scan.next();
                        scan.nextLine();
                        System.out.print("Quantidade: ");
                        String qtd = scan.nextLine();
                        System.out.print("Preço por unidade: ");
                        String preco = scan.nextLine();
                        Produto prod = new Produto(codigo, name, unid, Integer.parseInt(qtd), Double.parseDouble(preco));
                        estoque.addProduto(prod);
                    }
                    case 3 -> {
                        System.out.print("Codigo do produto: ");
                        int codigo = scan.nextInt();
                        estoque.removeProduto(codigo);
                    }
                    case 4 -> {
                        System.out.print("Digite o codigo do produto: ");
                        String valor = scan.nextLine();
                        Produto pesquisa = estoque.SearchProductById(Integer.parseInt(valor));
                        System.out.println("Codigo: " + pesquisa.getCodigo() + "\nProduto: " + pesquisa.getProduto());
                        System.out.println("Tipo de Unidade: " + pesquisa.getUnidade() + "\nQuantidade: " + pesquisa.getUnidade());
                        System.out.println("Preço: " + pesquisa.getPreco());
                    }
                    case 5 -> {
                        System.out.print("Digite o codigo: ");
                        int codigo = scan.nextInt();
                        System.out.print("Quantidade de entrada: ");
                        int quantidade = scan.nextInt();
                        estoque.EntradaEstoque(codigo, quantidade);
                    }
                    case 6 ->                     {
                        System.out.print("Digite o codigo: ");
                        int codigo = scan.nextInt();
                        System.out.print("Quantidade de saida: ");
                        int quantidade = scan.nextInt();
                        estoque.SaidaEstoque(codigo, quantidade);
                    }
                    case 7 -> {
                        System.out.print("Digite o codigo: ");
                        int codigo = scan.nextInt();
                        System.out.print("Novo Preço: ");
                        double preco = scan.nextDouble();
                        estoque.alteraPreco(codigo, preco);
                    }
                    case 8 -> {
                        System.out.println("Até breve! ");
                        break OUTER;
                    }
                    default -> {
                    }
                }
            }
        }
    }
}
