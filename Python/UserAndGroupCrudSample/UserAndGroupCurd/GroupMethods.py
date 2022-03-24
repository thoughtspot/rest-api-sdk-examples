#! /usr/bin/env python3
# pylint: disable= no-name-in-module

from restapisdk.models.privilege_enum import PrivilegeEnum
from restapisdk.models.tspublic_rest_v_2_group_adduser_request \
    import TspublicRestV2GroupAdduserRequest
from restapisdk.models.tspublic_rest_v_2_group_create_request \
    import TspublicRestV2GroupCreateRequest
from restapisdk.models.type_5_enum import Type5Enum
from restapisdk.models.user_name_and_id_input import UserNameAndIDInput
from restapisdk.models.visibility_3_enum import Visibility3Enum

from UserAndGroupCrudSample.UserAndGroupCurd.ControllerBase \
    import ControllerBase


class GroupMethods(ControllerBase):
    def CreateNewGroup(self, client, groupName):

        groupController = client.group
        body = TspublicRestV2GroupCreateRequest()
        body.name = groupName
        body.display_name = 'sample group1'
        body.visibility = Visibility3Enum.DEFAULT
        body.description = 'sample group1'
        body.privileges = \
            [PrivilegeEnum.ADMINISTRATION, PrivilegeEnum.DEVELOPER]

        body.mtype = Type5Enum.LOCAL_GROUP
        count = 0
        result = ''
        try:
            result = groupController.get_group(groupName)
            count = 1
        except:
            print('')
        finally:
            if count == 0:
                result = groupController.create_group(body)

        return result

    def AddSomeUserToGroup(self, client, userCreateBody, groupName):
        groupController = client.group
        groupBody = TspublicRestV2GroupAdduserRequest()
        groupBody.name = groupName
        groupBody.users = []

        groupBody.users.append(UserNameAndIDInput())
        for body in userCreateBody:
            groupBody.users[0].name = body.name
            result = groupController.add_users_to_group(groupBody)
        return result
