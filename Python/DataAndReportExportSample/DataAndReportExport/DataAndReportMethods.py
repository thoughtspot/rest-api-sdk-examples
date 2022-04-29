#! /usr/bin/env python3
# pylint: disable= no-name-in-module
from restapisdk.models.liveboard_data_format_type_enum \
    import LiveboardDataFormatTypeEnum
from restapisdk.models.tspublic_rest_v_2_data_liveboard_request \
    import TspublicRestV2DataLiveboardRequest

from Python.DataAndReportExportSample.DataAndReportExport.ControllerBase\
    import ControllerBase


class DataAndReportExport(ControllerBase):
    def GetLiveboardData(self, client, p_Id, viz_Id):
        dataController = client.data
        body = TspublicRestV2DataLiveboardRequest()
        body.id = p_Id
        body.format_type = LiveboardDataFormatTypeEnum.FULL
        if viz_Id is not None:
            body.viz_id = [viz_Id]

        result = dataController.liveboard_data(body)
        return result
