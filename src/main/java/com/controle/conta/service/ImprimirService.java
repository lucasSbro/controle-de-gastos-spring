package com.controle.conta.service;

import com.controle.conta.dto.Conta;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImprimirService {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    List<Conta> contas = new ArrayList<>();

    public ImprimirService(List<Conta> contas) {
        this.contas = contas;
        workbook = new XSSFWorkbook();
    }
    private void writeHeader() {
        sheet = workbook.createSheet("Contas");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();

        // Defina os atributos da nova fonte
        font.setFontName("Arial"); // Define o tipo de fonte como Arial
        font.setFontHeightInPoints((short) 16); // Define o tamanho da fonte como 16 pontos
        font.setBold(true); // Define a fonte como negrito

        // Aplica a nova fonte ao estilo de célula
        style.setFont(font);

        // O restante do seu código permanece inalterado
        createCell(row, 0, "ID", style);
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Descrição", style);
        createCell(row, 2, "Valor", style);
        createCell(row, 3, "Entrada/Saida", style);

        // Define manualmente a largura das colunas
        sheet.setColumnWidth(0, 256 * 10); // Defina a largura da coluna 0 (ID) em 10 caracteres
        sheet.setColumnWidth(1, 256 * 20); // Defina a largura da coluna 1 (Descrição) em 20 caracteres
        sheet.setColumnWidth(2, 256 * 15); // Defina a largura da coluna 2 (Valor) em 15 caracteres
        sheet.setColumnWidth(3, 256 * 15); // Defina a largura da coluna 3 (Entrada/Saida) em 15 caracteres

    }
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {

        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }
    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();

        // Defina os atributos da nova fonte
        font.setFontName("Arial"); // Define o tipo de fonte como Arial
        font.setFontHeightInPoints((short) 14); // Define o tamanho da fonte como 14 pontos

        // Aplica a nova fonte ao estilo de célula
        style.setFont(font);
        for (Conta record: contas) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getId(), style);
            createCell(row, columnCount++, record.getGasto(), style);
            createCell(row, columnCount++, record.getValor().toString(), style);
            createCell(row, columnCount++, record.getPositivo() ? "+" : "-"   , style);
        }
    }
    public ByteArrayOutputStream generateExcelFile() throws IOException {
        writeHeader();
        write();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
        return outputStream;
    }
}
