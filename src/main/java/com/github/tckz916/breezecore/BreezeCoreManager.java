package com.github.tckz916.breezecore;

import com.github.tckz916.breezecore.manager.FileManager;
import com.github.tckz916.breezecore.manager.JsonManager;
import com.github.tckz916.breezecore.manager.XmlManager;
import com.github.tckz916.breezecore.message.MessageFormat;

/**
 * Created by tckz916 on 2015/10/13.
 */
public class BreezeCoreManager {

    private static BreezeCoreManager instance = null;

    private MessageFormat messageFormat = null;
    private JsonManager jsonManager = null;
    private FileManager fileManager = null;
    private XmlManager xmlManager = null;

    public BreezeCoreManager() {
        instance = this;

        messageFormat = new MessageFormat();
        jsonManager = new JsonManager();
        fileManager = new FileManager();
        xmlManager = new XmlManager();
    }

    public static BreezeCoreManager getInstance() {
        return instance;
    }

    public MessageFormat getMessageFormat() {
        return messageFormat;
    }

    public JsonManager getJsonManager() {
        return jsonManager;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public XmlManager getXmlManager() {
        return xmlManager;
    }

}
