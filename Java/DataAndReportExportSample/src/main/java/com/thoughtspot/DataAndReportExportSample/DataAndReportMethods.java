package com.thoughtspot.DataAndReportExportSample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import localhost.RESTAPISDKClient;
import localhost.controllers.DataController;
import localhost.exceptions.ApiException;
import localhost.models.FormatType1Enum;
import localhost.models.TspublicRestV2DataLiveboardRequest;

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
        tspublicRestV2DataLiveboardRequest.setFormatType(FormatType1Enum.FULL);

        if (vizId != null) {
           List<String> list = new ArrayList<>();
           list.add(vizId);
           tspublicRestV2DataLiveboardRequest.setVizId(list);
        }

        DataController dataController = client.getDataController();
        Object result = dataController.liveboardData(tspublicRestV2DataLiveboardRequest);

        return result;
    }
}
