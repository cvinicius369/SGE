import Configurations.BaseData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
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
            System.out.print(" | Tipo de Unidade: " + prod.getUnidade() + " | Quantidade: " + prod.getQuantidade());
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
        for (Produto prod : this.produtos){ if (prod.getCodigo() == codigo){ return prod; } }
        return null;
    }
    public Produto SearchProductByName(String name){ 
        for (Produto prod : this.produtos){ if (prod.getProduto().equalsIgnoreCase(name)){ return prod; } }
        return null;
    }
}
public class Main {
    public static void Banner(){
        System.out.println("---------------------------------------------------");
        System.out.println("          S G E  - J A V A  V E R S I O N          ");
        System.out.println("---------------------------------------------------");
    }
    public static void Line(){
        System.out.println("---------------------------------------------------");
    }
    public static void main(String[] args) throws IOException {
        Banner();
        BaseData database = new BaseData();
        database.log("DEBUG : Incialização do Sistema", 200, "Em execução");
        try (Scanner scan = new Scanner(System.in).useLocale(Locale.US);) {
            Estoque estoque = new Estoque();
            OUTER:
            while (true) {
                database.log("DEBUG : Abrindo menu do sistema", 200, "Em execução");
                System.out.println("[1] - Listar Produtos    | [2] - Adicionar Produto");
                System.out.println("[3] - Remover Produto    | [4] - Pesquisar Produto");
                System.out.println("[5] - Entrada de Estoque | [6] - Saida de Estoque");
                System.out.print("[7] - Alterar preço      | [8] - Sair \n-> ");
                database.log("INFO : Aguardando Resposta . . .", 200, "Aguardando");
                int decision = scan.nextInt();
                database.log("INFO : Resposta do usuario: " + decision, 200, "Concluido");
                Line();
                switch (decision) {
                    case 1 -> { 
                        database.log("INFO : Geração Relação de Produtos", 200, "Em execução");
                        estoque.getRelacaoProdutos(); Line(); 
                        database.log("INFO : Geração Relação de Produtos", 200, "Concluido");
                    }
                    case 2 -> {
                        database.log("INFO : Coleta de Informações", 200, "Em execução");
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
                        database.log("INFO : Coleta de Informações", 200, "Concluido");
                        database.log("DEBUG : Geração de Produto", 200, "Em execução");
                        Produto prod = new Produto(codigo, name, unid, Integer.parseInt(qtd), Double.parseDouble(preco));
                        Line(); estoque.addProduto(prod); Line();
                        database.log("INFO : Geração de Produto", 200, "Concluido");
                    }
                    case 3 -> {
                        System.out.print("Codigo do produto: ");
                        int codigo = scan.nextInt(); Line();
                        estoque.removeProduto(codigo); Line();
                    }
                    case 4 -> {
                        System.out.print("Digite o codigo do produto: ");
                        scan.nextLine();
                        String valor = scan.nextLine(); Line();
                        Produto pesquisa = estoque.SearchProductById(Integer.parseInt(valor));
                        if (pesquisa != null) {
                            System.out.println("Codigo: " + pesquisa.getCodigo() + "\nProduto: " + pesquisa.getProduto());
                            System.out.println("Tipo de Unidade: " + pesquisa.getUnidade() + "\nQuantidade: " + pesquisa.getQuantidade());
                            System.out.println("Preço: " + pesquisa.getPreco()); Line();
                        } 
                        else { System.out.println("Produto não encontrado."); Line(); }
                    }
                    case 5 -> {
                        System.out.print("Digite o codigo: ");
                        int codigo = scan.nextInt();
                        System.out.print("Quantidade de entrada: ");
                        int quantidade = scan.nextInt();
                        Line(); estoque.EntradaEstoque(codigo, quantidade); Line();
                    }
                    case 6 ->                     {
                        System.out.print("Digite o codigo: ");
                        int codigo = scan.nextInt();
                        System.out.print("Quantidade de saida: ");
                        int quantidade = scan.nextInt();
                        Line(); estoque.SaidaEstoque(codigo, quantidade); Line();
                    }
                    case 7 -> {
                        System.out.print("Digite o codigo: ");
                        int codigo = scan.nextInt();
                        System.out.print("Novo Preço: "); scan.nextLine();
                        double preco = scan.nextDouble();
                        Line(); estoque.alteraPreco(codigo, preco); Line();
                    }
                    case 8 -> { 
                        database.log("INFO : Abortar Sistema", 200, "Em execução");
                        System.out.println("Até breve! "); Line(); break OUTER; 
                    }
                    default -> { 
                        database.log("WARNING : Resposta não reconhecida", 200, "Erro");
                        System.out.println("ERRO DE INPUT "); 
                    }
                }
            }
        } 
    }
}
