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


namespace ExportAndImport
{
    class MetadataMethods : ControllerBase
    {

        public async Task<List<string>> SearchObject(RESTAPISDKClient client, string p_name)
        {
            MetadataController metadataController = client.MetadataController;

            var body = new TspublicRestV2MetadataHeaderSearchRequest();
            //Add type of object
            body.Type = SearchObjectHeaderTypeEnum.LIVEBOARD;

            //Include only id in the output
            body.OutputFields = new List<string>();
            body.OutputFields.Add("id");

            //Set offset and batch size
            body.Offset = 0;
            body.BatchSize = 5;

            //Add the created by name
            body.Author = new List<NameAndIdInput>();
            var bodyCreatedBy = new NameAndIdInput();
            bodyCreatedBy.Name = p_name;
            body.Author.Add(bodyCreatedBy);

            //Send search request based on the inputs above
            object result = await metadataController.SearchObjectHeaderAsync(body);

            //Parse the response to get id and name
            var resultJObject = JObject.Parse(result.ToString());
            List<string> l_id = new List<string>();

            if (resultJObject != null)
            {
                var objectArray = resultJObject["headers"];

                foreach (object item in objectArray)
                {
                    var idJObject = JObject.Parse(item.ToString());
                    var objectId = idJObject["id"];
                    l_id.Add(objectId.ToString());
                }
            }

            return l_id;
        }

        public async Task<object> ExportObject(RESTAPISDKClient p_client, List<string> p_id)
        {

            MetadataController metadataController = p_client.MetadataController;

            var body = new TspublicRestV2MetadataTmlExportRequest();
            body.Id = p_id;
            body.ExportAssociated = ExportObjectTMLExportAssociatedEnum.True;

            object result = await metadataController.ExportObjectTMLAsync(body);

            return result;
        }

        public async Task<string> ImportObject(RESTAPISDKClient p_client, List<string> p_tmls)
        {

            MetadataController metadataController = p_client.MetadataController;

            var body = new TspublicRestV2MetadataTmlImportRequest();
            body.ObjectTML = p_tmls;
            body.ImportPolicy = ImportObjectTMLImportPolicyEnum.VALIDATEONLY;

            object result = await metadataController.ImportObjectTMLAsync(body);

            return result.ToString();
        }
    }
}
