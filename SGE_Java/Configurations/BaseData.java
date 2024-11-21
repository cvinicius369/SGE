package Configurations;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BaseData {
    private final String LOG_FILE = ".\\events.log";
    private static final String FILE_PATH = "Configurations/produtos.csv";
    private static final String USER_FILE = "Configurations/users.csv";
    public void log(String msg, int cod, String status){
        try (FileWriter fileWriter = new FileWriter(this.LOG_FILE, true);) {
            PrintWriter printWriter = new PrintWriter(fileWriter);
            write_log(printWriter, msg, cod, status);
        } 
        catch (IOException e){ System.err.println("Erro ao escrever arquivo log: " + e.getMessage()); }
    }
    public static void write_log(PrintWriter printWriter, String msg, int code, String status){
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        printWriter.printf("%s - %s - %s - %d%n", timestamp, msg, status, code);
    }
    public void writeToCSV(String newValue, int columnIndex, int rowIndex) throws IOException {
        File csvFile = new File(".\\userData.csv");
        List<String[]> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line; while ((line = br.readLine()) != null) { lines.add(line.split(";")); }
        }  
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
            for (String[] row : lines) { bw.write(String.join(";", row)); bw.newLine(); }
        }
    }
    public void adicionarProduto(int codigo, String nome, String unidade, int quantidade, double preco) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.append(String.valueOf(codigo)).append(",").append(nome).append(",").append(unidade)
            .append(",").append(String.valueOf(quantidade)).append(",").append(String.valueOf(preco))
            .append("\n");
        } catch (IOException e) { e.printStackTrace(); }
    }
    public List<String[]> listarProdutos() { 
        List<String[]> produtos = new ArrayList<>(); 
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) { 
            String linha; boolean primeiraLinha = true; 
            while ((linha = reader.readLine()) != null) { 
                if (primeiraLinha) { primeiraLinha = false; continue; } 
                String[] dados = linha.split(","); 
                if (dados.length == 5) { produtos.add(dados); } 
            } 
        } catch (IOException e) { e.printStackTrace(); } 
        return produtos; 
    } 
    public void imprimirProdutos() {
        List<String[]> produtos = listarProdutos();
        for (String[] produto : produtos) { 
            System.out.print("Código: " + produto[0] + " | "); System.out.print("Produto: " + produto[1] + " | "); 
            System.out.print("Tipo de Unidade: " + produto[2] + " | "); System.out.print("Quantidade: " + produto[3] + " | "); 
            System.out.print("Preço: " + produto[4] + " | "); System.out.println();
        }
    }
    public void removerProduto(String codigo) { 
        List<String[]> produtos = listarProdutos(); 
        produtos.removeIf(produto -> produto[0].equals(codigo)); 
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) { 
            String headerLine = reader.readLine(); 
            try (FileWriter writer = new FileWriter(FILE_PATH)) { 
                if (headerLine != null) { writer.append(headerLine).append("\n"); } 
                for (String[] produto : produtos) { 
                    writer.append(produto[0]) .append(",") .append(produto[1]) .append(",") 
                    .append(produto[2]) .append(",") .append(produto[3]) .append(",") .append(produto[4]) .append("\n"); 
                } 
            } catch (IOException e) { e.printStackTrace(); } 
        } catch (IOException e) { e.printStackTrace(); } 
    }
    public void alterarProduto(int codigo, String novoNome, String novaUnidade, int novaQuantidade, double novoPreco) {
        List<String[]> produtos = listarProdutos();
        for (String[] produto : produtos) {
            if (produto[0].equals(String.valueOf(codigo))) {
                produto[1] = novoNome; produto[2] = novaUnidade; produto[3] = String.valueOf(novaQuantidade); 
                produto[4] = String.valueOf(novoPreco);
                break;
            }
        }
        salvarProdutos(produtos);
    }
    public void alterarPreco(int codigo, double novoPreco) {
        List<String[]> produtos = listarProdutos();
        for (String[] produto : produtos) {
            if (produto[0].equals(String.valueOf(codigo))) { produto[4] = String.valueOf(novoPreco); break; }
        }
        salvarProdutos(produtos);
    }
    public void darBaixa(String codigo, int quantidade) {
        List<String[]> produtos = listarProdutos();
        for (String[] produto : produtos) {
            if (produto[0].equals(codigo)) {
                int quantidadeAtual = Integer.parseInt(produto[3]);
                if (quantidadeAtual >= quantidade) { produto[3] = String.valueOf(quantidadeAtual - quantidade); } 
                else { System.out.println("Quantidade insuficiente em estoque!"); }
                break;
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String headerLine = reader.readLine(); 
            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                if (headerLine != null) { writer.append(headerLine).append("\n"); }
                for (String[] produto : produtos) { 
                    writer.append(produto[0]).append(",").append(produto[1]).append(",").append(produto[2])
                    .append(",").append(produto[3]).append(",").append(produto[4]).append("\n");
                }
            } catch (IOException e) { e.printStackTrace(); }
        } catch (IOException e) { e.printStackTrace(); }
    }    
    public void darEntrada(String codigo, int quantidade) {
        List<String[]> produtos = listarProdutos();
        for (String[] produto : produtos) {
            if (produto[0].equals(codigo)) {
                int quantidadeAtual = Integer.parseInt(produto[3]); produto[3] = String.valueOf(quantidadeAtual + quantidade); 
                break;
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String headerLine = reader.readLine();
            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                if (headerLine != null) { writer.append(headerLine).append("\n"); }
                for (String[] produto : produtos) {
                    writer.append(produto[0]).append(",").append(produto[1]).append(",")
                    .append(produto[2]).append(",").append(produto[3]).append(",").append(produto[4]).append("\n");
                }
            } catch (IOException e) { e.printStackTrace(); }
        } catch (IOException e) { e.printStackTrace(); }
    }    
    private void salvarProdutos(List<String[]> produtos) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (String[] produto : produtos) {
                writer.append(produto[0]).append(",").append(produto[1]).append(",")
                .append(produto[2]).append(",").append(produto[3]).append(",")
                .append(produto[4]).append("\n");
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    public void buscarProdutoPorCodigo(int codigo) {
        String linha;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            while ((linha = br.readLine()) != null) { String[] dados = linha.split(",");
                if (dados[0].equals(String.valueOf(codigo))) {
                    System.out.print("Código: " + dados[0] + " | "); System.out.print("Produto: " + dados[1] + " | "); 
                    System.out.print("Tipo de Unidade: " + dados[2] + " | "); System.out.print("Quantidade: " + dados[3] + " | "); 
                    System.out.print("Preço: " + dados[4] + " | "); System.out.println();
                }
            }
        } catch (IOException e) { System.out.println("Erro ao ler o arquivo: " + e.getMessage()); }
    }
    public void addUser(int access, int password, int hl, String name, String func) {
        try (FileWriter writer = new FileWriter(USER_FILE, true)) {
            writer.append(String.valueOf(access)).append(",").append(name).append(",").append(String.valueOf(password))
            .append(",").append(String.valueOf(hl)).append(",").append(func)
            .append("\n");
        } catch (IOException e) { e.printStackTrace(); }
    }
}
