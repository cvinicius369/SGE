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
    // Local onde o arquivo log sera armazenado
    private final String LOG_FILE = ".\\events.log";
    // Metodo para registrar o log
    public void log(String msg, int cod, String status){
        try (FileWriter fileWriter = new FileWriter(this.LOG_FILE, true);) {
            PrintWriter printWriter = new PrintWriter(fileWriter);
            write_log(printWriter, msg, cod, status);
        } catch (IOException e){
            System.err.println("Erro ao escrever arquivo log: " + e.getMessage());
        }
    }
    // Metodo que configura o registro de log
    public static void write_log(PrintWriter printWriter, String msg, int code, String status){
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        printWriter.printf("%s - %s - %s - %d%n", timestamp, msg, status, code);
    }

    public void writeToCSV(String newValue, int columnIndex, int rowIndex) throws IOException {
        File csvFile = new File(".\\userData.csv");
        List<String[]> lines = new ArrayList<>();
    
        // Ler o arquivo CSV
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line.split(";"));
            }
        }  
    
        // Escrever de volta para o arquivo CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
            for (String[] row : lines) {
                bw.write(String.join(";", row));
                bw.newLine();
            }
        }
    }
}
