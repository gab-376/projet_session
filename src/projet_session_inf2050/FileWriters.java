/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projet_session_inf2050;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author gabbm
 */
public class FileWriters {
    
    public static void saveStringIntoFile(String filePath, String contentToSave)
            throws FileNotFoundException, IOException {
        File f = new File(filePath);
        FileUtils.writeStringToFile(f, contentToSave, "UTF-8");
    }
}
