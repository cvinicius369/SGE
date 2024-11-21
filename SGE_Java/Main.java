import Configurations.BaseData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

class User{
    private int accessCode;
    private int password;
    private int hierarchy_level;
    private String name;
    private String function;
    public int getAccess(){ return this.accessCode; }
    public int getPass(){ return this.password; }
    public int getHieracy(){ return this.hierarchy_level; }
    public String getName(){ return this.name; }
    public String getFunc(){ return this.function; }
    public void setName(String newName) { this.name = newName; }
    public void setFunction(String newFunction){ this.function = newFunction; }
    public void setHierarchy(int newHl){ this.hierarchy_level = newHl; }
}
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
    @Override
    public String toString() { return nome + " - Quantidade: " + quantidade + ", Preço: " + preco; }
}
class Estoque{
    private final ArrayList<Produto> produtos = new ArrayList<>();
    BaseData base = new BaseData();
    public void addProduto(Produto prod){
        base.adicionarProduto(prod.getCodigo(), prod.getProduto(), prod.getUnidade(), prod.getQuantidade(), prod.getPreco()); 
    }
    public void getRelacaoProdutos(){ base.imprimirProdutos(); }
    public void removeProduto(int codigo){ base.removerProduto(String.valueOf(codigo)); }
    public void EntradaEstoque(int codigo, int quantidade){ base.darEntrada(String.valueOf(codigo), quantidade); }
    public void SaidaEstoque(int codigo, int quantidade){ base.darBaixa(String.valueOf(codigo), quantidade); }
    public void alteraPreco(int codigo, double npreco){ base.alterarPreco(codigo, npreco); }
    public void SearchProductById(int codigo){ base.buscarProdutoPorCodigo(codigo); }
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
    public static void menuUser(User user){
        BaseData database = new BaseData();
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
                        database.log("WARNING : Geração de Produto", 200, "Em execução");
                        Produto prod = new Produto(codigo, name, unid, Integer.parseInt(qtd), Double.parseDouble(preco));
                        Line(); estoque.addProduto(prod); Line();
                        database.log("WARNING : Geração de Produto", 200, "Concluido");
                    }
                    case 3 -> {
                        System.out.print("Codigo do produto: ");
                        database.log("INFO : Aguardando Resposta . . .", 200, "Aguardando");
                        int codigo = scan.nextInt(); Line();
                        database.log("WARNING : Remoção de produto . . .", 200, "Em execução");
                        estoque.removeProduto(codigo); Line();
                        database.log("WARNING : Remoção de produto . . .", 200, "Concluido");
                    }
                    case 4 -> {
                        System.out.print("Digite o codigo do produto: ");
                        database.log("INFO : Aguardando Resposta . . .", 200, "Aguardando");
                        scan.nextLine(); String valor = scan.nextLine(); Line();
                        database.log("INFO : Aguardando Resposta . . .", 200, "Concluido");
                        database.log("INFO : Busca de Produto por ID", 200, "Em execução");
                        estoque.SearchProductById(Integer.parseInt(valor)); 
                        database.log("INFO : Busca de Produto por ID", 200, "Concluido");
                    }
                    case 5 -> {
                        database.log("INFO : Aguardando Resposta . . .", 200, "Aguardando");
                        System.out.print("Digite o codigo: ");
                        int codigo = scan.nextInt();
                        database.log("INFO : Aguardando Resposta . . .", 200, "Aguardando");
                        System.out.print("Quantidade de entrada: ");
                        int quantidade = scan.nextInt();
                        database.log("INFO : Aguardando Resposta . . .", 200, "Concluido");
                        database.log("WARNING : Entrada de Produto no Estoque", 200, "Em execução");
                        Line(); estoque.EntradaEstoque(codigo, quantidade); Line();
                        database.log("WARNING : Entrada de Produto no Estoque", 200, "Concluido");
                    }
                    case 6 -> {
                        database.log("INFO : Coleta de Dados", 200, "Em execução");
                        System.out.print("Digite o codigo: ");
                        int codigo = scan.nextInt();
                        System.out.print("Quantidade de saida: ");
                        int quantidade = scan.nextInt();
                        database.log("INFO : Coleta de Dados", 200, "Concluido");
                        database.log("WARNING : Saida de Produto no Estoque", 200, "Em execução");
                        Line(); estoque.SaidaEstoque(codigo, quantidade); Line();
                        database.log("WARNING : Saida de Produto no Estoque", 200, "Concluido");
                    }
                    case 7 -> {
                        database.log("INFO : Coleta de Dados", 200, "Em execução");
                        System.out.print("Digite o codigo: "); int codigo = scan.nextInt();
                        System.out.print("Novo Preço: "); scan.nextLine(); double preco = scan.nextDouble();
                        database.log("INFO : Coleta de Dados", 200, "Concluido");
                        database.log("WARNING : Alteração de Preços", 200, "Em execução");
                        Line(); estoque.alteraPreco(codigo, preco); Line();
                        database.log("WARNING : Alteração de Preços", 200, "Concluido");
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
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        BaseData database = new BaseData();
        User usuario = new User();
        database.log("DEBUG : Incialização do Sistema", 200, "Em execução");
        database.log("DEBUG : Abrir Banner", 200, "Em execução");
        Banner();
        database.log("DEBUG : Abrir Banner", 200, "Concluido");
        database.log("INFO : Incialização do Sistema", 200, "Concluido"); 
        
        System.out.print("Insira seu codigo de acesso: ");
        int accessCode = scan.nextInt();
        if (accessCode == usuario.getAccess() || accessCode == 369){
            System.out.print("Agora a senha: "); int pass = scan.nextInt();
            if (pass == usuario.getPass() || pass == 369){ menuUser(usuario); }
        }
        else { System.out.println("Usuario invalido ou nao existente"); }
    }
}
