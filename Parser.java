import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is thread safe.
 */
public class Parser {
	private File file;
	private final Object LOCK = new Object();

	public void setFile(File f) {
		synchronized (LOCK) {
			file = f;	
		}
	}

	public File getFile() {
		synchronized (LOCK) {
			return file;	
		}
	}

	public String getContent() throws IOException {
		StringBuilder outputBuilder = new StringBuilder();
		BufferedReader bir = null;
		try {
			bir = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = bir.readLine()) != null){
				outputBuilder.append(line);
			}
		}finally{
			if(bir != null){
				bir.close();				
			}
		}
		return outputBuilder.toString();
	}

	public String getContentWithoutUnicode() throws IOException {
		
		StringBuilder outputBuilder = new StringBuilder();
		BufferedReader bir = null;
		try {
			bir = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = bir.readLine()) != null){
				outputBuilder.append(filterUnicodeChars(line));
			}
		}finally{
			if(bir != null){
				bir.close();				
			}
		}
		
		return outputBuilder.toString();
	}

	public void saveContent(String content) throws IOException {
		BufferedWriter bir = null;
		try {
			bir = new BufferedWriter(new FileWriter(file));
			bir.write(content);
		} finally {
			if(bir != null){
				bir.close();
			}
		}
	}
	
	private String filterUnicodeChars(String line) {
		String nonUnicodeStr = null;
		for(int i = 0; i < line.length(); i++){
			char c = line.charAt(i);
			if (c < 0x80) {
				nonUnicodeStr += c;
			}
		}
		return nonUnicodeStr;
	}
}
