package org.eq.modules.bc.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadFileUtils {
	
	public static List<String> readFile(String fileName){
		List<String> result = new ArrayList<String>();
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(ReadFileUtils.class.getResourceAsStream(fileName), "UTF-8"));
			String line = null;
			while((line = br.readLine())!=null){
				result.add(line);
			}
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}