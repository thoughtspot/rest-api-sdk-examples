package com.thoughtspot.ExportAndImportSample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import localhost.RESTAPISDKClient;
import localhost.controllers.MetadataController;
import localhost.exceptions.ApiException;
import localhost.models.ExportAssociatedEnum;
import localhost.models.ForceCreateEnum;
import localhost.models.ImportPolicyEnum;
import localhost.models.NameAndIdInput;
import localhost.models.TspublicRestV2MetadataHeaderSearchRequest;
import localhost.models.TspublicRestV2MetadataTmlExportRequest;
import localhost.models.TspublicRestV2MetadataTmlImportRequest;
import localhost.models.Type10Enum;

/**
 * Metadata CRUD operations.
 */
public class MetadataMethods extends ControllerBase {

    /**
     * Search and get list of objects based on type.
     *
     * @param client client.
     * @param name name of the object to search.
     * @return list of objects.
     * @throws IOException .
     * @throws ApiException .
     */
    public List<String> searchObject(RESTAPISDKClient client, String name)
            throws IOException, ApiException {
        MetadataController metadataController = client.getMetadataController();

        TspublicRestV2MetadataHeaderSearchRequest tspublicRestV2MetadataHeaderSearchRequest =
                new TspublicRestV2MetadataHeaderSearchRequest();
        //Add type of object
        tspublicRestV2MetadataHeaderSearchRequest.setType(Type10Enum.LIVEBOARD);

        //Include only id in the output
        tspublicRestV2MetadataHeaderSearchRequest.setOutputFields(Collections.singletonList("id"));

        //Set offset and batch size
        // CHECKSTYLE:OFF (0 and 5 are magic numbers)
        tspublicRestV2MetadataHeaderSearchRequest.setOffset(0);
        tspublicRestV2MetadataHeaderSearchRequest.setBatchSize(5);
        // CHECKSTYLE:ON

        //Add the created by name
        NameAndIdInput nameAndIdInput = new NameAndIdInput();
        nameAndIdInput.setName(name);
        tspublicRestV2MetadataHeaderSearchRequest.setCreatedBy(Collections.singletonList(
                nameAndIdInput));

        //Send search request based on the inputs above
        Object result =
                metadataController.searchObjectHeader(tspublicRestV2MetadataHeaderSearchRequest);

        //Parse the response to get id and name
        Gson gson = new Gson();
        JSONObject json = gson.fromJson(result.toString(), JSONObject.class);

        ArrayList<LinkedTreeMap> obj = (ArrayList) json.get("headers");
        List<String> objectIds = new ArrayList<>();
        for (LinkedTreeMap map : obj) {
            objectIds.add(map.get("id").toString());
        }

        return objectIds;
    }

    /**
     * Export object based on id.
     *
     * @param client client.
     * @param id tsObject ids.
     * @return exported object data.
     * @throws IOException .
     * @throws ApiException .
     */
    public Object exportObject(RESTAPISDKClient client, List<String> id)
            throws IOException, ApiException {

        MetadataController metadataController = client.getMetadataController();

        TspublicRestV2MetadataTmlExportRequest tspublicRestV2MetadataTmlExportRequest =
                new TspublicRestV2MetadataTmlExportRequest();
        tspublicRestV2MetadataTmlExportRequest.setId(id);
        tspublicRestV2MetadataTmlExportRequest.setExportAssociated(ExportAssociatedEnum.ENUM_TRUE);

        Object result = metadataController.exportObjectTML(tspublicRestV2MetadataTmlExportRequest);

        return result;
    }

    /**
     * Import the provided TMLs to ThoughtSpot.
     *
     * @param client client.
     * @param tmls Tml data.
     * @return import response data.
     * @throws IOException .
     * @throws ApiException .
     */
    public String importObject(RESTAPISDKClient client, List<String> tmls)
            throws IOException, ApiException {

        MetadataController metadataController = client.getMetadataController();

        TspublicRestV2MetadataTmlImportRequest tspublicRestV2MetadataTmlImportRequest =
                new TspublicRestV2MetadataTmlImportRequest();
        tspublicRestV2MetadataTmlImportRequest.setObjectTML(tmls);
        tspublicRestV2MetadataTmlImportRequest.setImportPolicy(ImportPolicyEnum.PARTIAL);
        tspublicRestV2MetadataTmlImportRequest.setForceCreate(ForceCreateEnum.ENUM_TRUE);

        Object result = metadataController.importObjectTML(tspublicRestV2MetadataTmlImportRequest);

        return result.toString();
    }
}
