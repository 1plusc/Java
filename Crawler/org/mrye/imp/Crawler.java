package org.mrye.imp;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import java.awt.Font;
import javax.swing.JProgressBar;
import javax.swing.JButton;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.mrye.function.Clear_data;
import org.mrye.function.Export_txt;
import org.mrye.h2.H2_D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Crawler extends JFrame {
	private JTextField surl;// url
	private JLabel lthreads;// 所有线程
	private JLabel lgetpages;// 所有获取的页面
	private JLabel lfinishs;// 所有处理完成的页面
	private JProgressBar progressBar;// 进度条
	private JLabel lstatus;// 进度状态

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Crawler frame = new Crawler();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Crawler() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Develope\\java_code\\OnePlusC\\ImAgE\\water.png"));
		
		setTitle("\u7F51\u7AD9\u7CBE\u7075 V1.0Beta");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 481, 307);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mfile = new JMenu("\u6587\u4EF6");
		mfile.setMnemonic('1');
		mfile.setFont(new Font("宋体", Font.PLAIN, 12));
		menuBar.add(mfile);

		JMenuItem mclose = new JMenuItem("\u9000\u51FA");
		mclose.setMnemonic('C');
		mclose.setFont(new Font("宋体", Font.PLAIN, 12));
		mclose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		mfile.add(mclose);

		JMenu medit = new JMenu("\u7F16\u8F91");
		medit.setFont(new Font("宋体", Font.PLAIN, 12));
		menuBar.add(medit);

		JMenuItem mcopy = new JMenuItem("\u590D\u5236");
		mcopy.setFont(new Font("宋体", Font.PLAIN, 12));
		medit.add(mcopy);

		JMenuItem mcut = new JMenuItem("\u526A\u5207");
		mcut.setFont(new Font("宋体", Font.PLAIN, 12));
		medit.add(mcut);

		JMenuItem mpaste = new JMenuItem("\u7C98\u8D34");
		mpaste.setFont(new Font("宋体", Font.PLAIN, 12));
		medit.add(mpaste);

		JMenu mdata = new JMenu("\u6570\u636E");
		mdata.setFont(new Font("宋体", Font.PLAIN, 12));
		menuBar.add(mdata);

		JMenuItem mexporturl = new JMenuItem("\u5BFC\u51FA\u6240\u6709url");
		mexporturl.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Export_txt et3 = new Export_txt();
				et3.export(surl.getText(), 2);
				JFrame authorFrame = new JFrame("Success");
				authorFrame.setBounds(0, 0, 90, 90);
				authorFrame.setAlwaysOnTop(true);
				JTextArea authorTextArea = new JTextArea("成功导出URL信息");
				authorTextArea.setPreferredSize(new Dimension(500, 500));
				authorFrame.getContentPane().add(authorTextArea);
				authorFrame.setLocationRelativeTo(null);
				authorFrame.setVisible(true);
			}
		});
		mexporturl.setBackground(new Color(240, 240, 240));
		mexporturl.setFont(new Font("宋体", Font.PLAIN, 12));
		mdata.add(mexporturl);

		JMenuItem mexportdir = new JMenuItem(
				"\u5BFC\u51FA\u7F51\u7AD9\u76EE\u5F55");
		mexportdir.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Export_txt et3 = new Export_txt();
				et3.export(surl.getText(), 1);

			}
		});
		mexportdir.setFont(new Font("宋体", Font.PLAIN, 12));
		mdata.add(mexportdir);

		JMenuItem mclearalldata = new JMenuItem(
				"\u6E05\u7A7A\u6240\u6709\u6570\u636E");
		mclearalldata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clear_data cd = new Clear_data();
				cd.Something_about(1);
			}
		});
		mclearalldata.setFont(new Font("宋体", Font.PLAIN, 12));
		mdata.add(mclearalldata);

		JMenu mtool = new JMenu("\u5DE5\u5177");
		mtool.setFont(new Font("宋体", Font.PLAIN, 12));
		menuBar.add(mtool);

		JMenuItem msetinfo = new JMenuItem("\u8BBE\u7F6E");
		msetinfo.setFont(new Font("宋体", Font.PLAIN, 12));
		mtool.add(msetinfo);

		JMenu mabout = new JMenu("\u5173\u4E8E");
		mabout.setFont(new Font("宋体", Font.PLAIN, 12));
		menuBar.add(mabout);

		JMenuItem mproject = new JMenuItem("\u5173\u4E8E\u7A0B\u5E8F");
		mproject.setFont(new Font("宋体", Font.PLAIN, 12));
		mabout.add(mproject);

		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);

		//

		//

		JLabel lurl = new JLabel("\u521D\u59CBURL");
		lurl.setFont(new Font("宋体", Font.BOLD, 13));
		lurl.setBounds(10, 21, 52, 15);
		panel.add(lurl);

		surl = new JTextField();
		surl.setToolTipText("\u8BF7\u586B\u5199\u7F51\u5740");
		surl.setText("http://demo.javapms.com");
		surl.setBounds(72, 18, 317, 21);
		panel.add(surl);
		surl.setColumns(10);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setRollover(true);
		toolBar.setToolTipText("\u7A0B\u5E8F\u72B6\u6001\u4FE1\u606F......");
		toolBar.setBounds(0, 234, 475, 17);
		panel.add(toolBar);

		JLabel lthreadl = new JLabel("\u7EBF\u7A0B\u6570\u8FD0\u884C\u6570 ");
		lthreadl.setFont(new Font("宋体", Font.PLAIN, 12));
		toolBar.add(lthreadl);

		lthreads = new JLabel("-1");
		lthreads.setFont(new Font("宋体", Font.BOLD, 12));
		toolBar.add(lthreads);

		JLabel lgetpagel = new JLabel(" |\u6293\u53D6\u7F51\u9875\u6570 ");
		lgetpagel.setFont(new Font("宋体", Font.PLAIN, 12));
		toolBar.add(lgetpagel);

		lgetpages = new JLabel("-1");
		lgetpages.setFont(new Font("宋体", Font.BOLD, 12));
		toolBar.add(lgetpages);

		JLabel lfinishl = new JLabel(" |\u5904\u7406\u9875\u9762\u6570 ");
		lfinishl.setFont(new Font("宋体", Font.PLAIN, 12));
		toolBar.add(lfinishl);

		lfinishs = new JLabel("-1");
		lfinishs.setFont(new Font("宋体", Font.BOLD, 12));
		toolBar.add(lfinishs);

		progressBar = new JProgressBar();
		progressBar.setMinimum(0);
		toolBar.add(progressBar);

		lstatus = new JLabel("No");
		toolBar.add(lstatus);

		JButton gstart = new JButton("GO");
		gstart.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		gstart.addActionListener(new ActionListener() {

			private int flag = 1;

			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// Alpha_Go ag = new Alpha_Go();
				// ag.get_start(surl.getText());
				// lthreads.setText(Integer.toString(ag.getThread_num()));
				// http://demo.javapms.com

				lthreads.setText("1");
				lstatus.setText("Crawling");
				Alpha_Go_thread agt = new Alpha_Go_thread(surl.getText());
				Thread t_mc = new Thread(agt, "爬虫爬呀爬..");
				t_mc.start();

				while (flag == 1) {
					try {
						Thread thread = Thread.currentThread();
						thread.sleep(3500);
					} catch (InterruptedException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}// 暂停1.5秒后程序继续执行
					try {
						H2_D h2d = new H2_D();
						ResultSet rs = null;
						rs = h2d.select_all("select * from status ");
						while (rs.next()) {
							flag = rs.getInt("flag");
							lgetpages.setText(rs.getString("allpages"));
							lfinishs.setText(rs.getString("finishedpages"));
							progressBar.setMaximum(Integer.parseInt(rs
									.getString("allpages")));
							progressBar.setValue(Integer.parseInt(rs
									.getString("finishedpages")));
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				lstatus.setText("OK");
			}
		});

		gstart.setBounds(399, 17, 66, 22);
		panel.add(gstart);

		JLabel img_label = new JLabel("");
		img_label.setEnabled(false);
		img_label.setHorizontalAlignment(SwingConstants.CENTER);
		img_label.setBounds(0, 0, 475, 251);
		panel.add(img_label);
		img_label.setIcon(new ImageIcon(
				"D:\\Develope\\java_code\\OnePlusC\\ImAgE\\background.jpg"));
	}
}
