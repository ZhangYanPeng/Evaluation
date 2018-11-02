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

import cn.edu.xjtu.evaluation.entity.TestResult;

public class PdfCreator {

	public static void createSingleReport(TestResult result, String path) throws Exception {
		// step 1
		Document document = new Document(PageSize.A4.rotate(), (float) 31.75, (float) 31.75, (float) 25.4, (float) 25.4);
		// step 2
		PdfWriter.getInstance(document, new FileOutputStream(path));
		// step 3
		document.open();
		
		SingleReportUtil.createTestReport(document);
		// step 4
		document.close();
	}
	
	public static void createOverallReport(TestResult result, String path) throws Exception {
		// step 1
		Document document = new Document(PageSize.A4.rotate(), (float) 31.75, (float) 31.75, (float) 20, (float) 20);
		// step 2
		PdfWriter.getInstance(document, new FileOutputStream(path));
		// step 3
		document.open();
		
		Anchor anchorTarget = new Anchor(" ");
		anchorTarget.setName("BackToTop");
		Paragraph paragraph1 = new Paragraph();
		paragraph1.add(anchorTarget);
		document.add(paragraph1);
		Paragraph title = new Paragraph(
				"OVERALL REPORT",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD,
						new CMYKColor(255, 255, 255, 0)));
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingBefore(50);
		document.add(title);
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
}
