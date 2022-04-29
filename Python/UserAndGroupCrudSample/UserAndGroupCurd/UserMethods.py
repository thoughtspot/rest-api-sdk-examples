#! /usr/bin/env python3
# pylint: disable= no-name-in-module
import json

from restapisdk.models.search_users_state_enum import SearchUsersStateEnum
from restapisdk.models.tspublic_rest_v_2_user_search_request \
    import TspublicRestV2UserSearchRequest

from Python.UserAndGroupCrudSample.UserAndGroupCurd.ControllerBase\
    import ControllerBase


class UserMethods(ControllerBase):
    def searchActiveUsers(self, client):
        v_Name = []
        userController = client.user
        body = TspublicRestV2UserSearchRequest()
        body.state = SearchUsersStateEnum.ACTIVE

        result = userController.search_users(body)
        data_dict = json.loads(result)
        for data in data_dict:
            v_Name.append(data['name'])
        return v_Name

    def CreateNewUser(self, client, userCreateBody):
        userController = client.user
        result = []
        for body in userCreateBody:
            result.append(userController.create_user(body))

        return result
