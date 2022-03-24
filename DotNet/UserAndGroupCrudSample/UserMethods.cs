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
    class UserMethods : ControllerBase
    {

        public async Task<List<string>> SearchActiveUsers(RESTAPISDKClient p_client)
        {
            UserController userController = p_client.UserController;

            var body = new TspublicRestV2UserSearchRequest();
            body.State = StateEnum.ACTIVE;

            List<UserResponse> result = await userController.SearchUsersAsync(body);

            List<string> v_Name = new List<string>();

            for (var i = 0; i < result.Count; i++)
            {
                v_Name.Add(result[i].Name);
            }
            return v_Name;
        }

        public async Task<List<UserResponse>> CreateNewUser(RESTAPISDKClient p_client, List<TspublicRestV2UserCreateRequest> p_users)
        {

            UserController userController = p_client.UserController;

            List<UserResponse> result = new List<UserResponse>();

            foreach (TspublicRestV2UserCreateRequest body in p_users)
            {
                result.Add(await userController.CreateUserAsync(body));
            }

            return result;
        }
    }
}
