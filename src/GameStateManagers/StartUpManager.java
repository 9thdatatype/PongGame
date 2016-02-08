package GameStateManagers;

import java.io.File;
import fileManagement.GenericFileIO;

public class StartUpManager {
	
	public StartUpManager(String configFilePath){
		if(!new File(configFilePath).exists())
			return;
		
		configFile = new File(configFilePath);
	}
	
	public StartUpManager(File configFile){
		if(!configFile.exists())
			return;
		
		this.configFile = configFile;
	}
	
	public String loadSettings(){
		String returnMe = null;
		String configSettings[] = new GenericFileIO(configFile).readEntireFile();
		
		if(configSettings == null)
			return null;
		
		for(String string:configSettings)
			returnMe = string + "\n";
		
		return returnMe;
	}
	
	private File configFile;
	
}