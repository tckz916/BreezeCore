package com.github.tckz916.breezecore.manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by tckz916 on 2015/10/07.
 */
public class FileManager {

    public File loadFile(String foldername, String filename) {
        File folder = new File(foldername);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return file;
    }


    public void writeFile(File file, String write) {
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(write);
            bufferedWriter.newLine();
            fileWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

}
