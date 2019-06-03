package org.eq.modules.bc.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.stream.FileImageOutputStream;

public class FileUtil {
	
	/**
	 * 将网络图片下载到本地服务器
	 * 
	 * @param imageurl
	 * @param name
	 * @return
	 */
	public static String getImageByUrl(String imageurl, String name, String fileServerPath) {
		InputStream is = null;
		OutputStream os = null;
		try {

			// 构造URL
			URL url = new URL(imageurl);

			// 打开连接
			URLConnection con = url.openConnection();

			// 输入流
			is = con.getInputStream();

			// 1K的数据缓冲
			byte[] bs = new byte[1024];

			// 读取到的数据长度
			int len;

			File file = new File(fileServerPath);// (String)
													// property.get("ewmPath"));

			if (!file.exists()) {

				file.mkdirs();

			}

			// 输出的文件流
			os = new FileOutputStream(fileServerPath + name);

			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}

			return name;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 完毕，关闭所有链接
			if (!Tools.isNull(is)) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (!Tools.isNull(os)) {
				try {
					os.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		}
		return null;
	}
	
	 public static boolean byte2image(byte[] data, String path) {
	        try {
	            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
	            imageOutput.write(data, 0, data.length);
	            imageOutput.close();
	            
	            return true;
	        } catch (Exception ex) {
	            System.out.println("Exception: " + ex);
	            ex.printStackTrace();
	        }
	        return false;
	    }
}
