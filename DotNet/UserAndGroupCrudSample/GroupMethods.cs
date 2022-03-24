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

namespace UserGroupCrud
{
    class GroupMethods : ControllerBase
    {

        public async Task<GroupResponse> CreateNewGroup(RESTAPISDKClient p_client, string p_name)
        {
            
            GroupController groupController = p_client.GroupController;

            string v_name = p_name.Replace(" ", "");

            int v_status = 0;

            GroupResponse result = new GroupResponse();

            try
            {
                result = await groupController.GetGroupAsync(v_name, null);
                v_status = 1;
            }
            catch (Exception) { }
            finally
            {
                if (v_status == 0)
                {
                    var body = new TspublicRestV2GroupCreateRequest();
                    body.Name = v_name;
                    body.DisplayName = p_name;
                    body.Visibility = Visibility3Enum.DEFAULT;
                    body.Privileges = new List<PrivilegeEnum>();
                    body.Privileges.Add(PrivilegeEnum.DEVELOPER);
                    body.Privileges.Add(PrivilegeEnum.DATAMANAGEMENT);
                    body.Privileges.Add(PrivilegeEnum.EXPERIMENTALFEATUREPRIVILEGE);
                    body.Type = Type5Enum.LOCALGROUP;

                    result = await groupController.CreateGroupAsync(body);
                }
            };

            return result;
        }

        public async Task<bool> AddSomeUserToGroup(RESTAPISDKClient p_client, List<TspublicRestV2UserCreateRequest> p_username, string p_groupname)
        {

            GroupController groupController = p_client.GroupController;

            string v_groupname = p_groupname.Replace(" ", "");

            var body = new TspublicRestV2GroupAdduserRequest();
            body.Name = v_groupname;
            body.Users = new List<UserNameAndIDInput>();

            foreach(TspublicRestV2UserCreateRequest item in p_username)
            {
                var bodyUsers = new UserNameAndIDInput();
                bodyUsers.Name = item.Name;
                body.Users.Add(bodyUsers);
            }
            
            bool result = await groupController.AddUsersToGroupAsync(body);

            return result;
        }


    }
}
