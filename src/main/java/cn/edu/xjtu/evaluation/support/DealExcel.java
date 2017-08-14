package cn.edu.xjtu.evaluation.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.edu.xjtu.evaluation.entity.Exercise;
import cn.edu.xjtu.evaluation.entity.ExerciseType;
import cn.edu.xjtu.evaluation.entity.Intervention;
import cn.edu.xjtu.evaluation.entity.Part;
import cn.edu.xjtu.evaluation.entity.Question;
import cn.edu.xjtu.evaluation.entity.Student;
import cn.edu.xjtu.evaluation.entity.Test;

public class DealExcel {

	public static List<String[]> loadInStudentInfo(Long cid, String filepath) throws IOException {
		// TODO Auto-generated method stub
		InputStream stream = new FileInputStream(filepath);
		String filetype = filepath.split("\\.")[filepath.split("\\.").length - 1];
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
		int i = 0;
		for (Row row : sheet) {
			if (i == 0) {
				i++;
				continue;
			}
			int j = 0;
			String[] s = new String[5];
			for (Cell cell : row) {
				if (j == 0) {
					j++;
					continue;
				}
				s[j - 1] = getCellCotent(cell);
				j++;
			}
			s_info.add(s);
		}
		return s_info;
	}

	public static String getCellCotent(Cell cell) {
		try {
			return cell.getStringCellValue();
		} catch (Exception e) {
			// TODO: handle exception
			try {
				double value = cell.getNumericCellValue();
				Integer v_int = (int) value;
				return v_int.toString();
			} catch (Exception e1) {
				return "";
			}
		}
	}

	public static List<Test> loadInTest(String filepath) throws IOException {
		// TODO Auto-generated method stub
		InputStream stream = new FileInputStream(filepath);
		String filetype = filepath.split("\\.")[filepath.split("\\.").length - 1];
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
		List<Test> tl = new ArrayList<Test>();
		Test t = null;
		Part p = null;
		Exercise e = null;
		Question q = null;
		Intervention i = null;
		for (Row row : sheet) {
			if (q == null) {
				q = new Question();
				q.setInterventions(new HashSet<Intervention>());
			}
			q.setOptions(q.getOptions() + "||" + getCellCotent(row.getCell(6)));
			if (getCellCotent(row.getCell(7)).replaceAll(" ", "").length() != 0) {
				i = new Intervention();
				i.setAudio_path(getCellCotent(row.getCell(8)));
				i.setText(getCellCotent(row.getCell(7)));
				q.getInterventions().add(i);
			} else {
				q.setAnswer(Integer.valueOf(getCellCotent(row.getCell(9))));
				e.getQuestions().add(q);
				q = null;
			}
			if (getCellCotent(row.getCell(5)).replaceAll(" ", "").length() != 0) {
				if (e != null)
					p.getExercises().add(e);
				e = new Exercise();
				e.setAudio_path(getCellCotent(row.getCell(5)));
				e.setText(getCellCotent(row.getCell(4)));
				e.setE_no(getCellCotent(row.getCell(3)));
				e.setQuestions(new HashSet<Question>());
			}
			if (getCellCotent(row.getCell(1)).replaceAll(" ", "").length() != 0) {
				if (p != null)
					t.getParts().add(p);
				p = new Part();
				ExerciseType et = new ExerciseType();
				et.setDescription(getCellCotent(row.getCell(1)));
				p.setExerciseType(et);
				p.setDesription(getCellCotent(row.getCell(2)));
				p.setExercises(new HashSet<Exercise>());
			}
			if (getCellCotent(row.getCell(0)).replaceAll(" ", "").length() != 0) {
				if (t != null)
					tl.add(t);
				t = new Test();
				t.setTitle(getCellCotent(row.getCell(0)));
				t.setParts(new HashSet<Part>());
			}
		}

		if (e != null) {
			p.getExercises().add(e);
		}
		if (p != null) {
			t.getParts().add(p);
		}
		if (t != null) {
			tl.add(t);
		}
		return tl;
	}

}