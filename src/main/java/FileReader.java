

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
//import org.mule.api.MuleEventContext;
//import org.mule.api.MuleMessage;
//import org.mule.api.lifecycle.Callable;
//import org.mule.api.transport.PropertyScope;

public class FileReader {
	
	private String dataDirectory;
	private String fileName;
	private String contentPropertyName;
	
//	@Override
//	public Object onCall(MuleEventContext muleEventContext) throws Exception {
//		MuleMessage message = muleEventContext.getMessage();
//		if (!fileName.contains("**.*")) {
//			String content = readCurrentFileToString();
//			message.setPayload(content);
//			message.setProperty(contentPropertyName, content, PropertyScope.SESSION);
//		} else {
//			Map<String, String> fileContent  = new HashMap<String, String>();
//			Collection<File> filesInDataDirectory = FileUtils.listFiles(new File(dataDirectory), null, false);
//			for (File file : filesInDataDirectory) {
//				String targetfileName = file.getName();
//				String targetContent = readFileToString(targetfileName);
//				fileContent.put(targetfileName, targetContent);
//			}
//			message.setPayload(fileContent);
//			message.setProperty(contentPropertyName, fileContent, PropertyScope.SESSION);
//		}
//		return message;
//	}

	public String readCurrentFileToString() throws Exception {
		String filePath = dataDirectory + File.separatorChar + fileName;
		System.out.println("Reading from file path " + filePath);
		File file = new File(filePath);
		String content = FileUtils.readFileToString(file);
		return content;
	}
	
	public String readFileToString(String fileName) throws Exception {
		String filePath = dataDirectory + File.separatorChar + fileName;
		System.out.println("Reading from file path " + filePath);
		File file = new File(filePath);
		Charset charset = StandardCharsets.UTF_8;
		String content = FileUtils.readFileToString(file, charset);
		return content;
	}
	
	public void writeStringToFile(String fileName, String content) throws Exception {
		String filePath = dataDirectory + File.separatorChar + fileName;
		System.out.println("Writing to file path " + filePath);
		File file = new File(filePath);
		FileUtils.write(file, content);
	}
	
	public String getDataDirectory() {
		return dataDirectory;
	}

	public void setDataDirectory(String dataDirectory) {
		this.dataDirectory = dataDirectory;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentPropertyName() {
		return contentPropertyName;
	}

	public void setContentPropertyName(String contentPropertyName) {
		this.contentPropertyName = contentPropertyName;
	}

}
