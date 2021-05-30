package cij;

import java.io.File;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Arrays;

public class FileRetriver {
	private String projectDataPath; // the folder should end with *_data

	public FileRetriver(String projectDataPath) {
		this.projectDataPath = projectDataPath;
	}

	public String[] getCommitDirectories() {
		File commitsPath = new File("./python/"+this.projectDataPath);
		return commitsPath.list();
	}

	@SuppressWarnings("unchecked")
	public List<String> getCommitAfterChangeJavaFiles(String commitHashDirectory) {
		String afterChangeFilesDirectoryName = "./python/"+this.projectDataPath+"/"+commitHashDirectory+"/after";
		File afterChangeFilesDirectory = new File(afterChangeFilesDirectoryName);
		return Arrays.asList(afterChangeFilesDirectory.list());
	}

	@SuppressWarnings("unchecked")
	public List<String> getCommitBeforeChangeJavaFiles(String commitHashDirectory) {
		String beforeChangeFilesDirectoryName = "./python/"+this.projectDataPath+"/"+commitHashDirectory+"/before";
		File beforeChangeFilesDirectory = new File(beforeChangeFilesDirectoryName);
		return Arrays.asList(beforeChangeFilesDirectory.list());
	}
}
