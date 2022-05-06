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

        public async Task<object> SearchActiveUsers(RESTAPISDKClient p_client)
        {
            UserController userController = p_client.UserController;

            var body = new TspublicRestV2UserSearchRequest();
            body.State = SearchUsersStateEnum.ACTIVE;
            body.OutputFields = new List<string>();
            body.OutputFields.Add("name");

            object result = await userController.SearchUsersAsync(body);
            
            return result;
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
