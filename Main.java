
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(new FileInputStream("input.txt"));
		System.out.println("Processing...");
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Output");
		CellStyle wrapCellStyle = workbook.createCellStyle();
		wrapCellStyle.setWrapText(true);
		int rowNum = 1;
		while (scanner.hasNext()) {
			String input = scanner.nextLine();
			if (input == "")
				continue;
			String[] sentences = input.split("\\.");
			for (String sentence : sentences) {
				Row row = sheet.createRow(rowNum++);
				Cell cell = row.createCell(0);
				if (sentence.trim().isEmpty()) {
					continue;
				}
				cell.setCellValue(cell.getStringCellValue() + sentence.trim() + ".");
				if (sentence.endsWith(".")) {
					rowNum++;
					row = sheet.createRow(rowNum);
					cell = row.createCell(0);
				}
				sheet.autoSizeColumn(0);
				rowNum = rowNum + 2;
			}
		}

		try {
			FileOutputStream outputStream = new FileOutputStream("output.xls");
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();
			System.out.println("Done !");
		} catch (IOException e) {
			System.out.println("Error " + e);
		}
		scanner.close();
	}
}
