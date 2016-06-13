/**
 * 
 */
package org.mrye.imp;

/**
 * @author Alpha
 * 
 */
public class Alpha_Go  {
	private int thread_num;
	private int getallpages_num;
	private int hasfinished_num;
	private String status;

	public String getStatus() {
		return status;
	}

	private void setStatus(String status) {
		this.status = status;
	}

	public int getThread_num() {
		return thread_num;
	}

	public int getGetallpages_num() {
		return getallpages_num;
	}

	public int getHasfinished_num() {
		return hasfinished_num;
	}

	private void setThread_num(int thread_num) {
		this.thread_num = thread_num;
	}

	private void setGetallpages_num(int getallpages_num) {
		this.getallpages_num = getallpages_num;
	}

	private void setHasfinished_num(int hasfinished_num) {
		this.hasfinished_num = hasfinished_num;
	}

	public void get_start(String url) {
		System.out.println(url);
		setThread_num(1);
		
		My_Crawing mc=new My_Crawing(url);
		Thread t_mc = new Thread(mc, "ÅÀ³æÅÀÑ½ÅÀ..");
		t_mc.start();
		
		
	}
}
