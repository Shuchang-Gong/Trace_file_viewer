import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JPanel;

public class Draw extends JPanel {

	public int index;
	public Double[][] finish_arr;
	public boolean con = false;
	public int[] num;
	public double[] new_arr;

	public Draw() {
		setBackground(Color.white);
		setBounds(0, 100, 1000, 325);
	}

	public Draw(int index, Double[][] arr, int[] num) {
		setBackground(Color.white);
		setBounds(0, 100, 1000, 325);
		this.finish_arr = arr;
		con = true;
		this.index = index;
		this.num = num;

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawLine(40, 275, 900, 275); // draw x-axis
		g.setColor(Color.black);
		g.drawLine(45, 280, 45, 25); // draw y-axis
		g.setColor(Color.black);
		g.drawString("Time [s]", 450, 315); // draw label
		g.drawString("Volume [bytes]", 5, 20);
		g.drawString("0", 15, 280);
	

		for (int i = 0; i < 13; i++) {
			if (i == 0) {
				g.drawString("0", 42, 295);
			} else if (i == 1) {
				int x = i * 50;
				String xx = String.valueOf(x);
				g.drawLine(45 + i * 69, 275, 45 + i * 69, 280);
				g.drawString(xx, 40 + i * 69, 295);
			} else {
				int x = i * 50;
				String xx = String.valueOf(x);
				g.drawLine(45 + i * 69, 275, 45 + i * 69, 280);
				g.drawString(xx, 35 + i * 69, 295);
			}
		}

		if (con) {
			g.drawLine(942, 275, 942, 280);
			g.drawLine(900, 275, 950, 275);
			g.drawString("650", 932, 295);
			for(int j = 0; j <= 7; j++) {
				g.drawLine(40, 275 - j*35, 45, 275 - j*35);
				if (j <= 4 && j != 0) {
					int z = j * 200;
					String zz = String.valueOf(z) + "kB";
					g.drawString(zz, 3, 280 - j*35);		
				}else if(j > 4){
					double z = j * 200 / 1000.0;
					String zz = String.valueOf(z) + "M";
					g.drawString(zz, 6, 280 - j*35);	
				}
			}
	//		System.out.println("in the draw");
			double ratio = 900.0 / 650.0;
			double max_num = 250 / finish_arr[index][num[index]];
			for (int i = 0; i < 656; i++) {
				if (finish_arr[index][i] != 0.0) {
			//		System.out.println(finish_arr[index][i]);
					double y2 = max_num * finish_arr[index][i];
					int y = (int) y2;
			//		System.out.println(y);
					g.drawLine((int) Math.round(i * ratio + 45), 275, (int) Math.round(i * ratio + 45), 275-y);
					g.setColor(Color.red);
				}
			}

		}    // end the if 
	}

}
