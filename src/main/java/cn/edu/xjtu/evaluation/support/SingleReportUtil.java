package cn.edu.xjtu.evaluation.support;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class SingleReportUtil {
	public static void main(String[] args) {
		Document document = new Document(PageSize.A4.rotate(), (float) 31.75, (float) 31.75, (float) 25.4,
				(float) 25.4);
		// step 2
		try {
			PdfWriter.getInstance(document,
					new FileOutputStream("C:\\Users\\yanpengzhang\\Desktop\\Workspace\\tmp.pdf"));
		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// step 3
		document.open();

		SingleReportUtil.createTestReport(document);
		// step 4
		document.close();
	}

	public static BaseColor tab_title = new BaseColor(91, 155, 213);
	public static BaseColor tab_odd = new BaseColor(210, 222, 239);
	public static BaseColor tab_even = new BaseColor(234, 239, 247);

	public static void createTestReport(Document document) {
		try {
			// 标题
			BaseFont bfCn_title = BaseFont.createFont("C:/WINDOWS/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);
			Font FontCn_title = new Font(bfCn_title, 22, Font.BOLD, SingleReportUtil.tab_title);
			Paragraph title = new Paragraph("诊断报告", FontCn_title);
			title.setAlignment(Element.ALIGN_CENTER);
			title.setSpacingAfter(25);
			document.add(title);

			// 结果表格
			PdfPTable result_table = new PdfPTable(1);
			result_table.setWidthPercentage(100);
			result_table.addCell(
					PdfCreator.FormatTableTitleCell("学号： 姓名： 测试：", BaseColor.WHITE, SingleReportUtil.tab_title));
			{
				PdfPTable summary_table = new PdfPTable(2);
				summary_table.setWidths(new float[]{(float)0.35, (float)0.65});
				String result_text = "在本次测试中，\n 我的总得分：\n在全部做大的学生中，我排在XX%。\n我共答对XX道题目，其中不同难度\n题目所占百分比如右图所示。";
				PdfPCell summary_table_text_cell = PdfCreator.FormatTableCell(result_text, Element.ALIGN_TOP,
						Element.ALIGN_LEFT, BaseColor.BLACK, BaseColor.WHITE);
				summary_table_text_cell.setBorder(0);
				summary_table_text_cell.setPadding(20);
				summary_table.addCell(summary_table_text_cell);
				
				String pic_path = PicCreator.CreatePiePic();
				PdfPCell summary_pic = new PdfPCell(Image.getInstance(pic_path));
				summary_pic.setFixedHeight(270);
				summary_table.addCell(summary_pic);
				result_table.addCell(summary_table);
			}
			document.add(result_table);

			// 具体统计
			Font bfCn_indicator_text = new Font(bfCn_title, 12, Font.BOLD, BaseColor.BLACK);
			Paragraph detail_table_indicator = new Paragraph("以下是我在不同难度题目上的表现，红色字体代表的是做错的答案哦。", bfCn_indicator_text);
			detail_table_indicator.setAlignment(Element.ALIGN_LEFT);
			detail_table_indicator.setSpacingBefore(20);
			detail_table_indicator.setSpacingAfter(20);
			document.add(detail_table_indicator);
			PdfPTable detail_table = new PdfPTable(17);
			detail_table.setWidthPercentage(100);
			detail_table.setWidths(new float[] { (float) 0.2, (float) 0.05, (float) 0.05, (float) 0.05, (float) 0.05,
					(float) 0.05, (float) 0.05, (float) 0.05, (float) 0.05, (float) 0.05, (float) 0.05, (float) 0.05,
					(float) 0.05, (float) 0.05, (float) 0.05, (float) 0.05, (float) 0.05 });
			{
				// 表头
				detail_table
						.addCell(PdfCreator.FormatTableTitleCell("题号", BaseColor.WHITE, SingleReportUtil.tab_title));
				for (int i = 1; i < 17; i++) {
					detail_table.addCell(PdfCreator.FormatTableTitleCell(String.valueOf(i), BaseColor.WHITE,
							SingleReportUtil.tab_title));
				}
			}
			{
				// 行1
				PdfPCell r1_cell = PdfCreator.FormatTableCell("题目难度", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_odd);
				r1_cell.setBackgroundColor(new BaseColor(189, 215, 238));
				detail_table.addCell(r1_cell);
				for (int i = 1; i < 17; i++) {
					PdfPCell r1_cell_tmp = PdfCreator.FormatTableCell("难", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_odd);
					r1_cell.setBackgroundColor(new BaseColor(189, 215, 238));
					detail_table.addCell(r1_cell_tmp);
				}
			}
			{
				// 行2
				PdfPCell r2_cell = PdfCreator.FormatTableCell("我的答案", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_even);
				r2_cell.setBackgroundColor(new BaseColor(91, 155, 213));
				detail_table.addCell(r2_cell);
				for (int i = 1; i < 17; i++) {
					PdfPCell r2_cell_tmp = PdfCreator.FormatTableCell("难", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_even);
					r2_cell.setBackgroundColor(new BaseColor(91, 155, 213));
					detail_table.addCell(r2_cell_tmp);
				}
			}
			{
				// 行3
				PdfPCell r3_cell = PdfCreator.FormatTableCell("正确答案", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_odd);
				r3_cell.setBackgroundColor(new BaseColor(189, 215, 238));
				detail_table.addCell(r3_cell);
				for (int i = 1; i < 17; i++) {
					PdfPCell r3_cell_tmp = PdfCreator.FormatTableCell("难", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_odd);
					r3_cell.setBackgroundColor(new BaseColor(189, 215, 238));
					detail_table.addCell(r3_cell_tmp);
				}
			}
			document.add(detail_table);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createEvaluationReport(Document document) {
		try {
			// 标题
			BaseFont bfCn_title = BaseFont.createFont("C:/WINDOWS/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);
			Font FontCn_title = new Font(bfCn_title, 22, Font.BOLD, SingleReportUtil.tab_title);
			Paragraph title = new Paragraph("动态评估报告", FontCn_title);
			title.setAlignment(Element.ALIGN_CENTER);
			title.setSpacingAfter(25);
			document.add(title);

			// 描述
			Font bfCn_indicator_text = new Font(bfCn_title, 16, Font.BOLD, BaseColor.BLACK);
			Paragraph overall_table_indicator = new Paragraph("我的整体做大情况如下：", bfCn_indicator_text);
			overall_table_indicator.setAlignment(Element.ALIGN_LEFT);
			overall_table_indicator.setSpacingBefore(20);
			overall_table_indicator.setSpacingAfter(20);
			document.add(overall_table_indicator);
			// 总体表
			PdfPTable result_table = new PdfPTable(6);
			result_table.setWidthPercentage(100);
			result_table.setWidths(
					new float[] { (float) 0.15, (float) 0.15, (float) 0.15, (float) 0.15, (float) 0.2, (float) 0.2 });
			PdfPCell result_table_title = PdfCreator.FormatTableTitleCell("Test1", BaseColor.WHITE,
					SingleReportUtil.tab_title);
			result_table_title.setColspan(6);
			result_table.addCell(result_table_title);
			{
				PdfPCell test_date = PdfCreator.FormatTableCell("测试日期", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_odd);
				test_date.setRowspan(2);
				result_table.addCell(test_date);

				PdfPCell test_time = PdfCreator.FormatTableCell("测试时长", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_odd);
				test_time.setRowspan(2);
				result_table.addCell(test_time);

				PdfPCell test_score = PdfCreator.FormatTableCell("诊断成绩", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_odd);
				test_score.setColspan(2);
				result_table.addCell(test_score);

				PdfPCell test_escore = PdfCreator.FormatTableCell("动态评估成绩", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_odd);
				test_escore.setRowspan(2);
				result_table.addCell(test_escore);

				PdfPCell test_poe = PdfCreator.FormatTableCell("学习潜能分数", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_odd);
				test_poe.setRowspan(2);
				result_table.addCell(test_poe);

				PdfPCell test_oscore = PdfCreator.FormatTableCell("原始分数", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_even);
				result_table.addCell(test_oscore);

				PdfPCell test_pscore = PdfCreator.FormatTableCell("百分制", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_even);
				result_table.addCell(test_pscore);
			}
			{
				// 结果
				result_table.addCell(PdfCreator.FormatTableCell("2018-09-12", Element.ALIGN_MIDDLE,
						Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
				result_table.addCell(PdfCreator.FormatTableCell("00:20:15", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_odd));
				result_table.addCell(PdfCreator.FormatTableCell("16", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_odd));
				result_table.addCell(PdfCreator.FormatTableCell("16/64", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_odd));
				result_table.addCell(PdfCreator.FormatTableCell("33", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_odd));
				result_table.addCell(PdfCreator.FormatTableCell("0.83", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_odd));
			}
			document.add(result_table);

			// 描述
			Paragraph detail_table_indicator = new Paragraph("在系统提示情况下，我在每道题目上的表现：", bfCn_indicator_text);
			detail_table_indicator.setAlignment(Element.ALIGN_LEFT);
			detail_table_indicator.setSpacingBefore(20);
			detail_table_indicator.setSpacingAfter(20);
			document.add(detail_table_indicator);
			// 表
			PdfPTable detail_table = new PdfPTable(21);
			detail_table.setWidthPercentage(100);
			detail_table.setWidths(new float[] { (float) 0.04, (float) 0.12, (float) 0.08, (float) 0.04, (float) 0.04,
					(float) 0.04, (float) 0.04, (float) 0.04, (float) 0.04, (float) 0.04, (float) 0.04, (float) 0.04,
					(float) 0.04, (float) 0.04, (float) 0.04, (float) 0.04, (float) 0.04, (float) 0.04, (float) 0.04,
					(float) 0.08, (float) 0.08 });
			{
				PdfPCell test_qno = PdfCreator.FormatTableTitleCell("题号", BaseColor.WHITE, SingleReportUtil.tab_title);
				test_qno.setColspan(3);
				detail_table.addCell(test_qno);

				for (int i = 1; i < 17; i++)
					detail_table.addCell(PdfCreator.FormatTableTitleCell(String.valueOf(i), BaseColor.WHITE,
							SingleReportUtil.tab_title));
				detail_table.addCell(
						PdfCreator.FormatTableTitleCell("答对\n题数", BaseColor.WHITE, SingleReportUtil.tab_title));
				detail_table.addCell(
						PdfCreator.FormatTableTitleCell("累积\n分数", BaseColor.WHITE, SingleReportUtil.tab_title));
			}
			{
				PdfPCell test_con = PdfCreator.FormatTableCell("提\n示\n情\n况", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_odd);
				test_con.setRowspan(5);
				detail_table.addCell(test_con);

				{
					// 行1
					detail_table.addCell(PdfCreator.FormatTableCell("未提示", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_odd));
					detail_table.addCell(PdfCreator.FormatTableCell("得4分", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_odd));
					for (int i = 1; i < 17; i++)
						detail_table.addCell(PdfCreator.FormatTableCell("√", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, SingleReportUtil.tab_odd));
					detail_table.addCell(PdfCreator.FormatTableCell(String.valueOf(4), Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
					detail_table.addCell(PdfCreator.FormatTableCell(String.valueOf(16), Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
				}
				{
					// 行2
					detail_table.addCell(PdfCreator.FormatTableCell("提示一次", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_even));
					detail_table.addCell(PdfCreator.FormatTableCell("得3分", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_even));
					for (int i = 1; i < 17; i++)
						detail_table.addCell(PdfCreator.FormatTableCell("√", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, SingleReportUtil.tab_even));
					detail_table.addCell(PdfCreator.FormatTableCell(String.valueOf(4), Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_even));
					detail_table.addCell(PdfCreator.FormatTableCell(String.valueOf(16), Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_even));
				}
				{
					// 行3
					detail_table.addCell(PdfCreator.FormatTableCell("提示二次", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_odd));
					detail_table.addCell(PdfCreator.FormatTableCell("得2分", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_odd));
					for (int i = 1; i < 17; i++)
						detail_table.addCell(PdfCreator.FormatTableCell("√", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, SingleReportUtil.tab_odd));
					detail_table.addCell(PdfCreator.FormatTableCell(String.valueOf(4), Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
					detail_table.addCell(PdfCreator.FormatTableCell(String.valueOf(16), Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
				}
				{
					// 行4
					detail_table.addCell(PdfCreator.FormatTableCell("提示三次", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_even));
					detail_table.addCell(PdfCreator.FormatTableCell("得1分", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_even));
					for (int i = 1; i < 17; i++)
						detail_table.addCell(PdfCreator.FormatTableCell("√", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, SingleReportUtil.tab_even));
					detail_table.addCell(PdfCreator.FormatTableCell(String.valueOf(4), Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_even));
					detail_table.addCell(PdfCreator.FormatTableCell(String.valueOf(16), Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_even));
				}
				{
					// 行5
					detail_table.addCell(PdfCreator.FormatTableCell("提示四次", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_odd));
					detail_table.addCell(PdfCreator.FormatTableCell("得0分", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_odd));
					for (int i = 1; i < 17; i++)
						detail_table.addCell(PdfCreator.FormatTableCell("√", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, SingleReportUtil.tab_odd));
					detail_table.addCell(PdfCreator.FormatTableCell(String.valueOf(4), Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
					detail_table.addCell(PdfCreator.FormatTableCell(String.valueOf(16), Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
				}
			}
			document.add(detail_table);

			document.newPage();
			Paragraph ad_p = new Paragraph(" ");
			ad_p.setSpacingAfter(50);
			document.add(ad_p);
			{
				PdfPTable ability_table = new PdfPTable(2);
				ability_table.setWidthPercentage(100);
				{
					PdfPTable ability_def_table = new PdfPTable(4);
					ability_def_table.setWidthPercentage(100);
					ability_def_table.setWidths(new float[] { (float) 0.3, (float) 0.2, (float) 0.3, (float) 0.2 });
					ability_def_table.addCell(
							PdfCreator.FormatTableTitleCell("听力技能", BaseColor.WHITE, SingleReportUtil.tab_title));
					ability_def_table.addCell(
							PdfCreator.FormatTableTitleCell("定义", BaseColor.WHITE, SingleReportUtil.tab_title));
					ability_def_table.addCell(
							PdfCreator.FormatTableTitleCell("相关题目", BaseColor.WHITE, SingleReportUtil.tab_title));
					ability_def_table.addCell(
							PdfCreator.FormatTableTitleCell("指导", BaseColor.WHITE, SingleReportUtil.tab_title));

					ability_def_table.addCell(PdfCreator.FormatTableCell("词汇与表达", Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
					ability_def_table.addCell(PdfCreator.FormatTableCell("", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, BaseColor.WHITE));
					ability_def_table.addCell(PdfCreator.FormatTableCell("", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, BaseColor.WHITE));
					ability_def_table.addCell(PdfCreator.FormatTableCell("📚", Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));

					ability_def_table.addCell(PdfCreator.FormatTableCell("语法", Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
					ability_def_table.addCell(PdfCreator.FormatTableCell("", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, BaseColor.WHITE));
					ability_def_table.addCell(PdfCreator.FormatTableCell("", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, BaseColor.WHITE));
					ability_def_table.addCell(PdfCreator.FormatTableCell("📚", Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));

					ability_def_table.addCell(PdfCreator.FormatTableCell("主旨大意", Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
					ability_def_table.addCell(PdfCreator.FormatTableCell("", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, BaseColor.WHITE));
					ability_def_table.addCell(PdfCreator.FormatTableCell("", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, BaseColor.WHITE));
					ability_def_table.addCell(PdfCreator.FormatTableCell("📚", Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));

					ability_def_table.addCell(PdfCreator.FormatTableCell("细节", Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
					ability_def_table.addCell(PdfCreator.FormatTableCell("", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, BaseColor.WHITE));
					ability_def_table.addCell(PdfCreator.FormatTableCell("", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, BaseColor.WHITE));
					ability_def_table.addCell(PdfCreator.FormatTableCell("📚", Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));

					ability_def_table.addCell(PdfCreator.FormatTableCell("推理", Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
					ability_def_table.addCell(PdfCreator.FormatTableCell("", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, BaseColor.WHITE));
					ability_def_table.addCell(PdfCreator.FormatTableCell("", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, BaseColor.WHITE));
					ability_def_table.addCell(PdfCreator.FormatTableCell("📚", Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));
					
					PdfPCell ability_def_table_cell = new PdfPCell(ability_def_table);
					ability_def_table_cell.setVerticalAlignment(Element.ALIGN_TOP);
					ability_def_table_cell.setBorder(0);
					ability_table.addCell(ability_def_table_cell);
				}
				{
					// 雷达图
					PdfPCell pic = new PdfPCell(PdfCreator.FormatTableCell("听力技能掌握概率-雷达图", Element.ALIGN_TOP, Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));
					pic.setFixedHeight(200);
					ability_table.addCell(pic);
				}
				{
					//掌握程度表
					PdfPTable ability_inter_table = new PdfPTable(2);
					ability_inter_table.setWidthPercentage(100);
					{
						PdfPCell cell_ab_in_cell = new PdfPCell();
						
						Font ability_inter_ind_font = new Font(bfCn_title, 16, Font.BOLD, BaseColor.BLACK);
						Paragraph ability_inter_ind = new Paragraph("动态评估下的个人听力技能", ability_inter_ind_font);
						ability_inter_ind.setAlignment(Element.ALIGN_LEFT);
						ability_inter_ind.setSpacingAfter(10);
						cell_ab_in_cell.addElement(ability_inter_ind);
						
						PdfPTable ability_inter_sta_table = new PdfPTable(4);
						ability_inter_sta_table.setWidthPercentage(100);
						ability_inter_sta_table.setWidths(new float[] { (float) 0.4, (float) 0.2, (float) 0.2, (float) 0.2 });
						ability_inter_sta_table.addCell(
								PdfCreator.FormatTableTitleCell("听力技能", BaseColor.WHITE, SingleReportUtil.tab_title));
						ability_inter_sta_table.addCell(
								PdfCreator.FormatTableTitleCell("相关\n题目", BaseColor.WHITE, SingleReportUtil.tab_title));
						ability_inter_sta_table.addCell(
								PdfCreator.FormatTableTitleCell("总提示\n频率", BaseColor.WHITE, SingleReportUtil.tab_title));
						ability_inter_sta_table.addCell(
								PdfCreator.FormatTableTitleCell("平均提示\n频率", BaseColor.WHITE, SingleReportUtil.tab_title));

						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("词汇与表达", Element.ALIGN_MIDDLE,
								Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("1、2", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, BaseColor.WHITE));
						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("2", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, BaseColor.WHITE));
						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("1", Element.ALIGN_MIDDLE,
								Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));

						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("语法", Element.ALIGN_MIDDLE,
								Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("3、4", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, BaseColor.WHITE));
						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("6", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, BaseColor.WHITE));
						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("3", Element.ALIGN_MIDDLE,
								Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));

						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("主旨大意", Element.ALIGN_MIDDLE,
								Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("12", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, BaseColor.WHITE));
						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("4", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, BaseColor.WHITE));
						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("2", Element.ALIGN_MIDDLE,
								Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));

						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("细节", Element.ALIGN_MIDDLE,
								Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("5、6", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, BaseColor.WHITE));
						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("4", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, BaseColor.WHITE));
						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("2", Element.ALIGN_MIDDLE,
								Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));

						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("推理", Element.ALIGN_MIDDLE,
								Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("7、9", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, BaseColor.WHITE));
						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("3", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, BaseColor.WHITE));
						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("1.5", Element.ALIGN_MIDDLE,
								Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));

						cell_ab_in_cell.addElement(ability_inter_sta_table);
						cell_ab_in_cell.setBorder(0);
						cell_ab_in_cell.setVerticalAlignment(Element.ALIGN_TOP);
						ability_table.addCell(cell_ab_in_cell);
					}
				}
				{
					// 相对表现
					PdfPCell pic1 = new PdfPCell(PdfCreator.FormatTableCell("再来看看我在群体中的表现吧！", Element.ALIGN_TOP, Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));
					pic1.setFixedHeight(200);
					ability_table.addCell(pic1);
				}
				document.add(ability_table);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
