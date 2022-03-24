//This sample code does below operations
//  1. Lists all the liveboards for a user
//  2. Export the liveboards and associated objects as tml for the once listed by above call
//  3. Write the exported tml into separate file for each object
//  4. Import the tmls present in specified folder

using System;
using System.IO;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;

using RESTAPISDK.Standard;


namespace ExportAndImport
{
    class StartHere
    {
        public const string v_BaseURL = "https://your-testcluster.thoughtspot.com";
        public const string v_UserName = "testuser";
        public const string v_Password = "testpassword";

        static void TMLToFile(object result, string filePath)
        {
            //Read the filename from info.filename and edoc fields from the response
            var resultJObject = JObject.Parse(result.ToString());

            List<string> v_Edoc = new List<string>();

            if (resultJObject != null)
            {
                var objectArray = resultJObject["object"];

                foreach (object item in objectArray)
                {
                    var objectJObject = JObject.Parse(item.ToString());
                    var objectInfo = objectJObject["info"];

                    string filename = "errorfile";

                    if (objectInfo != null)
                    {
                        var infoJObject = JObject.Parse(objectInfo.ToString());
                        var objectfilename = infoJObject["filename"];

                        if (objectfilename != null)
                        {
                            filename = objectfilename.ToString();
                        }

                        var objectEdoc = objectJObject["edoc"];
                        if (objectEdoc != null)
                        {
                            //Write the edoc to file with name as in the info.filename
                            FileStream stream = new FileStream($"{filePath}/{filename}", FileMode.Create);
                            using (StreamWriter writer = new StreamWriter(stream))
                            {
                                writer.Write(objectEdoc.ToString());
                            }
                        }
                    }
                }
            }
        }

        static List<string> TMLFromFile(string filePath)
        {
            DirectoryInfo d = new DirectoryInfo(filePath);
            List<string> l_tmls = new List<string>();

            foreach (string tml in Directory.GetFiles(filePath, "*.tml"))
            {
                using (StreamReader sr = new StreamReader(tml))
                {
                    l_tmls.Add(sr.ReadToEnd());
                }
            }

            return l_tmls;
        }

        static void Main(string[] args)
        {
            try
            {
                var controller = new ControllerBase();
                RESTAPISDKClient client = controller.GetClient(v_BaseURL, v_UserName, v_Password);

                var MetadataController = new MetadataMethods();

                string userFilter = "tsadmin";

                //Search Liveboards created by a particular user and get the list of ids
                List<string> ids = MetadataController.SearchObject(client, userFilter).Result;

                //Export TML for the list of object ids received from above call
                object result = MetadataController.ExportObject(client, ids).Result;

                //Write the TMLs into file on disk
                string filePath = $@"/tmp/test";
                TMLToFile(result, filePath);

                //Read the TMLs from a folder
                List<string> tmls = TMLFromFile(filePath);

                //Import TML received from above call
                string resp = MetadataController.ImportObject(client, tmls).Result;

                Console.Write("Response: " + resp);

            }
            catch (Exception e)
            {
                Console.Write("Error: "+e.Message);
            }

        }
    }
}
