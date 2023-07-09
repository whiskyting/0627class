package test0613;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class WinFrame extends JFrame{


private Map<String, List<JButton>> buttons = new HashMap<String,List<JButton>>();
	
	private JTextField jtfRoom,jtfName,jtfIP;
	private static JTextArea jtaR;

	private JTextArea jtaD;
	private JScrollPane jspR;
//	jspD;
	private static JButton[] bingo;
	private JButton connectIP;

	private static JButton start;

	private static JButton auto;

	private static JButton clear;

	private JButton exit;
	private boolean room=false,startF=false;
	private static int num = 1, rdn ,index = 0;
	static int[] Number = new int[25];

	
	private void addButton(String key,JButton button) {
		if(! buttons.containsKey(key)) {
			buttons.put(key, new ArrayList<JButton>());
		}
		
		List<JButton> buttons = this.buttons.get(key);
		if(!buttons.contains(button)) {
			buttons.add(button);
			add(button);
			
		}
		
	}
	
	
	
	private List<JButton> getButton(String key) {
        if (! buttons.containsKey(key))
            buttons.put(key, new ArrayList<JButton>());
        return buttons.get(key);
    }
	
	
	
	
	public void regElement() {
		setTitle("BinGo");
		setLayout(null);
		setBounds(100,100,716, 535);
		setResizable(false); //視窗無法調整大小
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		jtfRoom = new JTextField();
		jtfName = new JTextField();
		jtfIP = new JTextField();
		jtaR = new JTextArea();
//		jtaD = new JTextArea();
		jspR = new JScrollPane(jtaR);
//		jspD = new JScrollPane(jtaD);
		connectIP = new JButton();
		start = new JButton("開始");
		auto = new JButton("自動選號");
		clear = new JButton("重新選號");
		exit = new JButton("離開");
		
        jtfName.setBounds(10, 10, 90, 30);
        jtfName.setEditable(false);
        add(jtfName);
		
        jtfRoom.setBounds(110, 10, 190, 30);
        jtfRoom.setEditable(false);
        jtfRoom.setText("未加入房間");
        add(jtfRoom);
		
        jtfIP.setBounds(310, 10, 280, 30);
        jtfIP.setEditable(false);
        jtfIP.setText("127.0.0.1");//預設IP
        add(jtfIP);
        
        connectIP.setBounds(600, 10, 90, 30);
        connectIP.setText("連接");
        add(connectIP);
        
        jspR.setBounds(550, 60, 140, 420);
        jtaR.setEditable(false);
        add(jspR);
        
//        jspD.setBounds(550, 410, 140, 80);
//        add(jspD);
        
        start.setBounds(30,360,240,60);
        addButton("start", start);
        

        
        auto.setBounds(280,360,240,60);
        addButton("auto", auto);

        
        clear.setBounds(30,430,240,60);
        addButton("clear", clear);

        exit.setBounds(280,430,240,60);
        addButton("exit", exit);

        
        SwingUtilities.updateComponentTreeUI(this);
        
        

	}
	
	//遊戲運行初始化
	private void initComp() {
		
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				start.setEnabled(false);
				if(start.getText().equals("準備")|| num == 26) {
					for(JButton start : getButton("start")) {
						start.setEnabled(false);
					}
					for(JButton auto : getButton("auto")) {
						auto.setEnabled(false);
					}
					for(JButton clear : getButton("clear")) {
						clear.setEnabled(false);
					}
				}
				
				start();
				
				
				
			}
		});
		
		auto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
				auto();
				start.setEnabled(true);
				
			}
		});
		
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
				start.setEnabled(false);
				
			}
		});
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
//				checkProgress();
				
			}
		});
		
//		jtaD.addKeyListener(new KeyListener() {
//			
//			boolean flagS = false;
//			boolean flagE = false;
//			
//			
//			@Override
//			//按著不做功能
//			public void keyTyped(KeyEvent e) {
//				
//			}
//			
//			@Override
//			//釋放按鈕 未完成
//			public void keyReleased(KeyEvent e) {
//				if (flagE) { //按下enter
//					if(flagS) { //強迫換行
//						jtaD.setText(jtaD.getText()+"\r\n");
//					}else { //送出
////						send
//					}
//				}
////				if ()
//				
//			}
//			
//			@Override
//			//按下按鈕
//			public void keyPressed(KeyEvent e) {
//				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
//					flagE = true;
//				}
//				if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
//					flagS = true;
//				}
//			}
//		});
		
		
	}
	
	
	
	
	
//	private void checkProgress() {
//		if(room) {
//			if (startF) {
//				for (JButton menu : this.getButton("menu")) {
//					menu.setEnabled(false);
//				}
//			}else {
//				for(JButton menu : this.getButton("munu")) {
//					menu.setEnabled(true);
//				}
//			}
//			
//		}
//	}
	
	
	//各按鈕功能
	private void start() {
		
		if(start.getText().equals("準備")) {
			boolean isRepeat;
			for(int i=0 ; i<25;i++) {
				do {
					
					rdn = (int)(Math.random()*25+1);
					//檢查機制
					isRepeat = false;
					for (int j=0; j<i; j++) {
						if (Number[j] == rdn) {
							isRepeat = true;
							break;
						}
					}
					
				}while(isRepeat);
				Number[i] = rdn;
			}
			
			index = 1;
			jtaR();
			
			
		}else if(start.getText().equals("開始")) {
			for(JButton start : getButton("start")) {
				start.setText("準備");
			}
			int w = 450/5;
			int h = 250/5;
			this.bingo = new JButton[5*5];
			for (int x=0 ; x<5;x++) {
				for(int y=0;y<5;y++) {
					JButton bingo = new JButton();
					bingo.setBounds(30+x*(w+10), 60+y*(h+10), w, h);
					addButton("bingo", bingo);
					this.bingo[y*5+x] = bingo;

					bingo.addActionListener(new NumberbuttonAction(this, x, y) {
					});
					
				}
			}
			repaint(); //渲染
		}
	}
	
	private static class NumberbuttonAction  implements ActionListener {
		
		final int pos;
		final WinFrame core;
		
		private NumberbuttonAction (WinFrame core,int x ,int y) {
			this.core = core;
			this.pos = y * 5 + x;
		}
		
		public void actionPerformed(ActionEvent e) {
			if(bingo[pos].getBackground() != Color.pink
					&& bingo[pos].getBackground() != Color.red ) {
				bingo[pos].setBackground(Color.pink);
				bingo[pos].setText(Integer.toString(num++));
			}
			if (num == 26 && auto.isEnabled()) {
				start.setEnabled(true);
				
			}
			play(pos);

			
			

		}
	}
	
	private static void play(int pos) {
		
		
		//判斷連線
		int line = 0;
		if (!start.isEnabled() && !auto.isEnabled() && !clear.isEnabled()) {
			if(bingo[pos].getText().equals(Integer.toString(Number[index]) )) {
				bingo[pos].setBackground(Color.red);
				index ++;
				jtaR.append("\n第"+index+"個數字"+Integer.toString(Number[index]));
			}else {
				jtaR.append("\n選錯囉，請選"+Number[index]);
			}
			
		}
		if (bingo[0].getBackground()== Color.red &&
			bingo[1].getBackground()== Color.red &&
			bingo[2].getBackground()== Color.red &&
			bingo[3].getBackground()== Color.red &&
			bingo[4].getBackground()== Color.red ) {
			line++;
		}
		if (bingo[5].getBackground()== Color.red &&
			bingo[6].getBackground()== Color.red &&
			bingo[7].getBackground()== Color.red &&
			bingo[8].getBackground()== Color.red &&
			bingo[9].getBackground()== Color.red ) {
				line++;
			}
		if (bingo[10].getBackground()== Color.red &&
			bingo[11].getBackground()== Color.red &&
			bingo[12].getBackground()== Color.red &&
			bingo[13].getBackground()== Color.red &&
			bingo[14].getBackground()== Color.red ) {
				line++;
			}
		if (bingo[15].getBackground()== Color.red &&
			bingo[16].getBackground()== Color.red &&
			bingo[17].getBackground()== Color.red &&
			bingo[18].getBackground()== Color.red &&
			bingo[19].getBackground()== Color.red ) {
					line++;
				}
		if (bingo[20].getBackground()== Color.red &&
			bingo[21].getBackground()== Color.red &&
			bingo[22].getBackground()== Color.red &&
			bingo[23].getBackground()== Color.red &&
			bingo[24].getBackground()== Color.red ) {
					line++;
				}
		if (bingo[0].getBackground()== Color.red &&
			bingo[5].getBackground()== Color.red &&
			bingo[10].getBackground()== Color.red &&
			bingo[15].getBackground()== Color.red &&
			bingo[20].getBackground()== Color.red ) {
					line++;
				}
		if (bingo[1].getBackground()== Color.red &&
			bingo[6].getBackground()== Color.red &&
			bingo[11].getBackground()== Color.red &&
			bingo[16].getBackground()== Color.red &&
			bingo[21].getBackground()== Color.red ) {
					line++;
				}
		if (bingo[2].getBackground()== Color.red &&
			bingo[7].getBackground()== Color.red &&
			bingo[12].getBackground()== Color.red &&
			bingo[17].getBackground()== Color.red &&
			bingo[22].getBackground()== Color.red ) {
					line++;
				}
		if (bingo[3].getBackground()== Color.red &&
			bingo[8].getBackground()== Color.red &&
			bingo[13].getBackground()== Color.red &&
			bingo[18].getBackground()== Color.red &&
			bingo[23].getBackground()== Color.red ) {
					line++;
				}
		if (bingo[4].getBackground()== Color.red &&
			bingo[9].getBackground()== Color.red &&
			bingo[14].getBackground()== Color.red &&
			bingo[19].getBackground()== Color.red &&
			bingo[24].getBackground()== Color.red ) {
					line++;
				}
		if (bingo[0].getBackground()== Color.red &&
			bingo[6].getBackground()== Color.red &&
			bingo[12].getBackground()== Color.red &&
			bingo[18].getBackground()== Color.red &&
			bingo[24].getBackground()== Color.red ) {
					line++;
				}
		if (bingo[4].getBackground()== Color.red &&
			bingo[8].getBackground()== Color.red &&
			bingo[12].getBackground()== Color.red &&
			bingo[16].getBackground()== Color.red &&
			bingo[20].getBackground()== Color.red ) {
					line++;
				}
		ImageIcon picture = new ImageIcon("src/test0613/bingo.png");
		
		
		if (line == 3) {
			JOptionPane.showConfirmDialog(null, "", "BinGo", JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,picture);
		}
		
		if (line < 3) {
			
		}
		
		

	}
	
	
	private void auto() {
		boolean isRepeat;
		int[] ButtonNumber = new int[25];
		int n;
		
		for(int i=0 ; i<25;i++) {
			do {
				
				n = (int)(Math.random()*25);
				//檢查機制
				isRepeat = false;
				for (int j=0; j<i; j++) {
					if (ButtonNumber[j] == n) {
						isRepeat = true;
						break;
					}
				}
				
			}while(isRepeat);
			ButtonNumber[i] = n;
			

			if (bingo[n].getBackground() != Color.pink) {
				bingo[n].setText(Integer.toString(i+1));
	            bingo[n].setBackground(Color.pink);
			}
			
		}

		
	}
	
	private void clear() {
		
		num = 1;
		for (JButton bingo : getButton("bingo")) {
            bingo.setText("");
            bingo.setBackground(null);
		}
	}
	
	private void exit() {
		System.exit(0);
	}
	
	private static void jtaR() {
		
		jtaR.setText("第"+index+"個數字"+Integer.toString(Number[index]) );
		
		
	}

	

	
	public static void main(String[] args) {
		WinFrame fwk =	new WinFrame();
		fwk.regElement();
		fwk.initComp();
		
		
	}

	

}
