package cn.edu.xjtu.evaluation.support;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import cn.edu.xjtu.evaluation.entity.EvaluationResult;
import cn.edu.xjtu.evaluation.entity.TestResult;

public class SingleReportUtil {

	public static BaseColor tab_title = new BaseColor(91, 155, 213);
	public static BaseColor tab_odd = new BaseColor(210, 222, 239);
	public static BaseColor tab_even = new BaseColor(234, 239, 247);

	public static void createTestReport(Document document, TestResult tr, String path_tmp) {
		try {
			// 标题
			BaseFont bfCn_title = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
			Font FontCn_title = new Font(bfCn_title, 22, Font.BOLD, SingleReportUtil.tab_title);
			Paragraph title = new Paragraph("诊断报告", FontCn_title);
			title.setAlignment(Element.ALIGN_CENTER);
			title.setSpacingAfter(25);
			document.add(title);

			// 结果表格
			PdfPTable result_table = new PdfPTable(1);
			result_table.setWidthPercentage(100);
			String title_str = "";
			title_str += "学号：" + tr.getStu_no();
			title_str += "     姓名：" + tr.getName();
			title_str += "     测试：" + tr.getTest();
			result_table.addCell(PdfCreator.FormatTableTitleCell(title_str, BaseColor.WHITE, SingleReportUtil.tab_title));
			{
				PdfPTable summary_table = new PdfPTable(2);
				summary_table.setWidths(new float[] { (float) 0.35, (float) 0.65 });
				String result_text = "在本次测试中，\n我的总得分：" + tr.getScore() + "（试题总分：64分）\n在全部作答的学生中，我排在" + tr.getRank() + "%。\n我共答对"
						+ tr.getRight_sum() + "道题目，其中不同难度\n题目所占百分比如右图所示。";
				PdfPCell summary_table_text_cell = PdfCreator.FormatTableCell(result_text, Element.ALIGN_TOP,
						Element.ALIGN_LEFT, BaseColor.BLACK, BaseColor.WHITE);
				summary_table_text_cell.setBorder(0);
				summary_table_text_cell.setPadding(20);
				summary_table.addCell(summary_table_text_cell);

				String pic_path = PicCreator.CreatePiePic(tr.getPercents(), path_tmp);
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
			String[][] results = tr.getResults();
			{
				// 行1
				PdfPCell r1_cell = PdfCreator.FormatTableCell("题目难度", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd);
				detail_table.addCell(r1_cell);
				for (int i = 0; i < 16; i++) {
					PdfPCell r1_cell_tmp = PdfCreator.FormatTableCell(results[0][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd);
					detail_table.addCell(r1_cell_tmp);
				}
			}
			{
				// 行2
				PdfPCell r2_cell = PdfCreator.FormatTableCell("我的答案", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_even);
				detail_table.addCell(r2_cell);
				for (int i = 0; i < 16; i++) {
					BaseColor c;
					int style;
					if(results[1][i].compareTo(results[2][i]) != 0){
						c = BaseColor.RED;
						style = Font.BOLD;
					}
					else{
						c = BaseColor.BLACK;
						style = Font.NORMAL;
					}
					PdfPCell r2_cell_tmp = PdfCreator.FormatTableCell(results[1][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							c, SingleReportUtil.tab_even);
					detail_table.addCell(r2_cell_tmp);
				}
			}
			{
				// 行3
				PdfPCell r3_cell = PdfCreator.FormatTableCell("正确答案", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_odd);
				detail_table.addCell(r3_cell);
				for (int i = 0; i < 16; i++) {
					PdfPCell r3_cell_tmp = PdfCreator.FormatTableCell(results[2][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_odd);
					detail_table.addCell(r3_cell_tmp);
				}
			}
			document.add(detail_table);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createEvaluationReport(Document document, EvaluationResult er, String path) {
		try {
			document.newPage();
			// 标题
			BaseFont bfCn_title =  BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
			Font FontCn_title = new Font(bfCn_title, 22, Font.BOLD, SingleReportUtil.tab_title);
			Paragraph title = new Paragraph("动态评估报告", FontCn_title);
			title.setAlignment(Element.ALIGN_CENTER);
			title.setSpacingAfter(25);
			document.add(title);

			// 描述
			Font bfCn_indicator_text = new Font(bfCn_title, 16, Font.BOLD, BaseColor.BLACK);
			Paragraph overall_table_indicator = new Paragraph("我的整体作答情况如下：", bfCn_indicator_text);
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
				result_table.addCell(PdfCreator.FormatTableCell(er.getOverallPerformance()[0], Element.ALIGN_MIDDLE,
						Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
				result_table.addCell(PdfCreator.FormatTableCell(er.getOverallPerformance()[1], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_odd));
				result_table.addCell(PdfCreator.FormatTableCell(er.getOverallPerformance()[2], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_odd));
				result_table.addCell(PdfCreator.FormatTableCell(er.getOverallPerformance()[3], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_odd));
				result_table.addCell(PdfCreator.FormatTableCell(er.getOverallPerformance()[4], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
						BaseColor.BLACK, SingleReportUtil.tab_odd));
				result_table.addCell(PdfCreator.FormatTableCell(er.getOverallPerformance()[5], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
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
					for (int i = 0; i < 18; i++)
						detail_table.addCell(PdfCreator.FormatTableCell(er.getScore_statictis()[0][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, SingleReportUtil.tab_odd));
				}
				{
					// 行2
					detail_table.addCell(PdfCreator.FormatTableCell("提示一次", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_even));
					detail_table.addCell(PdfCreator.FormatTableCell("得3分", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_even));
					for (int i = 0; i < 18; i++)
						detail_table.addCell(PdfCreator.FormatTableCell(er.getScore_statictis()[1][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, SingleReportUtil.tab_odd));
				}
				{
					// 行3
					detail_table.addCell(PdfCreator.FormatTableCell("提示二次", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_odd));
					detail_table.addCell(PdfCreator.FormatTableCell("得2分", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_odd));
					for (int i = 0; i < 18; i++)
						detail_table.addCell(PdfCreator.FormatTableCell(er.getScore_statictis()[2][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, SingleReportUtil.tab_odd));
				}
				{
					// 行4
					detail_table.addCell(PdfCreator.FormatTableCell("提示三次", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_even));
					detail_table.addCell(PdfCreator.FormatTableCell("得1分", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_even));
					for (int i = 0; i < 18; i++)
						detail_table.addCell(PdfCreator.FormatTableCell(er.getScore_statictis()[3][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, SingleReportUtil.tab_odd));
				}
				{
					// 行5
					detail_table.addCell(PdfCreator.FormatTableCell("提示四次", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_odd));
					detail_table.addCell(PdfCreator.FormatTableCell("得0分", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, SingleReportUtil.tab_odd));
					for (int i = 0; i < 18; i++)
						detail_table.addCell(PdfCreator.FormatTableCell(er.getScore_statictis()[4][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
								BaseColor.BLACK, SingleReportUtil.tab_odd));
				}
			}
			document.add(detail_table);

			document.newPage();
			Paragraph ad_p = new Paragraph(" ");
			document.add(ad_p);
			{
				PdfPTable ability_table = new PdfPTable(2);
				ability_table.setWidths(new float[] { (float) 0.6, (float) 0.4});
				ability_table.setWidthPercentage(100);
				{
					PdfPTable ability_def_table = new PdfPTable(2);
					ability_def_table.setWidthPercentage(100);
					ability_def_table.setWidths(new float[] { (float) 0.15, (float) 0.8});
					ability_def_table.addCell(
							PdfCreator.FormatTableTitleCell("听力技能", BaseColor.WHITE, SingleReportUtil.tab_title));
					ability_def_table.addCell(
							PdfCreator.FormatTableTitleCell("定义", BaseColor.WHITE, SingleReportUtil.tab_title));
					
					ability_def_table.addCell(PdfCreator.FormatTableCell("词汇与表达", Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
					PdfPCell ct1 = PdfCreator.FormatTableCell("1)能理解词汇或短语在具体情境下的意义和用法\r\n2)能识别并理解关键词汇的意义和用法（即有助于确立主题和观点的词汇）\r\n3)能理解常见的固定搭配和习惯性口头表达", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,
							BaseColor.BLACK, BaseColor.WHITE);
					ct1.setHorizontalAlignment(Element.ALIGN_LEFT);
					ct1.setMinimumHeight(25);
					ability_def_table.addCell(ct1);

					ability_def_table.addCell(PdfCreator.FormatTableCell("语法", Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
					PdfPCell ct2 = PdfCreator.FormatTableCell("1)能识别和使用主要的句式结构（虚拟语气，倒装句、否定句等）\r\n2)	能通过分析句法结构理解长句和难句的意义\r\n3)能够识别会话或短文中的衔接手段，并借助其获取关键信息", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,BaseColor.BLACK, BaseColor.WHITE);
					ct2.setHorizontalAlignment(Element.ALIGN_LEFT);
					ct2.setMinimumHeight(25);
					ability_def_table.addCell(ct2);

					ability_def_table.addCell(PdfCreator.FormatTableCell("主旨大意", Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
					PdfPCell ct3 = PdfCreator.FormatTableCell("1)能依据关键信息识别对话或篇章的主题\r\n2)能理解说话者的观点和意图\r\n3)能概括主要内容", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,BaseColor.BLACK, BaseColor.WHITE);
					ct3.setHorizontalAlignment(Element.ALIGN_LEFT);
					ct3.setMinimumHeight(25);
					ability_def_table.addCell(ct3);

					ability_def_table.addCell(PdfCreator.FormatTableCell("细节", Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
					PdfPCell ct4 = PdfCreator.FormatTableCell("1)能听懂细节并掌握关键信息，如人物、事件、时间等\r\n2)能根据语篇特征区分主要信息和次要信息\r\n3)能区分信息之间的异同", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,BaseColor.BLACK, BaseColor.WHITE);
					ct4.setHorizontalAlignment(Element.ALIGN_LEFT);
					ct4.setMinimumHeight(25);
					ability_def_table.addCell(ct4);

					ability_def_table.addCell(PdfCreator.FormatTableCell("推理", Element.ALIGN_MIDDLE,
							Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
					PdfPCell ct5 = PdfCreator.FormatTableCell("1)能结合语境、个人知识与经验或社会文化知识理解理解话语的隐含意义\r\n2)能理解话语的交际功能\r\n3)能依据上下文推理不熟悉词汇的意义", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER,BaseColor.BLACK, BaseColor.WHITE);
					ct5.setHorizontalAlignment(Element.ALIGN_LEFT);
					ct5.setMinimumHeight(25);
					ability_def_table.addCell(ct5);

					PdfPCell ability_def_table_cell = new PdfPCell();
					ability_def_table_cell.addElement(ability_def_table);
					ability_def_table_cell.setVerticalAlignment(Element.ALIGN_TOP);
					ability_def_table_cell.setBorderColor(BaseColor.WHITE);
					ability_table.addCell(ability_def_table_cell);
				}
				{
					// 雷达图
					PdfPCell pic = new PdfPCell();
					Font rada_font = new Font(bfCn_title, 16, Font.BOLD, BaseColor.BLACK);
					Paragraph rada_text = new Paragraph("听力技能掌握概率-雷达图", rada_font);
					rada_text.setAlignment(Element.ALIGN_CENTER);
					rada_text.setSpacingAfter(12);

					pic.addElement(rada_text);
					String pic_path = PicCreator.CreateRadPic(new double[] { 0, 0.2, 0.8, 0.5, 0.3 }, path);
					Image img = Image.getInstance(pic_path);
					img.scalePercent(10);
					img.setAlignment(Element.ALIGN_CENTER);
					pic.addElement(img);
					pic.setBorderColor(BaseColor.WHITE);
					ability_table.addCell(pic);
				}
				{
					// 掌握程度表
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
						ability_inter_sta_table
								.setWidths(new float[] { (float) 0.25, (float) 0.35, (float) 0.2, (float) 0.2 });
						
						ability_inter_sta_table.addCell(
								PdfCreator.FormatTableTitleCell("听力技能", BaseColor.WHITE, SingleReportUtil.tab_title));
						ability_inter_sta_table.addCell(
								PdfCreator.FormatTableTitleCell("相关\n题目", BaseColor.WHITE, SingleReportUtil.tab_title));
						ability_inter_sta_table.addCell(PdfCreator.FormatTableTitleCell("总提示\n频率", BaseColor.WHITE,
								SingleReportUtil.tab_title));
						ability_inter_sta_table.addCell(PdfCreator.FormatTableTitleCell("平均提示\n频率", BaseColor.WHITE,
								SingleReportUtil.tab_title));

						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("词汇与表达", Element.ALIGN_MIDDLE,
								Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
						for(int i=0; i<3; i++){
							ability_inter_sta_table.addCell(PdfCreator.FormatTableCell(er.getAbility()[0][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));
						}

						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("语法", Element.ALIGN_MIDDLE,
								Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
						for(int i=0; i<3; i++){
							ability_inter_sta_table.addCell(PdfCreator.FormatTableCell(er.getAbility()[1][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));
						}

						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("主旨大意", Element.ALIGN_MIDDLE,
								Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
						for(int i=0; i<3; i++){
							ability_inter_sta_table.addCell(PdfCreator.FormatTableCell(er.getAbility()[2][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));
						}

						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("细节", Element.ALIGN_MIDDLE,
								Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
						for(int i=0; i<3; i++){
							ability_inter_sta_table.addCell(PdfCreator.FormatTableCell(er.getAbility()[3][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));
						}

						ability_inter_sta_table.addCell(PdfCreator.FormatTableCell("推理", Element.ALIGN_MIDDLE,
								Element.ALIGN_CENTER, BaseColor.BLACK, SingleReportUtil.tab_odd));
						for(int i=0; i<3; i++){
							ability_inter_sta_table.addCell(PdfCreator.FormatTableCell(er.getAbility()[4][i], Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, BaseColor.BLACK, BaseColor.WHITE));
						}

						cell_ab_in_cell.addElement(ability_inter_sta_table);
						cell_ab_in_cell.setBorder(0);
						cell_ab_in_cell.setVerticalAlignment(Element.ALIGN_TOP);
						ability_table.addCell(cell_ab_in_cell);
					}
				}
				{
					// 相对表现
					PdfPCell pic = new PdfPCell();
					Font rada_font = new Font(bfCn_title, 16, Font.BOLD, BaseColor.BLACK);
					Paragraph rada_text = new Paragraph("再来看看我在群体中的表现吧！", rada_font);
					rada_text.setAlignment(Element.ALIGN_CENTER);
					rada_text.setSpacingAfter(12);

					pic.addElement(rada_text);
					String pic_path = PicCreator.CreateColComp(er.getInter_sta(), path);
					Image img = Image.getInstance(pic_path);
					img.scalePercent(11);
					img.setAlignment(Element.ALIGN_CENTER);
					pic.addElement(img);
					pic.setBorderColor(BaseColor.WHITE);
					ability_table.addCell(pic);
				}
				document.add(ability_table);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
