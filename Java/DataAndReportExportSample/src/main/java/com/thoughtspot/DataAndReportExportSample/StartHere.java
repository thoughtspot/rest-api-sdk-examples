package com.thoughtspot.DataAndReportExportSample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import localhost.RESTAPISDKClient;

/**
 * Test Data and Report export sample.
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
     * Constructor.
     */
    private StartHere() {
    }

    /**
     * Store result in a file.
     *
     * @param result data that need to put into file.
     * @param filePath file path.
     * @param id id of the object.
     */
    public static void resultToFile(Object result, String filePath, String id) {
        FileOutputStream fos = null;
        try {
            File file = new File(filePath + "/" + id + ".dat");
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            //Write the object to file
            fos = new FileOutputStream(file);
            System.out.println(filePath + "/" + id + ".dat");

            byte[] bytesArray = result.toString().getBytes();

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

    /**
     * Data and Report export execution starts here.
     *
     * @param args args.
     */
    public static void main(String[] args) {
        try {
            ControllerBase controllerBase = new ControllerBase();
            RESTAPISDKClient client = controllerBase.getClient(BASEURL, USERNAME, PASSWORD);

            DataAndReportMethods dataAndReportMethods = new DataAndReportMethods();

            String objectId = "33248a57-cc70-4e39-9199-fb5092283381";
            String vizId = "730496d6-6903-4601-937e-2c691821af3c";

            //Export TML for the list of object ids received from above call
            Object result = dataAndReportMethods.getLiveboardData(client, objectId, vizId);

            //Write the TMLs into file on disk
            String filePath = "/tmp/exporttest";
            resultToFile(result, filePath, objectId);

            System.out.println(String.format("Data for object with id %s and vizId %s "
                    + "has been written to file %s/%s.dat", objectId, vizId, filePath, objectId));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
