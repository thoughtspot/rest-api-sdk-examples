// This sample projects does below operations
// 1. List all the active users in the cluster
// 2. Create 2 new users
// 3. Create a new group
// 4. Add the new users created to the new group

using System;
using System.IO;
using System.Collections.Generic;
using System.Globalization;
using System.Threading.Tasks;
using Newtonsoft.Json.Linq;

using RESTAPISDK.Standard;
using RESTAPISDK.Standard.Models;
using RESTAPISDK.Standard.Controllers;
using RESTAPISDK.Standard.Utilities;
using RESTAPISDK.Standard.Exceptions;

namespace UserGroupCrud
{
    class StartHere
    {

        //Provide the host and credentials for login
        public const string v_BaseURL = "https://your-testcluster.thoughtspot.com";
        public const string v_UserName = "testuser";
        public const string v_Password = "testpassword";


        static void Main(string[] args)
        {

            try
            {
                var controller = new ControllerBase();
                RESTAPISDKClient client = controller.GetClient(v_BaseURL, v_UserName, v_Password);

                //List the active users in the cluster

                var UserMethods = new UserMethods();
                List<string> v_Name = UserMethods.SearchActiveUsers(client).Result;
                Console.Write("List of users: \n ");


                foreach (var Name in v_Name)
                {
                    Console.Write("{0} \n\n", Name);
                }

                //Create new users

                List<TspublicRestV2UserCreateRequest> userCreateBody = new List<TspublicRestV2UserCreateRequest>();

                var body = new TspublicRestV2UserCreateRequest();

                body.Name = "SampleUser1";
                body.DisplayName = "Sample User1";
                body.Password = "Welcome_1234";
                //body.Mail = "sampleuser1@thoughtspot.com";

                userCreateBody.Add(body);

                body = new TspublicRestV2UserCreateRequest();

                body.Name = "SampleUser2";
                body.DisplayName = "Sample User2";
                body.Password = "Welcome_1234";
                //body.Mail = "sampleuser2@thoughtspot.com";

                userCreateBody.Add(body);

                List<UserResponse> userResult = UserMethods.CreateNewUser(client, userCreateBody).Result;

                foreach (UserResponse item in userResult)
                {
                    Console.Write("User detail: {0} \n\n", item.ToString());
                }

                //Create a group if it does not exist
                var GroupMethods = new GroupMethods();

                string groupName = "Sample Group";

                GroupResponse groupResult = GroupMethods.CreateNewGroup(client, groupName).Result;

                Console.Write("Group detail: {0} \n\n", groupResult.ToString());


                //Add users to the group
                bool result = GroupMethods.AddSomeUserToGroup(client, userCreateBody, groupName).Result;

                Console.Write("Users added to the group {0}", groupName);
            }

            catch (Exception e)
            {
                Console.Write("Error: " + e.ToString());
            }
        }
    }
}
