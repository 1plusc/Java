/**
 * 
 */
package org.mrye.function;


import org.mrye.h2.H2_D;

/**
 * @author Alpha
 *
 */
public class Clear_data {

	/**
	 * ����KEYֵ����ɾ����Щ���ݱ������
	 * @param key
	 */
	public void Something_about(int key){
		switch (key) {
		case 1:
			clear_all_dbdata();
			break;

		default:
			break;
		}
	}

	/**
	 * ɾ���������ݱ������
	 */
	private void clear_all_dbdata() {
		// TODO Auto-generated method stub
		H2_D h2d;
		try {
			h2d = new H2_D();
			h2d.delete("delete from directory");
			h2d.delete("delete from domain");
			h2d.delete("delete from headers");
			h2d.delete("delete from queue");
			h2d.delete("delete from status");
			h2d.delete("delete from user");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
