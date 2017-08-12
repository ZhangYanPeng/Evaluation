package cn.edu.xjtu.evaluation.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class DealExcel {
	public static void main(String[] args) {
		try {
			loadInStudentInfo((long) 1,
					"F:\\Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\Evaluation\\upload\\stu_list\\2110.xlsx");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void loadInStudentInfo(Long cid, String filepath) throws IOException {
		// TODO Auto-generated method stub
		InputStream stream = new FileInputStream(filepath);
		Workbook wb = null;
		if (filepath.split(".")[1].equals("xls")) {
			wb = new HSSFWorkbook(stream);
		} else if (filepath.split(".")[1].equals("xlsx")) {
			wb = new XSSFWorkbook(stream);
		} else {
			System.out.println("您输入的excel格式不正确");
		}
		Sheet sheet1 = wb.getSheetAt(0);
		for (Row row : sheet1) {
			for (Cell cell : row) {
				System.out.print(cell.getStringCellValue() + "  ");
			}
			System.out.println();
		}
	}

}
