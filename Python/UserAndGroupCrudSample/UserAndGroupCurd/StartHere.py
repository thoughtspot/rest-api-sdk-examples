#! /usr/bin/env python3
# pylint: disable= no-name-in-module
import json

from restapisdk.models.tspublic_rest_v_2_user_create_request \
    import TspublicRestV2UserCreateRequest

from Python.UserAndGroupCrudSample.UserAndGroupCurd.ControllerBase \
    import ControllerBase
from Python.UserAndGroupCrudSample.UserAndGroupCurd.GroupMethods import GroupMethods
from Python.UserAndGroupCrudSample.UserAndGroupCurd.UserMethods import UserMethods


class StartHere:

    def __init__(self):
        # Provide the host and credentials for login
        self.v_BaseURL = "https://your-testcluster.thoughtspot.com"
        self.v_UserName = "testuser"
        self.v_Password = "testpassword"

    def userOperations(self):
        try:
            client = ControllerBase.getClient(
                self, self.v_BaseURL, self.v_UserName, self.v_Password)

            # List the active users in the cluster
            v_Name = UserMethods.searchActiveUsers(self, client)

            count = 0
            for name in v_Name:

                if count == 0:

                    print("List of users :: \n" + name)
                    count = count + 1
                else:
                    print(name)
            userCreateBody = []

            # create new users
            body = TspublicRestV2UserCreateRequest()
            body.name = 'sampleUser1'
            body.display_name = 'sample user1'
            body.mail = 'sampleuser1@thoughtspot.com'
            body.password = 'P2wd_4321'

            userCreateBody.append(body)

            body = TspublicRestV2UserCreateRequest()
            body.name = 'sampleUser2'
            body.display_name = 'sample user2'
            body.mail = 'sampleuser1@thoughtspot.com'
            body.password = 'P2wd_4321'

            userCreateBody.append(body)
            result = UserMethods.CreateNewUser(self, client, userCreateBody)
            for user in result:
                print("User details: " + user.name)

            # Create a group if it does not exist
            groupName = "Sample Group"
            result = GroupMethods.CreateNewGroup(self, client, groupName)
            print('group details::', result.name)

            # Add users to the group
            GroupMethods.AddSomeUserToGroup(
                self, client, userCreateBody, groupName)
            print('Users added to the group::' + groupName)

        except Exception as e:
            print(e)


if __name__ == '__main__':
    s = StartHere()
    s.userOperations()
