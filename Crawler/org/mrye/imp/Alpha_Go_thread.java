/**
 * 
 */
package org.mrye.imp;

/**
 * @author Alpha
 * 
 */
public class Alpha_Go_thread implements Runnable {

	private String url = "";

	public Alpha_Go_thread(String url) {
		super();
		this.url = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(url);
		My_Crawing mc=new My_Crawing(url);
		Thread t_mc = new Thread(mc, "≈¿≥Ê≈¿—Ω≈¿..");
		t_mc.start();
	}

}
