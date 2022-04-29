#! /usr/bin/env python3
# pylint: disable= no-name-in-module
from restapisdk.configuration import Environment
from restapisdk.models.tspublic_rest_v_2_session_gettoken_request \
    import TspublicRestV2SessionGettokenRequest
from restapisdk.restapisdk_client import RestapisdkClient


class ControllerBase:
    def getToken(self, baseUrl, userName, password):
        client = RestapisdkClient(content_type='application/json',
                                  accept_language='application/json',
                                  access_token='',
                                  environment=Environment.PRODUCTION,
                                  base_url=baseUrl,
                                  skip_ssl_cert_verification=True, )
        body = TspublicRestV2SessionGettokenRequest()
        body.user_name = userName
        body.password = password
        session_controller = client.session
        response = session_controller.get_token(body)
        return response.token

    def getClient(self, baseUrl, userName, password):
        client = RestapisdkClient(
            content_type='application/json',
            accept_language='application/json',
            access_token=ControllerBase.getToken(
                self, baseUrl, userName, password),
            environment=Environment.PRODUCTION,
            base_url=baseUrl,
            skip_ssl_cert_verification=True)

        return client
