#! /usr/bin/env python3
# pylint: disable= no-name-in-module

from restapisdk.models.state_enum import StateEnum
from restapisdk.models.tspublic_rest_v_2_user_search_request \
    import TspublicRestV2UserSearchRequest

from UserAndGroupCrudSample.UserAndGroupCurd.ControllerBase \
    import ControllerBase


class UserMethods(ControllerBase):
    def searchActiveUsers(self, client):
        v_Name = []
        userController = client.user
        body = TspublicRestV2UserSearchRequest()
        body.state = StateEnum.ACTIVE

        result = userController.search_users(body)
        for i in result:
            v_Name.append(i.name)
        return v_Name

    def CreateNewUser(self, client, userCreateBody):
        userController = client.user
        result = []
        for body in userCreateBody:
            result.append(userController.create_user(body))

        return result
