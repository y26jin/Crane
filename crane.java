import javax.swing.*;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.event.*;
import java.lang.*;

public class crane extends JComponent{
		private static int windowsHeight = 600, windowsWidth = 800, recHeight = 30, baseWidth = 300, baseHeight = 60;
		private int x_center = 0;
		private int y_center = 0;
		private int x,y;
		int currentX, currentY, oldX, oldY;
		int[] xpoints = {windowsWidth, 0, 0, windowsWidth};
		int[] ypoints = {windowsHeight, windowsHeight, windowsHeight-recHeight, windowsHeight-recHeight};
		private Polygon ground = new Polygon(xpoints, ypoints, 4);
		
		int rectangleX = 0, magic = 0, magic1 = 0, magic2 = 0, magic3 = 0, attach = 0; // the x coordinate of rectangle
		double theta01=0, theta02=0, theta03=0;
		double theta1, theta2, theta3;
		private RoundRectangle2D roundRec = new RoundRectangle2D.Float(0, 
																		windowsHeight-recHeight-baseHeight,
																		baseWidth,
																		baseHeight,
																		30,
																		30);
		private static int basePVlength = 30;
		int[] xbasepv = {baseWidth/2-basePVlength, baseWidth/2, baseWidth/2 + basePVlength};
		int[] ybasepv = {windowsHeight-recHeight-baseHeight,
						windowsHeight-recHeight-baseHeight-basePVlength,
						windowsHeight-recHeight-baseHeight};
		private Polygon basePV = new Polygon(xbasepv, ybasepv, 3);
		private int basepvCenterX = baseWidth/2, basepvCenterY = windowsHeight-recHeight-baseHeight-basePVlength/2;
		
		// there are three arms
		private static int armWidth = 30, armHeight = 130;
		private static int boxWidth = 60;
		double tempx, tempy;
		int armx1, army1;
		int armx2, army2;
		int armx3, army3;
		int radius = 30;
		int pivotX1=rectangleX+baseWidth/2, pivotY1 = windowsHeight-recHeight-baseHeight-basePVlength;
		int pivotX2=rectangleX+baseWidth/2, pivotY2 = windowsHeight-recHeight-baseHeight-basePVlength - armHeight-radius;
		int pivotX3=rectangleX+baseWidth/2, pivotY3 = windowsHeight-recHeight-baseHeight-basePVlength - 2*armHeight-2*radius;
		private Rectangle2D arm1 = new Rectangle2D.Float(baseWidth/2 - basePVlength + armWidth/2,
														windowsHeight-recHeight-baseHeight-basePVlength-armHeight,
														armWidth,
														armHeight);
		private Rectangle2D arm2 = new Rectangle2D.Float(baseWidth/2 - basePVlength + armWidth/2,
														windowsHeight-recHeight-baseHeight-basePVlength-2*armHeight-radius,
														armWidth,
														armHeight);
		private Rectangle2D arm3 = new Rectangle2D.Float(baseWidth/2 - basePVlength + armWidth/2,
				windowsHeight-recHeight-baseHeight-basePVlength-3*armHeight-2*radius,
				armWidth,
				armHeight);
		private Rectangle2D mag = new Rectangle2D.Float(baseWidth/2 - basePVlength + armWidth/4,
				windowsHeight-recHeight-baseHeight-basePVlength-3*armHeight-2*radius-armWidth/2,
				3*armWidth/2,
				armWidth/2);
		
		private Rectangle2D box1 = new Rectangle2D.Float(windowsWidth-3*boxWidth,
				windowsHeight-recHeight-2*boxWidth,
				boxWidth,boxWidth);
		private Rectangle2D box2 = new Rectangle2D.Float(windowsWidth-3*boxWidth,
				windowsHeight-recHeight-boxWidth,
				boxWidth,boxWidth);
		private Rectangle2D box3 = new Rectangle2D.Float(windowsWidth-2*boxWidth,
				windowsHeight-recHeight-2*boxWidth,
				boxWidth,boxWidth);
		private Rectangle2D box4 = new Rectangle2D.Float(windowsWidth-2*boxWidth,
				windowsHeight-recHeight-boxWidth,
				boxWidth,boxWidth);
		
		int[] xarm1 = {baseWidth/2 - basePVlength + armWidth/2,
						baseWidth/2 - basePVlength + armWidth/2,
						baseWidth/2 - basePVlength + armWidth/2+armWidth,
						baseWidth/2 - basePVlength + armWidth/2+armWidth};
		int[] yarm1 = {windowsHeight-recHeight-baseHeight-basePVlength-armHeight,
						windowsHeight-recHeight-baseHeight-basePVlength,
						windowsHeight-recHeight-baseHeight-basePVlength-armHeight,
						windowsHeight-recHeight-baseHeight-basePVlength};
		int[] xarm2 = {baseWidth/2 - basePVlength + armWidth/2,
						baseWidth/2 - basePVlength + armWidth/2,
						baseWidth/2 - basePVlength + armWidth/2+armWidth,
						baseWidth/2 - basePVlength + armWidth/2+armWidth};
		int[] yarm2 = {windowsHeight-recHeight-baseHeight-basePVlength-2*armHeight-radius,
						windowsHeight-recHeight-baseHeight-basePVlength-armHeight-radius,
						windowsHeight-recHeight-baseHeight-basePVlength-2*armHeight-radius,
						windowsHeight-recHeight-baseHeight-basePVlength-armHeight-radius};
		int[] xarm3 = {baseWidth/2 - basePVlength + armWidth/2,
						baseWidth/2 - basePVlength + armWidth/2,
						baseWidth/2 - basePVlength + armWidth/2+armWidth,
						baseWidth/2 - basePVlength + armWidth/2+armWidth};
		int[] yarm3 = {windowsHeight-recHeight-baseHeight-basePVlength-3*armHeight-2*radius,
						windowsHeight-recHeight-baseHeight-basePVlength-2*armHeight-2*radius,
						windowsHeight-recHeight-baseHeight-basePVlength-3*armHeight-2*radius,
						windowsHeight-recHeight-baseHeight-basePVlength-2*armHeight-2*radius};
		int[] xmag = {baseWidth/2 - basePVlength + armWidth/4,
						baseWidth/2 - basePVlength + armWidth/4,
						baseWidth/2 - basePVlength + armWidth/4 + 3*armWidth/2,
						baseWidth/2 - basePVlength + armWidth/4 + 3*armWidth/2};
		int[] ymag = {windowsHeight-recHeight-baseHeight-basePVlength-3*armHeight-2*radius-armWidth/2,
						windowsHeight-recHeight-baseHeight-basePVlength-3*armHeight-2*radius,
						windowsHeight-recHeight-baseHeight-basePVlength-3*armHeight-2*radius,
						windowsHeight-recHeight-baseHeight-basePVlength-3*armHeight-2*radius-armWidth/2};
		
		private Rectangle2D testRec = new Rectangle2D.Float(baseWidth/2-basePVlength+armWidth/4, windowsHeight-recHeight-baseHeight-basePVlength-3*armHeight-2*radius-armWidth/2-boxWidth, boxWidth, boxWidth);
		AffineTransform armTrans1 = new AffineTransform();
		AffineTransform armTrans2 = new AffineTransform();
		AffineTransform armTrans3 = new AffineTransform();
		AffineTransform baseTrans = new AffineTransform();
		
		int Switch = 1;
		
		Graphics2D gTwo1, gTwo2, gTwo3, gTwo4, gTwo5, gTwo6, gTwo7;
		
		public static void main(String[] args){
			crane canvas = new crane();
			JFrame mainWindow = new JFrame("Doozers");
			mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainWindow.setSize(windowsWidth, windowsHeight);
			mainWindow.setContentPane(canvas);
			mainWindow.setVisible(true);
		}
		
		public void paintComponent(Graphics g){
			
			// draw the ground
			gTwo1 = (Graphics2D) g;
			gTwo1.translate(x_center, y_center);
			gTwo1.setColor(Color.GREEN);
			gTwo1.fill(ground);
			gTwo1.setColor(Color.GREEN);
			gTwo1.draw(ground);
			gTwo1.fill(box1);
			gTwo1.setColor(Color.cyan);
			gTwo1.draw(box1);
			gTwo1.fill(box2);
			gTwo1.setColor(Color.magenta);
			gTwo1.draw(box2);
			gTwo1.fill(box3);
			gTwo1.setColor(Color.orange);
			gTwo1.draw(box3);
			gTwo1.fill(box4);
			gTwo1.setColor(Color.pink);
			gTwo1.draw(box3);
			
			
			// draw the crane
			gTwo2 = (Graphics2D) g;
			gTwo2.translate(x_center, y_center);
			gTwo2.translate(x,y);
			// the base
			gTwo2.setColor(Color.blue);
			gTwo2.setStroke(new BasicStroke(4));
			gTwo2.fill(roundRec);
			gTwo2.setColor(Color.black);
			gTwo2.draw(roundRec);
			
			// the pviot connecting arms with base
			gTwo2.fill(basePV);
			gTwo2.draw(basePV);
			
			
			// arm1
			gTwo3 = (Graphics2D) g;
			gTwo3.translate(x_center, y_center);
			gTwo3.transform(armTrans1);
			gTwo3.fill(arm1);
			gTwo3.setColor(Color.blue);
			gTwo3.draw(arm1);
			gTwo3.setColor(Color.black);
			gTwo3.drawOval(pivotX2-radius/2, pivotY2, radius, radius);
			
			// arm2
			gTwo4 = (Graphics2D) g;
			gTwo4.translate(x_center, y_center);
			gTwo4.transform(armTrans2);
			gTwo4.fill(arm2);
			gTwo4.setColor(Color.blue);
			gTwo4.draw(arm2);
			gTwo4.setColor(Color.black);
			gTwo4.drawOval(pivotX3-radius/2, pivotY3, radius, radius);
			
			// arm3
			gTwo5 = (Graphics2D) g;
			gTwo5.translate(x_center, y_center);
			gTwo5.transform(armTrans3);
			gTwo5.fill(arm3);
			gTwo5.setColor(Color.blue);
			gTwo5.draw(arm3);
			
			// mag
			gTwo6 = (Graphics2D) g;
			gTwo6.translate(x_center, y_center);
			gTwo6.fill(mag);
			if(Switch == 0){
			gTwo6.setColor(Color.red);
			}
			else{
				gTwo6.setColor(Color.black);
			}
			gTwo6.draw(mag);
		}
		
		public boolean checkRecPos(int x, int y){
			if(x >= rectangleX && x <= rectangleX + baseWidth){
				if(y >= windowsHeight-recHeight-baseHeight && y <= windowsHeight-recHeight) return true;
				else return false;
			}
			else{
				return false;
			}
		}
		
		public int checkArmPos(int x, int y){
			int rtValue = 0;
			
			Double tempX = (x-pivotX1-rectangleX)*Math.cos(theta01)+(y-pivotY1)*Math.sin(theta01);
			Double tempY = -(x-pivotX1-rectangleX)*Math.sin(theta01)+(y-pivotY1)*Math.cos(theta01);
			int xx = tempX.intValue() + pivotX1+rectangleX;
			int yy = tempY.intValue() + pivotY1;
			int xx1, yy1, xx2, yy2;

			
			if(xx >  rectangleX+ pivotX1-armWidth/2 && xx < rectangleX+pivotX1+armWidth/2 ){
				if(yy<pivotY1 && yy>pivotY1-armHeight) rtValue = 1;
				else if(yy<pivotY2 && yy>pivotY2-armHeight) rtValue = 2;
				else if(yy<pivotY3 && yy>pivotY3-armHeight) {rtValue = 3;}
				else if(yy<pivotY3-armHeight && yy>pivotY3-armHeight-armWidth/2) rtValue = 4;
			}
			else{
				double temp2 = theta02 - theta01;
				Double tempX1 = (xx-pivotX2-rectangleX)*Math.cos(temp2)+(yy-pivotY2)*Math.sin(temp2);
				Double tempY1 = -(xx-pivotX2-rectangleX)*Math.sin(temp2)+(yy-pivotY2)*Math.cos(temp2);
				xx1 = tempX1.intValue() + pivotX2+rectangleX;
				yy1 = tempY1.intValue() + pivotY2;
				
				if(xx1 >  rectangleX+pivotX2-armWidth/2 && xx1 < rectangleX+pivotX2+armWidth/2){
					if(yy1<pivotY2 && yy1>pivotY2-armHeight) rtValue = 2;
					else if(yy1<pivotY3 && yy1>pivotY3-armHeight) rtValue = 3;
					else if(yy1<pivotY3-armHeight && yy1>pivotY3-armHeight-armWidth/2) rtValue = 4;
				}
				else{
					double temp3 = theta03 - theta02 - theta01;
					Double tempX2 = (xx1-pivotX3-rectangleX)*Math.cos(temp3)+(yy1-pivotY3)*Math.sin(temp3);
					Double tempY2 = -(xx1-pivotX3-rectangleX)*Math.sin(temp3)+(yy1-pivotY3)*Math.cos(temp3);
					xx2 = tempX1.intValue() + pivotX3+rectangleX;
					yy2 = tempY1.intValue() + pivotY3;
					
					
					if(xx2 > rectangleX+pivotX3-armWidth/2 && xx2 < rectangleX+pivotX3+armWidth/2){
						if(yy2<pivotY3 && yy2>pivotY3-armHeight) rtValue = 3;
						else if(yy2<pivotY3-armHeight && yy2>pivotY3-armHeight-armWidth/2) rtValue = 4;
					}
					else if(xx2 > rectangleX + pivotX3-2*armWidth && xx2 < rectangleX + pivotX3+2*armWidth){
						if(yy2<pivotY3-armHeight && yy2>pivotY3-armHeight-armWidth/2) rtValue = 4;
					}
				}
			}
			return rtValue;
		}
		
		public crane(){
			super();
			addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e){
					oldX = e.getX();
					oldY = e.getY();

					if(Switch == 0 && checkArmPos(oldX, oldY) == 4){
						Switch = 1;
						if(oldX > windowsWidth-2*boxWidth && oldX < windowsWidth - boxWidth){
							// pick up box1
						}
						else if(oldX > windowsWidth-boxWidth && oldX<windowsWidth){
							
						}
					}
					else if(Switch == 1 && checkArmPos(oldX, oldY) == 4){
						Switch = 0;
					}
				}
					
				public void mouseReleased(MouseEvent e){
					if(magic == 1){
						rectangleX = currentX-oldX+rectangleX;
						magic = 0;// update the location of the leftmost edge of the base
					}
					if(magic1 == 1){
						theta01 += theta1;
						magic1 = 0;
					}
					if(magic2 == 1){
						theta02 += theta2;
						magic2 = 0;
					}
					if(magic3 == 1){
						theta03 += theta3;
						magic3 = 0;
					}
					
				}
			});
			
			
			addMouseMotionListener(new MouseMotionAdapter() {
				
				public void mouseDragged(MouseEvent e) {
					currentX = e.getX();
					currentY = e.getY();
					
					if (roundRec != null && checkRecPos(oldX, oldY)){
						x = rectangleX + currentX - oldX;
						magic = 1;
					}
					else if(arm1 != null && checkArmPos(oldX, oldY) == 1){ // rotate the first arm
						theta1 = -Math.atan2(oldY-pivotY1, oldX-pivotX1) + Math.atan2(currentY-pivotY1, currentX-pivotX1);
						
						if(Math.abs(theta1) < Math.PI/2){
							armTrans1.setToRotation(theta1, pivotX1, pivotY1);
							magic1 = 1;
						}
						
					}	
					else if(arm2 != null && checkArmPos(oldX, oldY) == 2){// rotate the second arm
						theta2 = -Math.atan2(oldY-pivotY2, oldX-pivotX2) + Math.atan2(currentY-pivotY2, currentX-pivotX2);
						
						if(Math.abs(theta2) < Math.PI/2){
							armTrans2.setToRotation(theta2, pivotX2, pivotY2);
							magic2 = 1;
						}
					}
					else if(arm3 != null && checkArmPos(oldX, oldY) == 3){
						theta3 = -Math.atan2(oldY-pivotY3, oldX-pivotX3) + Math.atan2(currentY-pivotY3, currentX-pivotX3);
						
						if(Math.abs(theta3) < Math.PI/2){
								armTrans3.setToRotation(theta3, pivotX3, pivotY3);
								magic3 = 1;
						}
					}
					
					repaint();
				}
			});
		}

}
