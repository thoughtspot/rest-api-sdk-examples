// This sample code does below operations
//  1. Exports the data for a Liveboard in JSON format and saves to a file

using System;
using System.IO;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;

using RESTAPISDK.Standard;


namespace DataAndReportExport
{
    class StartHere
    {
        //Provide the host and credentials for login
        public const string v_BaseURL = "https://your-testcluster.thoughtspot.com";
        public const string v_UserName = "testuser";
        public const string v_Password = "testpassword";

        static void ResultToFile(object result, string filePath, string p_id)
        {
            //Write the object to file

            FileStream stream = new FileStream($"{filePath}/{p_id}.dat", FileMode.Create);
            using (StreamWriter writer = new StreamWriter(stream))
            {
                writer.Write(result.ToString());
            }
        }

        static void Main(string[] args)
        {
            try
            {
                var controller = new ControllerBase();
                RESTAPISDKClient client = controller.GetClient(v_BaseURL, v_UserName, v_Password);

                var DataAndReportMethods = new DataAndReportMethods();

                //string objectId = "bea79810-145f-4ad0-a02c-4177a6e7d861";
                string objectId = "327f4d60-c502-43b0-b1d4-c73df5031a2e";

                //set the value as null to not consider vizid
                //string vizId = "4394f4b7-7bce-4031-ba27-cea587f295ec";
                string vizId = "8fbf93e6-54ba-4a20-b2bb-4afe8dca5321";

                //Export TML for the list of object ids received from above call
                object result = DataAndReportMethods.GetLiveboardData(client, objectId, vizId).Result;

                //Write the TMLs into file on disk
                string filePath = $@"/tmp/exporttest";
                ResultToFile(result, filePath, objectId);

                Console.Write("Data for object with id {0} and vizId {3} has been written to file {1}/{2}.dat", objectId, filePath, objectId, vizId);

            }
            catch (Exception e)
            {
                Console.Write("Error: "+e.Message);
            }

        }
    }
}