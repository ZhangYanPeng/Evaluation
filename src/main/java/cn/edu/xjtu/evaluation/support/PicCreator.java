package cn.edu.xjtu.evaluation.support;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

public class PicCreator {

	//饼状图
	public static String CreatePiePic(double[] percents, String path){
		int width = 4650, height = 2500;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D g2d = image.createGraphics();
		g2d.setBackground(Color.WHITE);
		g2d.clearRect(0, 0, width, height);
		
		Font f1 = new Font("宋体", Font.BOLD, 120);
		g2d.setFont(f1);
		g2d.setPaint(Color.LIGHT_GRAY);
		g2d.drawString("我的答题情况", 700, 400);
		g2d.drawString("全部学生作答情况", 3000, 400);
		
		//我的情况
		int[][] angle = new int[3][2];
		{
			angle[0][1] = (int) (percents[0]*360);
			angle[1][1] = (int) (percents[1]*360);
			angle[2][1] = 360 - angle[0][1] - angle[1][1];
			angle[0][0] = 90;
			angle[1][0] = angle[0][0] + angle[0][1];
			angle[2][0] = angle[1][0] + angle[1][1];
		}
		int cx_my=550, cy_my=1000;
		Stroke stroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(10, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		g2d.setPaint(new Color(112, 173, 71));
		g2d.fillArc(cx_my, cy_my, 1000, 1000, angle[2][0], angle[2][1]);
		g2d.setPaint(new Color(68, 114, 196));
		g2d.fillArc(cx_my, cy_my, 1000, 1000, angle[1][0], angle[1][1]);
		g2d.setPaint(new Color(255, 192, 0));
		g2d.fillArc(cx_my, cy_my, 1000, 1000, angle[0][0], angle[0][1]);
		
		
		g2d.setPaint(Color.WHITE);
		g2d.drawLine(cx_my+500, cy_my+500, cx_my+500, cy_my);
		g2d.drawLine(cx_my+500, cy_my+500, (int)(cx_my+500 + 500 * Math.cos(angle[1][0]/180.0*Math.PI)), (int)(cy_my+500 - 500 * Math.sin(angle[1][0]/180.0*Math.PI)));
		g2d.drawLine(cx_my+500, cy_my+500, (int)(cx_my+500 + 500 * Math.cos(angle[2][0]/180.0*Math.PI)), (int)(cy_my+500 - 500 * Math.sin(angle[2][0]/180.0*Math.PI)));
		g2d.setStroke(stroke);
		
		int[] text_ang = new int[3];
		for(int i=0; i<3; i++){
			text_ang[i] = (int)((angle[i][0] + angle[i][1] / 2.0 ) / 10);
			if(i>0 && text_ang[i]<=text_ang[i-1])
				text_ang[i] = text_ang[i-1]+1;
		}
		for(int i=0; i<3; i++){
				Font f2 = new Font("宋体", Font.BOLD, 60);
				g2d.setFont(f2);
				String text= "";
				switch(i){
					case 2: text="高难度";g2d.setPaint(new Color(112, 173, 71));break;
					case 1: text="中等难度";g2d.setPaint(new Color(68, 114, 196));break;
					case 0: text="容易";g2d.setPaint(new Color(255, 192, 0));break;
					default: g2d.setPaint(Color.LIGHT_GRAY);break;
				}
				double ang = text_ang[i] * 10;
				g2d.drawString(text, (int)(cx_my+500 + 800 * Math.cos(ang/180.0*Math.PI)) - 100, -20 + (int)(cy_my+500 - 700 * Math.sin(ang/180.0*Math.PI)));
				g2d.drawString( String.format("%.2f %%", percents[i]*100), (int)(cx_my+500 + 800 * Math.cos(ang/180.0*Math.PI)) - 100,  50 + (int)(cy_my+500 - 700 * Math.sin(ang/180.0*Math.PI)));
		}
		
		//全部情况
		int[][] angle_all = new int[3][2];
		{
			angle_all[0][1] = (int) (percents[3]*360);
			angle_all[1][1] = (int) (percents[4]*360);
			angle_all[2][1] = 360 - angle_all[0][1] - angle_all[1][1];
			angle_all[0][0] = 90;
			angle_all[1][0] = angle_all[0][0] + angle_all[0][1];
			angle_all[2][0] = angle_all[1][0] + angle_all[1][1];
		}
		int cx_all=3000, cy_all=1000;
		g2d.setStroke(new BasicStroke(10, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		g2d.setPaint(new Color(112, 173, 71));
		g2d.fillArc(cx_all, cy_all, 1000, 1000, angle_all[2][0], angle_all[2][1]);
		g2d.setPaint(new Color(68, 114, 196));
		g2d.fillArc(cx_all, cy_all, 1000, 1000, angle_all[1][0], angle_all[1][1]);
		g2d.setPaint(new Color(255, 192, 0));
		g2d.fillArc(cx_all, cy_all, 1000, 1000, angle_all[0][0], angle_all[0][1]);
		
		
		g2d.setPaint(Color.WHITE);
		g2d.drawLine(cx_all+500, cy_all+500, cx_all+500, cy_all);
		g2d.drawLine(cx_all+500, cy_all+500, (int)(cx_all+500 + 500 * Math.cos(angle_all[1][0]/180.0*Math.PI)), (int)(cy_all+500 - 500 * Math.sin(angle_all[1][0]/180.0*Math.PI)));
		g2d.drawLine(cx_all+500, cy_all+500, (int)(cx_all+500 + 500 * Math.cos(angle_all[2][0]/180.0*Math.PI)), (int)(cy_all+500 - 500 * Math.sin(angle_all[2][0]/180.0*Math.PI)));
		g2d.setStroke(stroke);
		
		int[] text_ang_all = new int[3];
		for(int i=0; i<3; i++){
			text_ang_all[i] = (int)((angle_all[i][0] + angle_all[i][1] / 2.0 ) / 10);
			if(i>0 && text_ang_all[i]<=text_ang_all[i-1])
				text_ang_all[i] = text_ang_all[i-1]+1;
		}
		for(int i=0; i<3; i++){
				Font f2 = new Font("宋体", Font.BOLD, 60);
				g2d.setFont(f2);
				String text = "";
				switch(i){
					case 2: text="高难度"; g2d.setPaint(new Color(112, 173, 71));break;
					case 1: text="中等难度"; g2d.setPaint(new Color(68, 114, 196));break;
					case 0: text="容易"; g2d.setPaint(new Color(255, 192, 0));break;
					default: g2d.setPaint(Color.LIGHT_GRAY);break;
				}
				double ang = text_ang_all[i] * 10;
				g2d.drawString(text, (int)(cx_all+500 + 800 * Math.cos(ang/180.0*Math.PI)) - 100, -20 + (int)(cy_all+500 - 700 * Math.sin(ang/180.0*Math.PI)));
				g2d.drawString( String.format("%.2f %%", percents[i]*100), (int)(cx_all+500 + 800 * Math.cos(ang/180.0*Math.PI)) - 100,  50 + (int)(cy_all+500 - 700 * Math.sin(ang/180.0*Math.PI)));
		}
		
		try {
			String filepath =  path + System.currentTimeMillis()+".jpg";
			ImageIO.write(image, "jpeg", new File(filepath));
			return filepath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	//雷达图
	public static String CreateRadPic(double[] params, String path){
		int width = 2400, height = 2000;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D g2d = image.createGraphics();
		g2d.setBackground(Color.WHITE);
		g2d.clearRect(0, 0, width, height);
		
		for(int i=0; i<6 ; i++){
			PicCreator.DrawFive(g2d, 1200, 1100, i, 900); 
		}
		
		//刻度
		String[] units = new String[] {"  0", "0.2", "0.4", "0.6", "0.8", "  1"};
		Font f1 = new Font("宋体", Font.BOLD, 60);
		g2d.setFont(f1);
		for(int i=0; i<6 ; i++){
			g2d.setPaint(Color.BLACK);
			g2d.drawString(units[i], 1050, 1100-i*900/5);
		}
		
		Font f2 = new Font("宋体", Font.BOLD, 100);
		g2d.setFont(f2);
		g2d.setPaint(Color.BLACK);
		g2d.drawString("词汇与表达", 950, 110 );
		g2d.drawString("推理", 0, 900 );
		g2d.drawString("细节", 400, 1900 );
		g2d.drawString("主旨大意", 1800, 1900 );
		g2d.drawString("语法", 2150  , 900);
		PicCreator.DrawFiveData(g2d, 1200, 1100, params, 900 );
		
		try {
			String filepath =  path + System.currentTimeMillis()+".jpg";
			ImageIO.write(image, "jpeg", new File(filepath));
			return filepath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void DrawFive(Graphics2D g2d, int x, int y, int level, int rad){
		int l = (int) (level/5.0 * rad);
		Stroke stroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		g2d.setPaint(Color.LIGHT_GRAY);
		for(int i=0; i<5; i++){
			double s_ang = Math.PI*(90+i*72)/180, e_ang = Math.PI*(90+(i+1)*72)/180;
			g2d.drawLine((int)(x + l * Math.cos(s_ang)), (int)(y - l * Math.sin(s_ang)), (int)(x + l * Math.cos(e_ang)), (int)(y - l * Math.sin(e_ang)));
		}
		g2d.setStroke(stroke);
		
	}
	
	public static void DrawFiveData(Graphics2D g2d, int x, int y, double[] params, int rad){
		Stroke stroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(16, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		g2d.setPaint(Color.BLUE);
		for(int i=0; i<5; i++){
			double l1 = rad * params[i%5];
			double l2 = rad * params[(i+1)%5];
			double s_ang = Math.PI*(90+i*72)/180, e_ang = Math.PI*(90+(i+1)*72)/180;
			g2d.drawLine((int)(x + l1 * Math.cos(s_ang)), (int)(y - l1 * Math.sin(s_ang)), (int)(x + l2 * Math.cos(e_ang)), (int)(y - l2 * Math.sin(e_ang)));
		}
		g2d.setStroke(stroke);
	}
	
	//对比柱状图
	public static String CreateColComp(double[][] params, String path){
		int width = 2400, height = 2000;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D g2d = image.createGraphics();
		g2d.setBackground(Color.WHITE);
		g2d.clearRect(0, 0, width, height);
		
		Stroke stroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		g2d.setPaint(Color.LIGHT_GRAY);
		for(int i=0; i<=8; i++){
			g2d.drawLine(200,100+140*i,2350,100+140*i);
		}
		g2d.setStroke(stroke);
		
		double maxv = 0;
		for(int i=0; i<5 ; i++){
			if(maxv < params[0][i])
				maxv = params[0][i];
			if(maxv < params[1][i])
				maxv = params[1][i];
		}
		int unit = 1;
		while(unit*8<maxv)
			unit++;
		
		PicCreator.DrawCmpData(g2d, params, unit);
		
		Font f1 = new Font("宋体", Font.BOLD, 60);
		g2d.setFont(f1);
		g2d.setPaint(Color.BLACK);
		for(int i=0; i<=8 ; i++){
			g2d.drawString(String.valueOf(unit*(8-i)), 100, 100+140*i);
		}
		g2d.drawString("词汇与表达", 350, 1320 );
		g2d.drawString("语法", 840, 1320 );
		g2d.drawString("主旨大意", 1180, 1320 );
		g2d.drawString("细节", 1600, 1320 );
		g2d.drawString("推理", 2040, 1320);
		
		Font f2 = new Font("宋体", Font.BOLD, 100);
		g2d.setFont(f2);
		g2d.drawString("我在群体中的相对表现", 740, 1500);
		
		Font f3 = new Font("宋体", Font.BOLD, 50);
		g2d.setFont(f3);
		g2d.drawString("我的总提示频率", 600, 1650);
		g2d.drawString("全部学生平均总提示频率", 1300, 1650);
		g2d.setPaint(new Color(91, 155, 213));
		g2d.fillRect(540, 1610, 40, 40);
		g2d.setPaint(new Color(237, 125, 49));
		g2d.fillRect(1240, 1610, 40, 40);
		
		try {
			String filepath =  path + System.currentTimeMillis()+".jpg";
			ImageIO.write(image, "jpeg", new File(filepath));
			return filepath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void DrawCmpData(Graphics2D g2d, double[][] params, int unit) {
		// TODO Auto-generated method stub
		for(int i=0; i<5; i++){
			g2d.setPaint(new Color(91, 155, 213));
			int height =  (int) (params[0][i]/unit * 140);
			g2d.fillRect(i*400+390, 1220-height, 100, height);
		}
		
		for(int i=0; i<5; i++){
			g2d.setPaint(new Color(237, 125, 49));
			int height =  (int) (params[1][i]/unit * 140);
			g2d.fillRect(i*400+510, 1220-height, 100, height);
		}
	}

	public static String CreateScoreColComp(int[][] score, String[] test, String path) {
		// TODO Auto-generated method stub
		int width = 2400, height = 1700;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D g2d = image.createGraphics();
		g2d.setBackground(Color.WHITE);
		g2d.clearRect(0, 0, width, height);
		
		Stroke stroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		g2d.setPaint(Color.LIGHT_GRAY);
		for(int i=0; i<=7; i++){
			g2d.drawLine(200,100+150*i,2350,100+150*i);
		}
		g2d.setStroke(stroke);
		
		PicCreator.DrawScoreData(g2d, score);
		
		Font f1 = new Font("宋体", Font.BOLD, 60);
		g2d.setFont(f1);
		g2d.setPaint(Color.BLACK);
		for(int i=0; i<=7 ; i++){
			g2d.drawString(String.valueOf(10*(7-i)), 100, 100+150*i);
		}
		g2d.drawString("TEST I", 400, 1270 );
		g2d.drawString("TEST II", 1200, 1270 );
		g2d.drawString("TEST III", 1970, 1270);
		
		Font f3 = new Font("宋体", Font.BOLD, 50);
		g2d.setFont(f3);
		g2d.drawString("提示前", 600, 1450);
		g2d.drawString("提示后", 1300, 1450);
		g2d.setPaint(new Color(91, 155, 213));
		g2d.fillRect(540, 1410, 40, 40);
		g2d.setPaint(new Color(237, 125, 49));
		g2d.fillRect(1240, 1410, 40, 40);
		
		try {
			String filepath =  path + System.currentTimeMillis()+".jpg";
			ImageIO.write(image, "jpeg", new File(filepath));
			return filepath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void DrawScoreData(Graphics2D g2d, int[][] score) {
		// TODO Auto-generated method stub
		for(int i=0; i<3; i++){
			g2d.setPaint(new Color(91, 155, 213));
			int height =  (int) (score[0][i]/10.0 * 150);
			g2d.fillRect(i*800+390, 1150-height, 100, height);
		}
		
		for(int i=0; i<3; i++){
			g2d.setPaint(new Color(237, 125, 49));
			int height =  (int) (score[1][i]/10.0 * 150);
			g2d.fillRect(i*800+510, 1150-height, 100, height);
		}
	}

	public static String CreatePercentPic(double[][] inter_percent, String path) {
		// TODO Auto-generated method stub
		int width = 2400, height = 2000;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D g2d = image.createGraphics();
		g2d.setBackground(Color.WHITE);
		g2d.clearRect(0, 0, width, height);
		
		Stroke stroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		g2d.setPaint(Color.LIGHT_GRAY);
		for(int i=0; i<=10; i++){
			g2d.drawLine(200,100+130*i,2350,100+130*i);
		}
		g2d.setStroke(stroke);
		
		PicCreator.DrawPercentData(g2d, inter_percent);
		
		Font f1 = new Font("宋体", Font.BOLD, 60);
		g2d.setFont(f1);
		g2d.setPaint(Color.BLACK);
		for(int i=0; i<=10 ; i++){
			g2d.drawString(String.valueOf(10*(10-i))+"%", 50, 100+130*i);
		}
		g2d.drawString("词汇与表达", 350, 1500 );
		g2d.drawString("语法", 840, 1500 );
		g2d.drawString("主旨大意", 1180, 1500 );
		g2d.drawString("细节", 1600, 1500 );
		g2d.drawString("推理", 2040, 1500);
		
		Font f3 = new Font("宋体", Font.BOLD, 50);
		g2d.setFont(f3);
		g2d.drawString("TEST I", 600, 1600);
		g2d.drawString("TEST II", 1200, 1600);
		g2d.drawString("TEST III", 1800, 1600);
		g2d.setPaint(new Color(112, 173, 71));
		g2d.fillRect(540, 1560, 40, 40);
		g2d.setPaint(new Color(68, 114, 196));
		g2d.fillRect(1140, 1560, 40, 40);
		g2d.setPaint(new Color(255, 192, 0));
		g2d.fillRect(1740, 1560, 40, 40);
		
		try {
			String filepath =  path + System.currentTimeMillis()+".jpg";
			ImageIO.write(image, "jpeg", new File(filepath));
			return filepath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static void DrawPercentData(Graphics2D g2d, double[][] inter_percent) {
		// TODO Auto-generated method stub
		for(int i=0; i<5; i++){
			if(inter_percent[0][i] + inter_percent[1][i] + inter_percent[2][i] == 0){
				inter_percent[0][i] = 0.33;
				inter_percent[1][i] = 0.33;
				inter_percent[2][i] = 0.33;
			}
			int height1 = 130*10;
			int height2 = (int) (height1 - 130*10*inter_percent[2][i]);
			int height3 = (int) (height2 - 130*10*inter_percent[1][i]);
			g2d.setPaint(new Color(255, 192, 0));
			g2d.fillRect(i*400+340, 1400-height1, 200, height1);
			g2d.setPaint(new Color(68, 114, 196));
			g2d.fillRect(i*400+340, 1400-height2, 200, height2);
			g2d.setPaint(new Color(112, 173, 71));
			g2d.fillRect(i*400+340, 1400-height3, 200, height3);
		}
	}
}
