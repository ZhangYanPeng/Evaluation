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

public class PicCreator {

	//饼状图
	public static String CreatePiePic(){
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
		
		Stroke stroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(10, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		g2d.setPaint(new Color(112, 173, 71));
		g2d.fillArc(550, 1000, 1000, 1000, 220, 230);
		g2d.setPaint(new Color(68, 114, 196));
		g2d.fillArc(550, 1000, 1000, 1000, 130, 90);
		g2d.setPaint(new Color(255, 192, 0));
		g2d.fillArc(550, 1000, 1000, 1000, 90, 40);
		g2d.setPaint(Color.WHITE);
		g2d.drawLine(1050, 1500, 1050, 1000);
		g2d.setStroke(stroke);
		
		try {
			ImageIO.write(image, "jpeg", new File("C:\\Users\\yanpengzhang\\Desktop\\Workspace\\pic1.jpg"));
			return "C:\\Users\\yanpengzhang\\Desktop\\Workspace\\pic1.jpg";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
}
