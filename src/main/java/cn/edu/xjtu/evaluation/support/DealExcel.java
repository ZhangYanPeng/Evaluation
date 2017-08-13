package cn.edu.xjtu.evaluation.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.edu.xjtu.evaluation.entity.Student;

public class DealExcel {

	public static List<String[]> loadInStudentInfo(Long cid, String filepath) throws IOException {
		// TODO Auto-generated method stub
		InputStream stream = new FileInputStream(filepath);
		String filetype = filepath.split("\\.")[filepath.split("\\.").length-1];
		Workbook wb = null;
		if (filetype.equals("xls")) {
			wb = new HSSFWorkbook(stream);
		} else if (filetype.equals("xlsx")) {
			wb = new XSSFWorkbook(stream);
		} else {
			System.out.println("您输入的excel格式不正确");
		}
		
		List s_info = new ArrayList();
		
		Sheet sheet = wb.getSheetAt(0);
		int i = 0 ;
		for (Row row : sheet) {
			if( i==0 ){
				i++;
				continue;
			}
			int j = 0 ;
			String[] s = new String[5];
			for(Cell cell : row){
				if( j==0 ){
					j++;
					continue;
				}
				s[j-1]=getCellCotent(cell);
				j++;
			}
			s_info.add(s);
		}
		return s_info;
	}

	public static String getCellCotent(Cell cell){
		try {
			return cell.getStringCellValue();
		} catch (Exception e) {
			// TODO: handle exception
			double value = cell.getNumericCellValue();
			Integer v_int = (int) value;
			return v_int.toString();
		}
	}
		

}