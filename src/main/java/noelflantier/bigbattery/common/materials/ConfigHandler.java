package noelflantier.bigbattery.common.materials;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.io.IOUtils;

import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.common.handlers.ModConfig;
import noelflantier.bigbattery.common.materials.MaterialsHandler.Material;

public class ConfigHandler {
	public static List<Material> loadRecipeConfig(String fileName){
		File file = new File(ModConfig.configDirectory, fileName);
	    String defaultVals = null;
    	try{
    		defaultVals = readRecipes(file,fileName);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
        if(!file.exists()) {
        	System.out.println("Could not load default recipes from " + file + " as the file does not exist.");
        	return null;
        }
        
        List<Material> rh;
        try {
        	rh = RecipeParser.parse(defaultVals);
        } catch (Exception e) {
        	return null;
        }
        
        return rh;
	}

	public static String readRecipes(File file, String fileName) throws IOException {
		if(file.exists()) {
			return readStream(new FileInputStream(file));
		}
		InputStream ins = MaterialsHandler.class.getResourceAsStream("/assets/"+Ressources.MODID+"/config/" + fileName);
		if(ins == null) {
		    throw new IOException("Could not find resource /assets/"+Ressources.MODID+"/config/" + fileName + " form classpath. ");
		}

	    String output = readStream(ins);
	    BufferedWriter writer = null;
	    try {
	    	writer = new BufferedWriter(new FileWriter(file, false));
	    	writer.write(output.toString());
	    } finally {
	    	IOUtils.closeQuietly(writer);
	    }
	    return output.toString();
	}
	
	private static String readStream(InputStream ins) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
		StringBuilder output = new StringBuilder();
		try {
			String line = reader.readLine();
		    while (line != null) {
		    	output.append(line);
		        output.append("\n");
		        line = reader.readLine();
		    }
	    } finally {
	    	IOUtils.closeQuietly(reader);
	    }
		return output.toString();
	}
}
