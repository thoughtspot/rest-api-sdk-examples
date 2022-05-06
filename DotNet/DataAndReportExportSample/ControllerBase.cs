using System;
using System.IO;
using System.Collections.Generic;
using System.Globalization;
using System.Threading.Tasks;

using RESTAPISDK.Standard;
using RESTAPISDK.Standard.Models;
using RESTAPISDK.Standard.Controllers;
using RESTAPISDK.Standard.Utilities;
using RESTAPISDK.Standard.Exceptions;

namespace DataAndReportExport
{
    class ControllerBase
    {

        public async Task<string> GetToken(string p_BaseURL, string p_UserName, string p_Password)
        {
            RESTAPISDKClient client = new RESTAPISDKClient.Builder()
                .AcceptLanguage("application/json")
                .ContentType("application/json")
                .BaseUrl(p_BaseURL)
                .HttpClientConfig(config => config.NumberOfRetries(0))
                .Build();
            SessionController sessionController = client.SessionController;

            var body = new TspublicRestV2SessionGettokenRequest();
            body.UserName = p_UserName;
            body.Password = p_Password;
            body.AccessLevel = GetTokenAccessLevelEnum.FULL;
            body.TokenExpiryDuration = "6000";

            SessionLoginResponse result = await sessionController.GetTokenAsync(body);

            return result.Token;
        }


        public RESTAPISDKClient GetClient(string p_BaseURL, string p_UserName, string p_Password)
        {
            var v_AccessToken = GetToken(p_BaseURL, p_UserName, p_Password).Result;

            RESTAPISDKClient client = new RESTAPISDK.Standard.RESTAPISDKClient.Builder()
            .AccessToken(v_AccessToken)
            .AcceptLanguage("application/json")
            .ContentType("application/json")
            .Environment(RESTAPISDK.Standard.Environment.Production)
            .BaseUrl(p_BaseURL)
            .HttpClientConfig(config => config.NumberOfRetries(0))
            .Build();

            return client;
        }
    }
}
