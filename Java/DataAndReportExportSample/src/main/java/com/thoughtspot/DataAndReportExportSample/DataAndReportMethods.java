package com.thoughtspot.DataAndReportExportSample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import localhost.RESTAPISDKClient;
import localhost.controllers.DataController;
import localhost.controllers.ReportController;
import localhost.exceptions.ApiException;
import localhost.models.AnswerReportTypeEnum;
import localhost.models.LiveboardDataFormatTypeEnum;
import localhost.models.TspublicRestV2DataLiveboardRequest;
import localhost.models.TspublicRestV2ReportAnswerRequest;

/**
 * Data and Report CRUD operations.
 */
public class DataAndReportMethods extends ControllerBase {
    /**
     * Get liveboard data.
     *
     * @param client client.
     * @param id id of the tsObject.
     * @param vizId visualization id.
     * @return Liveboard object.
     * @throws IOException .
     * @throws ApiException .
     */
    public Object getLiveboardData(RESTAPISDKClient client, String id, String vizId)
            throws IOException, ApiException {
        TspublicRestV2DataLiveboardRequest tspublicRestV2DataLiveboardRequest =
                new TspublicRestV2DataLiveboardRequest();
        tspublicRestV2DataLiveboardRequest.setId(id);
        tspublicRestV2DataLiveboardRequest.setFormatType(LiveboardDataFormatTypeEnum.FULL);

        if (vizId != null) {
           List<String> list = new ArrayList<>();
           list.add(vizId);
           tspublicRestV2DataLiveboardRequest.setVizId(list);
        }

        DataController dataController = client.getDataController();
        Object result = dataController.liveboardData(tspublicRestV2DataLiveboardRequest);

        return result;
    }

    public Object testAnswerReport(RESTAPISDKClient client, String objectId) throws IOException, ApiException {
        ReportController reportController = client.getReportController();
        // Test Answer report with PDF data.
        // Parameters for the API call
        TspublicRestV2ReportAnswerRequest tspublicRestV2ReportAnswerRequest
                = new TspublicRestV2ReportAnswerRequest().toBuilder()
                .id(objectId).type(AnswerReportTypeEnum.PDF).build();

        // Set callback and perform API call
        Object result = reportController.answerReport(
                tspublicRestV2ReportAnswerRequest);

        return result;
    }

    /**
     * Store result in a file.
     *
     * @param result   data that need to put into file.
     * @param filePath file path.
     * @param id       id of the object.
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

            byte[] bytesArray = result.toString().getBytes();

            fos.write(bytesArray);
            fos.flush();
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
