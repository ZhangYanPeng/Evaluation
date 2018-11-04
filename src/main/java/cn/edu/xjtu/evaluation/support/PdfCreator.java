package cn.edu.xjtu.evaluation.support;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;

import cn.edu.xjtu.evaluation.entity.EvaluationResult;
import cn.edu.xjtu.evaluation.entity.OverallReport;
import cn.edu.xjtu.evaluation.entity.TestResult;

public class PdfCreator {

	public static void createSingleReport(TestResult tr, EvaluationResult er, String path, String path_tmp) throws Exception {
		// step 1
		Document document = new Document(PageSize.A4.rotate(), (float) 31.75, (float) 31.75, (float) 25.4, (float) 25.4);
		// step 2
		PdfWriter.getInstance(document, new FileOutputStream(path));
		// step 3
		document.open();
		
		SingleReportUtil.createTestReport(document, tr, path_tmp);
		SingleReportUtil.createEvaluationReport(document, er, path_tmp);
		// step 4
		document.close();
	}
	
	public static void createOverallReport(OverallReport overallReport, String path, String path_tmp) throws Exception {
		// step 1
		Document document = new Document(PageSize.A4.rotate(), (float) 31.75, (float) 31.75, (float) 20, (float) 20);
		// step 2
		PdfWriter.getInstance(document, new FileOutputStream(path));
		// step 3
		document.open();
		OverallReportUtil.createOverallReport(document,overallReport,path_tmp);
		// step 4
		document.close();
	}
	
	public static PdfPCell FormatTableCell(String text, int Ver_Align, int Hor_Align, BaseColor tcolor, BaseColor bcolor) throws DocumentException, IOException{
		BaseFont bfCn_title = BaseFont.createFont("C:/WINDOWS/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
		Font FontCn_title = new Font(bfCn_title, 12, Font.NORMAL, tcolor );  
		
		Paragraph p = new Paragraph(text, FontCn_title);
		p.setAlignment(Element.ALIGN_CENTER);
		PdfPCell pc = new PdfPCell(p);
		pc.setUseAscender(true);
		pc.setVerticalAlignment(Ver_Align);
		pc.setHorizontalAlignment(Hor_Align);
		pc.setBackgroundColor(bcolor);
		pc.setFixedHeight(25);
		pc.setBorderColor(BaseColor.WHITE );
		return pc;
	}
	
	public static PdfPCell FormatTableTitleCell(String text, BaseColor tcolor, BaseColor bcolor) throws DocumentException, IOException{
		BaseFont bfCn_title = BaseFont.createFont("C:/WINDOWS/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
		Font FontCn_title = new Font(bfCn_title, 14, Font.BOLD, tcolor );  
		
		Paragraph p = new Paragraph(text, FontCn_title);
		p.setAlignment(Element.ALIGN_CENTER);
		PdfPCell pc = new PdfPCell(p);
		pc.setUseAscender(true);
		pc.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pc.setHorizontalAlignment(Element.ALIGN_CENTER);
		pc.setBackgroundColor(bcolor);
		pc.setFixedHeight(35);
		pc.setBorderColor(BaseColor.WHITE );
		return pc;
	}

	public static PdfPCell FormatMiniTableTitleCell(String text, BaseColor tcolor, BaseColor bcolor) throws DocumentException, IOException {
		BaseFont bfCn_title = BaseFont.createFont("C:/WINDOWS/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
		Font FontCn_title = new Font(bfCn_title, 8, Font.BOLD, tcolor );  
		
		Paragraph p = new Paragraph(text, FontCn_title);
		p.setAlignment(Element.ALIGN_CENTER);
		PdfPCell pc = new PdfPCell(p);
		pc.setUseAscender(true);
		pc.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pc.setHorizontalAlignment(Element.ALIGN_CENTER);
		pc.setBackgroundColor(bcolor);
		pc.setFixedHeight(50);
		pc.setBorderColor(BaseColor.WHITE );
		return pc;
	}
}
