package cn.edu.xjtu.evaluation.support;

import java.io.File;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DealExcel {
	public static void main(String[] args) {
		try {
			loadInStudentInfo((long) 1,"F:\\Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\Evaluation\\upload\\stu_list\\2110.xlsx");
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void loadInStudentInfo(Long cid, String filepath) throws BiffException, IOException {
		// TODO Auto-generated method stub
		File xlsFile = new File(filepath);
		// 获得工作簿对象
		XSSFWorkbook  workbook = Workbook.getWorkbook(xlsFile);
		// 获得所有工作表
		Sheet[] sheets = workbook.getSheets();
		// 遍历工作表
		if (sheets != null) {
			for (Sheet sheet : sheets) {
				// 获得行数
				int rows = sheet.getRows();
				// 获得列数
				int cols = sheet.getColumns();
				// 读取数据
				for (int row = 0; row < rows; row++) {
					for (int col = 0; col < cols; col++) {
						System.out.printf("%10s", sheet.getCell(col, row).getContents());
					}
					System.out.println();
				}
			}
		}
		workbook.close();

	}

}
