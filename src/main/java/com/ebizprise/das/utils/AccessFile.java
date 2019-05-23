//檔案存取vector處理
package com.ebizprise.das.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessFile {
	private static Logger logger = LoggerFactory.getLogger(AccessFile.class
			.getName());

	public static String createtofind(String filename) {

		String str = "shit";
		try {
			File ft = new File(filename);// myserver/keep/main
			if (ft.createNewFile() == true) {
				str = "ya";
			}
		} catch (Exception e) {
			return e.toString();
		}

		return str;
	}

	// ------------------------------------------------------------
	public static StringBuffer readTextFile(String filepath, String charset) {
		// boolean re = false;
		StringBuffer content = new StringBuffer();
		try {
			BufferedReader t = new BufferedReader(new InputStreamReader(
					new FileInputStream(filepath), charset));

			// 讀出資料
			String s = "";
			while ((s = t.readLine()) != null) {
				// s = new String(s.getBytes("8859_1"),"Big5");
				content.append(s + "\n");
			}
			t.close();
			// 寫入成功
			// re = true;
		} catch (Exception e) {
			System.out.println(" error : file \"" + filepath + "\" can't read");
		}
		return content;
	}

	public static StringBuffer readTextFile_trim(String filepath, String charset) {
		// boolean re = false;
		StringBuffer content = new StringBuffer();
		try {
			BufferedReader t = new BufferedReader(new InputStreamReader(
					new FileInputStream(filepath), charset));

			// 讀出資料
			String s = "";
			while ((s = t.readLine()) != null) {
				// s = new String(s.getBytes("8859_1"),"Big5");
				s = StringUtils.replace(s, "\t", "");
				s = StringUtils.replace(s, "\n", "");
				content.append(s);
			}
			t.close();
			// 寫入成功
			// re = true;
		} catch (Exception e) {
			System.out.println(" error : file \"" + filepath + "\" can't read");
		}
		return content;
	}

	// 讀檔傳回vector每筆只有一串字
	public static Vector ReadFile(String filename) {
		Vector v = new Vector();
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String str = "";

			while (true) {
				str = br.readLine();
				if (str == null)
					break;
				else
					v.addElement(str);
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			return null;
		}
		return v;
	}// end of ReadFile
		// ------------------------------------------------------------

	// 讀檔傳回vector每筆只有一串字

	public static List ReadFile_to_List(String filename) {
		List v = new ArrayList();
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String str = "";

			while (true) {
				str = br.readLine();
				if (str == null)
					break;
				else
					v.add(str);
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			return null;
		}
		return v;
	}// end of ReadFile

	public static int indexOfList(List word, String searchSeq) {
		// try {
		for (int i = 0; i < word.size(); i++) {
			String line = (String) word.get(i);
			int m = StringUtils.indexOf(line, searchSeq);
			if (m >= 0)
				return i;
		}
		return -1;
		// } catch (Exception e) {
		// }
		// return 0;
	}

	// ------------------------------------------------------------

	public static String WriteFile(String filepath, String filename,
			String str, String charset) {
		try {
			createDirs(filepath, true);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filepath + "/" + filename), charset));
			bw.write(str);
			bw.close();
		} catch (Exception e) {
			return e.toString();
		}
		return "success";
	}

	public static String WriteFile(String filename, String str, String charset) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filename), charset));
			bw.write(str);
			bw.close();
		} catch (Exception e) {
			return e.toString();
		}
		return "success";
	}

	// ------------------------------------------------------------
	/*
	 * public static String WriteFile(String filepath,String filename,String
	 * str) { try { createDirs(filepath, true); FileWriter fw=new
	 * FileWriter(filepath+"/"+filename); BufferedWriter bw=new
	 * BufferedWriter(fw); bw.write(str); bw.close(); fw.close(); }
	 * catch(Exception e) { return e.toString(); } return "success"; }
	 */
	// ------------------------------------------------------------
	public static String WriteFile(String file_path, String filename,
			ArrayList ayAll) {
		try {
			java.io.File ft = new java.io.File(file_path);
			if (!ft.exists())
				ft.mkdirs();

			FileWriter fw = new FileWriter(file_path + "/" + filename);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < ayAll.size(); i++) {
				Object obj1 = (Object) ayAll.get(i);

				if (obj1 == null) {
				} else if (obj1.getClass().toString()
						.equals("class java.lang.String")) {
					bw.write((String) obj1);
					bw.newLine();
				} else if (obj1.getClass().toString()
						.equals("class java.util.ArrayList")) {
					ArrayList ayRow = (ArrayList) obj1;
					String cStr = (String) ayRow.toString();
					cStr = StringUtils.replace(cStr, "[", "");
					cStr = StringUtils.replace(cStr, "]", "");
					bw.write(cStr);
					bw.newLine();
					/*
					 * for(int j=0;j<ayRow.size();j++) {
					 * 
					 * bw.write((String)ayRow.get((j))); bw.newLine(); }
					 */
				}

			}

			bw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
		return "success";
	}

	// ------------------------------------------------------------
	public static String WriteFile(String filename, List ay, String charset) {
		try {
			OutputStreamWriter fw = new OutputStreamWriter(
					new FileOutputStream(filename, false),
					StandardCharsets.UTF_8);
			// FileWriter fw = new FileWriter(filename, false);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < ay.size(); i++) {
				bw.write((String) ay.get(i));
				bw.newLine();
			}

			bw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}

	public static void checkCheateDir(String filename) {
		int index1 = StringUtils.lastIndexOf(filename, "/");
		if (index1 > 0) {
			String dir = filename.substring(0, index1);
			createDirs(dir, true);
		}
	}

	public static String WriteSQL(String filename, List ay) {
		try {
			checkCheateDir(filename);

			FileWriter fw = new FileWriter(filename, true);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < ay.size(); i++) {
				bw.write((String) ay.get(i) + ";");
				bw.newLine();
			}

			bw.close();
			fw.close();
			logger.info("匯出:" + filename);
		} catch (Exception e) {
			return e.toString();
		}
		return "success";
	}

	// ------------------------------------------------------------
	public static String WriteFile(String filename, String sa[]) {
		try {
			FileWriter fw = new FileWriter(filename);
			BufferedWriter bw = new BufferedWriter(fw);

			String s1, s2;
			for (int i = 0; i < sa.length; i++) {
				s1 = "";
				s2 = String.valueOf(i);
				for (int j = 3; j > s2.length(); j--)
					s1 += "0";
				s1 += s2 + "." + sa[i];
				bw.write(s1);
				bw.newLine();
			}

			bw.close();
			fw.close();
		} catch (Exception e) {
			return e.toString();
		}
		return "success";
	}

	// ------------------------------------------------------------
	public static String WriteFile(String filename, String[][] sda) {
		try {
			FileWriter fw = new FileWriter(filename);
			BufferedWriter bw = new BufferedWriter(fw);

			String str;
			for (int i = 0; i < sda.length; i++) {
				str = "";
				for (int j = 0; j < sda[i].length; j++) {
					str += sda[i][j] + ",";
				}
				bw.write(str.substring(0, str.length() - 1));
				bw.newLine();
			}

			bw.close();
			fw.close();
		} catch (Exception e) {
			return e.toString();
		}
		return "success";
	}

	// ------------------------------------------------------------
	public static String ReadFile(String filename, String[] sa) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			int i = 0;
			String s1;
			while (true) {
				s1 = br.readLine();
				if (s1 == null)
					break;
				else {
					sa[i++] = s1.substring(4);
				}
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			for (int i = 0; i < sa.length; i++) {
				sa[i] = new String("預設");
			}
			return e.toString();
		}
		return "success";
	}// end of ReadFile
		// ------------------------------------------------------------

	// 讀檔傳回vector每筆資料都是一個vector,子項以 , 分隔

	public static Vector ReadFileV(String filename) {
		Vector vt2 = new Vector();
		Vector vt1 = new Vector();
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String str = "";
			while (true) {
				str = br.readLine();
				if (str == null)
					break;
				else {
					vt2 = new Vector();
					for (int j = 0, k; j < 1000; j++) {
						k = str.indexOf(",");
						if (k == -1) {
							vt2.add(str);//
							break;
						} else {
							vt2.add(str.substring(0, k));
							str = str.substring(k + 1);
						}
					}
					vt1.add(vt2);
				}
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
			return vt1;
		}
		return vt1;
	}// end of ReadFile
		// ------------------------------------------------------------

	public static String Delete(String filename) {

		String str = "";
		try {
			File ft = new File(filename);
			str = String.valueOf(ft.delete());
		} catch (Exception e) {
			return e.toString();
		}
		return str;
	}

	// ------------------------------------------------------------
	public static String DeleteDir(String filename) {
		String str = "true";
		try {
			File ft = new File(filename);
			File[] z = ft.listFiles();
			for (int i = 0; i < z.length; i++) {
				if (z[i].isFile() || z[i].isHidden())
					z[i].delete();
				if (z[i].isDirectory())
					DeleteDir(z[i].toString());
			}
			ft.delete();
		} catch (Exception e) {
			return e.toString();
		}
		return str;
	}

	// ------------------------------------------------------------
	public static boolean check(String filename) {

		boolean b1 = check1(filename);
		if (b1) {
			return true;
		}
		boolean b2 = check2(filename);
		if (b2) {
			return true;
		}
		return false;
	}

	public static boolean check2(String filename) {

		File ft = null;
		try {
			String encoding = System.getProperty("file.encoding");// 获取系统编码
			ft = new File(new String(filename.getBytes("UTF-8"), encoding));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ft.exists();
	}

	public static boolean check1(String filename) {

		File ft = null;
		try {
			ft = new File(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ft.exists();
	}

	/**
	 * 將某個目錄內的所有檔案複製至另一個目錄.
	 * 
	 * @param sSource
	 *            來源目錄,路徑必須為完整路徑且後面不可加上 "\".
	 * @param sTarget
	 *            目的目錄,路徑必須為完整路徑且後面不可加上 "\".
	 * @throws IOException
	 */
	public static void copyDirFiles(String sSource, String sTarget)
			throws IOException {
		File ffs = new File(sSource);
		if (ffs.exists() != true)
			return;
		if (ffs.isDirectory() != true)
			return;
		createDirs(sTarget, true);
		File vf[] = ffs.listFiles();
		for (int j = 0; j < vf.length; j++) {
			if (vf[j].isFile()) {
				String sFileName = vf[j].getName();
				copyfile(sSource + "/" + sFileName, sTarget + "/" + sFileName);
			}
		}
	}

	// public static void copyFile(String src_file, String tag_file)
	// throws IOException {
	// InputStream in = new BufferedInputStream(new FileInputStream(src_file));
	// OutputStream out = new BufferedOutputStream(new
	// FileOutputStream(tag_file));
	// byte[] buffer = new byte[(int) (new File(src_file)).length()];
	// in.read(buffer);
	// out.write(buffer);
	// in.close();
	// out.close();
	// }
	public static void copyfile(String source_file, String distin_file) {
		String fromFileName = source_file;
		String toFileName = distin_file;
		FileChannel in = null;
		FileChannel out = null;
		try {
			in = new FileInputStream(fromFileName).getChannel();
			out = new FileOutputStream(toFileName).getChannel();
			in.transferTo(0, (int) in.size(), out);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 建立完整目錄
	 * 
	 * @param ignoreIfExitst
	 *            true:檔案存在就不管 false:不管存不存在都建
	 */
	public static File createDirs(String dir, boolean ignoreIfExitst) {
		File file = new File(dir);
		if (ignoreIfExitst && file.exists())
			return file;
		if (file.mkdirs() == false) {
			System.out.println("Cannot create directories = " + dir);
		}
		return file;
	}

	public static void copyfile(InputStream inputStream, String distin_file) {
		OutputStream os = null;
		try {
			// 打开流
			os = new FileOutputStream(distin_file);
			// 文件拷贝
			byte flush[] = new byte[1024];
			int len = 0;
			while (0 <= (len = inputStream.read(flush))) {
				os.write(flush, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();

				if (os != null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
} // end of class login
