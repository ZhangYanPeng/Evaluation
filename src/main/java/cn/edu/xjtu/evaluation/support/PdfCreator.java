package cn.edu.xjtu.evaluation.support;

import java.io.FileOutputStream;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.CMYKColor;
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
		
		Anchor anchorTarget = new Anchor(" ");
		anchorTarget.setName("BackToTop");
		Paragraph paragraph1 = new Paragraph();
		paragraph1.add(anchorTarget);
		document.add(paragraph1);
		Paragraph title = new Paragraph(
				"TEST RESULT REPORT",
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD,
						new CMYKColor(255, 255, 255, 0)));
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingBefore(50);
		document.add(title);
		// step 4
		document.close();
	}
	
	public static void createOverallReport(TestResult result, String path) throws Exception {
		// step 1
		Document document = new Document(PageSize.A4.rotate(), (float) 31.75, (float) 31.75, (float) 25.4, (float) 25.4);
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
}
