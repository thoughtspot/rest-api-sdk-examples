/*
 * RESTAPISDKLib
 *
 * This file was automatically generated by APIMATIC v3.0 ( https://www.apimatic.io ).
 */
package com.thoughtspot.ExportAndImportSample;

import java.io.IOException;

import localhost.Environment;
import localhost.RESTAPISDKClient;
import localhost.controllers.SessionController;
import localhost.exceptions.ApiException;
import localhost.models.AccessLevelEnum;
import localhost.models.SessionLoginResponse;
import localhost.models.TspublicRestV2SessionGettokenRequest;

/**
 * To get token based on session.
 */
public class ControllerBase {

    /**
     * To fetch token for the session.
     *
     * @param baseURL IP of the cluster.
     * @param userName name of the user.
     * @param password password of the user.
     * @return token.
     * @throws IOException .
     * @throws ApiException .
     */
    public String getToken(String baseURL, String userName, String password)
            throws IOException, ApiException {
        RESTAPISDKClient client = new RESTAPISDKClient.Builder()
                .baseUrl(baseURL)
                .httpClientConfig(configBuilder -> configBuilder.numberOfRetries(0))
                .build();
        SessionController sessionController = client.getSessionController();

        TspublicRestV2SessionGettokenRequest tspublicRestV2SessionGettokenRequest =
                new TspublicRestV2SessionGettokenRequest();
        tspublicRestV2SessionGettokenRequest.setUserName(userName);
        tspublicRestV2SessionGettokenRequest.setPassword(password);
        tspublicRestV2SessionGettokenRequest.setAccessLevel(AccessLevelEnum.FULL);
        tspublicRestV2SessionGettokenRequest.setTokenExpiryDuration("6000");

        SessionLoginResponse result =
                sessionController.getToken(tspublicRestV2SessionGettokenRequest);

        return result.getToken();
    }

    /**
     * Get the client.
     *
     * @param baseURL baseURL
     * @param userName userName
     * @param password password
     * @return RESTAPISDKClient
     * @throws IOException .
     * @throws ApiException .
     */
    public RESTAPISDKClient getClient(String baseURL, String userName, String password)
            throws IOException, ApiException {
        String accessToken = getToken(baseURL, userName, password);

        RESTAPISDKClient client = new RESTAPISDKClient.Builder()
                .accessToken(accessToken)
                .environment(Environment.PRODUCTION)
                .baseUrl(baseURL)
                .httpClientConfig(configBuilder -> configBuilder.numberOfRetries(0))
                .build();

        return client;
    }
}
