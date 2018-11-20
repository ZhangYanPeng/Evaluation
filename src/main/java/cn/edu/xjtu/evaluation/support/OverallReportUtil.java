package cn.edu.xjtu.evaluation.support;

import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import cn.edu.xjtu.evaluation.entity.OverallReport;

public class OverallReportUtil {
	
	public static BaseColor tab_title = new BaseColor(91, 155, 213);
	public static BaseColor tab_odd = new BaseColor(210, 222, 239);
	public static BaseColor tab_even = new BaseColor(234, 239, 247);
	
	public static void createOverallReport(Document document, OverallReport overallReport, String path_tmp) throws DocumentException, IOException {
		BaseFont bfCn_title = BaseFont.createFont("C:/WINDOWS/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H,
				BaseFont.NOT_EMBEDDED);
		Font FontCn_title = new Font(bfCn_title, 22, Font.BOLD, SingleReportUtil.tab_title);
		Paragraph title = new Paragraph("总结报告", FontCn_title);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingAfter(25);
		document.add(title);
		
		PdfPTable whole_table = new PdfPTable(2);
		whole_table.setWidthPercentage(100);
		
		//单词测试表格
		{
			PdfPTable test_table = new PdfPTable(7);
			test_table.setWidthPercentage(100);
			test_table.setWidths(new float[] { (float) 0.28, (float) 0.12, (float) 0.12, (float) 0.12, (float) 0.12, (float) 0.12, (float) 0.12 });
			PdfPCell cell0 = PdfCreator.FormatMiniTableTitleCell("", BaseColor.BLACK, OverallReportUtil.tab_title);
			cell0.setRowspan(2);
			test_table.addCell(cell0);
			for(int i=0; i<3; i++){
				PdfPCell tncell = PdfCreator.FormatMiniTableTitleCell(overallReport.getTest_name()[i], BaseColor.BLACK, OverallReportUtil.tab_title);
				tncell.setColspan(2);
				test_table.addCell(tncell);
			}
			test_table.addCell(PdfCreator.FormatMiniTableTitleCell("原始\n成绩\n（NDA1）", BaseColor.BLACK, OverallReportUtil.tab_title));
			test_table.addCell(PdfCreator.FormatMiniTableTitleCell("动态\n评估\n成绩\n（DA1）", BaseColor.BLACK, OverallReportUtil.tab_title));
			test_table.addCell(PdfCreator.FormatMiniTableTitleCell("原始\n成绩\n（NDA2）", BaseColor.BLACK, OverallReportUtil.tab_title));
			test_table.addCell(PdfCreator.FormatMiniTableTitleCell("动态\n评估\n成绩\n（DA2）", BaseColor.BLACK, OverallReportUtil.tab_title));
			test_table.addCell(PdfCreator.FormatMiniTableTitleCell("原始\n成绩\n（NDA3）", BaseColor.BLACK, OverallReportUtil.tab_title));
			test_table.addCell(PdfCreator.FormatMiniTableTitleCell("动态\n评估\n成绩\n（DA3）", BaseColor.BLACK, OverallReportUtil.tab_title));
			
			test_table.addCell(PdfCreator.FormatTableTitleCell("分数", BaseColor.BLACK, OverallReportUtil.tab_title));
			for(int i=0; i<6; i++)
				test_table.addCell(PdfCreator.FormatTableCell(overallReport.getTest_result()[0][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));
			test_table.addCell(PdfCreator.FormatTableTitleCell("总提示频率", BaseColor.BLACK, OverallReportUtil.tab_title));
			for(int i=0; i<6; i++)
				test_table.addCell(PdfCreator.FormatTableCell(overallReport.getTest_result()[1][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));
			test_table.addCell(PdfCreator.FormatTableTitleCell("学习潜能分数", BaseColor.BLACK, OverallReportUtil.tab_title));
			for(int i=0; i<6; i++)
				test_table.addCell(PdfCreator.FormatTableCell(overallReport.getTest_result()[2][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));
			
			PdfPCell test_cell = new PdfPCell();
			test_cell.addElement(test_table);
			test_cell.setVerticalAlignment(Element.ALIGN_TOP);
			test_cell.setBorderColor(BaseColor.WHITE);
			whole_table.addCell(test_cell);
		}
		
		//单次测试图
		{
			PdfPCell pic = new PdfPCell();
			Font rada_font = new Font(bfCn_title, 16, Font.BOLD, BaseColor.BLACK);
			Paragraph rada_text = new Paragraph("通过提示前后的成绩对比，看看自己是否有了进步~", rada_font);
			rada_text.setAlignment(Element.ALIGN_CENTER);
			rada_text.setSpacingAfter(12);

			pic.addElement(rada_text);
			String pic_path = PicCreator.CreateScoreColComp(overallReport.getScore(),overallReport.getTest_name(), path_tmp);
			Image img = Image.getInstance(pic_path);
			img.scalePercent(11);
			img.setAlignment(Element.ALIGN_CENTER);
			pic.addElement(img);
			pic.setBorderColor(BaseColor.WHITE);
			whole_table.addCell(pic);
		}
		//能力提示总计
		{
			PdfPTable test_table = new PdfPTable(5);
			test_table.setWidthPercentage(100);
			test_table.setWidths(new float[] { (float) 0.28, (float) 0.18, (float) 0.18, (float) 0.18, (float) 0.18});
			test_table.addCell(PdfCreator.FormatTableTitleCell("听力技能", BaseColor.BLACK, OverallReportUtil.tab_title));
			for(int i=0; i<3; i++){
				test_table.addCell(PdfCreator.FormatMiniTableTitleCell(overallReport.getTest_name()[i], BaseColor.BLACK, OverallReportUtil.tab_title));
			}
			test_table.addCell(PdfCreator.FormatMiniTableTitleCell("总提示频率", BaseColor.BLACK, OverallReportUtil.tab_title));
			test_table.addCell(PdfCreator.FormatTableCell("词汇与表达", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, OverallReportUtil.tab_odd));
			for(int i=0; i<4; i++){
				test_table.addCell(PdfCreator.FormatTableCell(overallReport.getInter_freq()[0][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));
			}
			test_table.addCell(PdfCreator.FormatTableCell("语法", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, OverallReportUtil.tab_odd));
			for(int i=0; i<4; i++){
				test_table.addCell(PdfCreator.FormatTableCell(overallReport.getInter_freq()[1][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));
			}
			test_table.addCell(PdfCreator.FormatTableCell("主旨大意", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, OverallReportUtil.tab_odd));
			for(int i=0; i<4; i++){
				test_table.addCell(PdfCreator.FormatTableCell(overallReport.getInter_freq()[2][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));
			}
			test_table.addCell(PdfCreator.FormatTableCell("细节", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, OverallReportUtil.tab_odd));
			for(int i=0; i<4; i++){
				test_table.addCell(PdfCreator.FormatTableCell(overallReport.getInter_freq()[3][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));
			}
			test_table.addCell(PdfCreator.FormatTableCell("推理", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, OverallReportUtil.tab_odd));
			for(int i=0; i<4; i++){
				test_table.addCell(PdfCreator.FormatTableCell(overallReport.getInter_freq()[4][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));
			}
			
			PdfPCell test_cell = new PdfPCell();
			test_cell.addElement(test_table);
			test_cell.setVerticalAlignment(Element.ALIGN_TOP);
			test_cell.setBorderColor(BaseColor.WHITE);
			whole_table.addCell(test_cell);
		}
		
		//能力总计图
		{
			PdfPCell pic = new PdfPCell();
			Font rada_font = new Font(bfCn_title, 16, Font.BOLD, BaseColor.BLACK);
			Paragraph rada_text = new Paragraph("技能提示情况", rada_font);
			rada_text.setAlignment(Element.ALIGN_CENTER);
			rada_text.setSpacingAfter(12);

			pic.addElement(rada_text);
			String pic_path = PicCreator.CreatePercentPic(overallReport.getInter_percent(), path_tmp);
			Image img = Image.getInstance(pic_path);
			img.scalePercent(11);
			img.setAlignment(Element.ALIGN_CENTER);
			pic.addElement(img);
			pic.setBorderColor(BaseColor.WHITE);
			whole_table.addCell(pic);
		}

		document.add(whole_table);
	}
}
