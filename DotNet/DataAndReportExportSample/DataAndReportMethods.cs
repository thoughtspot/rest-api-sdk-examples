using System;
using System.IO;
using System.Collections.Generic;
using System.Globalization;
using System.Threading.Tasks;
using System.Collections;
using System.Reflection;
using Newtonsoft.Json.Linq;
using System.Linq;

using RESTAPISDK.Standard;
using RESTAPISDK.Standard.Models;
using RESTAPISDK.Standard.Controllers;
using RESTAPISDK.Standard.Utilities;
using RESTAPISDK.Standard.Exceptions;


namespace DataAndReportExport
{
    class DataAndReportMethods : ControllerBase
    {
        public async Task<object> GetLiveboardData(RESTAPISDKClient p_client, string p_id, string p_vizId)
        {
            var body = new TspublicRestV2DataLiveboardRequest();
            body.Id = p_id;
            body.FormatType = FormatType1Enum.FULL;

            if (p_vizId != null)
            {
                body.VizId = new List<string>();
                body.VizId.Add(p_vizId.ToString());
            }

            DataController dataController = p_client.DataController;
            object result = await dataController.LiveboardDataAsync(body);

            return result;
        }
    }
}
