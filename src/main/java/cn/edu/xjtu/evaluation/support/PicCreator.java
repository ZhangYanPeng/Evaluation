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
	public static String CreatePiePic(double[] percents){
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
				g2d.drawString( String.valueOf(percents[i]*100)+"%", (int)(cx_my+500 + 800 * Math.cos(ang/180.0*Math.PI)) - 100,  50 + (int)(cy_my+500 - 700 * Math.sin(ang/180.0*Math.PI)));
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
				g2d.drawString( String.valueOf(percents[i+3]*100)+"%", (int)(cx_all+500 + 800 * Math.cos(ang/180.0*Math.PI)) - 100,  50 + (int)(cy_all+500 - 700 * Math.sin(ang/180.0*Math.PI)));
		}
		
		try {
			ImageIO.write(image, "jpeg", new File("C:\\Users\\yanpengzhang\\Desktop\\Workspace\\pic1.jpg"));
			return "C:\\Users\\yanpengzhang\\Desktop\\Workspace\\pic1.jpg";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	//雷达图
	public static String CreateRadPic(double[] params){
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
		for(int i=0; i<6 ; i++){
			Font f1 = new Font("宋体", Font.BOLD, 60);
			g2d.setFont(f1);
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
			ImageIO.write(image, "jpeg", new File("C:\\Users\\yanpengzhang\\Desktop\\Workspace\\pic1.jpg"));
			return "C:\\Users\\yanpengzhang\\Desktop\\Workspace\\pic1.jpg";
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
}
