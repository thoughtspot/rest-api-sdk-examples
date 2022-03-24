package com.thoughtspot.ExportAndImportSample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import localhost.RESTAPISDKClient;

/**
 * Test Export and Import sample.
 */
public final class StartHere {

    //Provide the host and credentials for login
    /**
     * Base URL to be connected.
     */
    private static final String BASEURL = "https://your-testcluster.thoughtspot.com";
    /**
     * Username of the user account.
     */
    private static final String USERNAME = "testuser";
    /**
     * Password of the user.
     */
    private static final String PASSWORD = "testpassword";


    /**
     * Private Constructor.
     */
    private StartHere() {
    }

    /**
     * Export TML to a file.
     *
     * @param result tml result.
     * @param filePath file path to export.
     */
    public static void exportTMLToFile(Object result, String filePath) {
        //Read the filename from info.filename and edoc fields from the response
        Gson gson = new Gson();
        JSONObject jsonObject = gson.fromJson(result.toString(), JSONObject.class);
        ArrayList<LinkedTreeMap> list = (ArrayList) jsonObject.get("object");

        for (LinkedTreeMap map : list) {
            LinkedTreeMap info = (LinkedTreeMap) map.get("info");
            String fileName = "";
            if (info.get("filename") != null) {
                fileName = info.get("filename").toString();
            } else {
                fileName = info.get("name").toString();
            }

            String edoc = "";
            if (map.get("edoc") != null) {
                edoc = (String) map.get("edoc");
            }

            FileOutputStream fos = null;
            try {
                File file = new File(filePath + "/" + fileName);
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }
                //Write the object to file
                fos = new FileOutputStream(file);
                System.out.println(filePath + "/" + fileName);

                byte[] bytesArray = edoc.getBytes();

                fos.write(bytesArray);
                fos.flush();
                System.out.println("File Written Successfully");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException ioe) {
                    System.out.println("Error in closing the Stream");
                }
            }
        }
    }

    /**
     * Get the TMLs from the file.
     *
     * @param filePath filePath.
     * @return List of TMLs which are read from file.
     * @throws IOException .
     */
    public static List<String> importTMLFromFile(String filePath) throws IOException {
        File dir = new File(filePath);
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".tml");
            }
        });

        List<String> tmlList = new ArrayList<>();

        for (File f : files) {
            String content = new String(Files.readAllBytes(f.toPath()));
            tmlList.add(content);
        }
        return tmlList;
    }

    /**
     * Export and Import execution starts here.
     *
     * @param args args.
     */
    public static void main(String[] args) {
        try {
            ControllerBase controller = new ControllerBase();
            RESTAPISDKClient client = controller.getClient(BASEURL, USERNAME, PASSWORD);

            MetadataMethods metadataController = new MetadataMethods();

            String userFilter = "tsadmin";

            //Search Liveboards created by a particular user and get the list of ids
            List<String> ids = metadataController.searchObject(client, userFilter);

            //Export TML for the list of object ids received from above call
            Object result = metadataController.exportObject(client, ids);

            //Write the TMLs into file on disk
            String filePath = "/tmp/test";
            exportTMLToFile(result, filePath);

            //Read the TMLs from a folder
            List<String> tmls = importTMLFromFile(filePath);

            //Import TML received from above call
            String resp = metadataController.importObject(client, tmls);

            System.out.println("Response: " + resp);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
