#! /usr/bin/env python3
# pylint: disable= no-name-in-module

import json

from restapisdk.models.export_associated_enum import ExportAssociatedEnum
from restapisdk.models.force_create_enum import ForceCreateEnum
from restapisdk.models.import_policy_enum import ImportPolicyEnum
from restapisdk.models.name_and_id_input import NameAndIdInput
from restapisdk.models.tspublic_rest_v_2_metadata_header_search_request \
    import TspublicRestV2MetadataHeaderSearchRequest
from restapisdk.models.tspublic_rest_v_2_metadata_tml_export_request \
    import TspublicRestV2MetadataTmlExportRequest
from restapisdk.models.tspublic_rest_v_2_metadata_tml_import_request \
    import TspublicRestV2MetadataTmlImportRequest
from restapisdk.models.type_10_enum import Type10Enum

from ExportAndImportSample.ExportAndImport.ControllerBase \
    import ControllerBase


class MetadataMethods(ControllerBase):

    def SearchObject(self, client, p_Name):
        metadataController = client.metadata
        body = TspublicRestV2MetadataHeaderSearchRequest()
        body.output_fields = ['id']
        body.offset = 0
        body.created_by = []
        body.created_by.append(NameAndIdInput())
        body.created_by[0].name = p_Name
        body.mtype = Type10Enum.LIVEBOARD
        searchIds = []
        result = metadataController.search_object_header(body)
        data_dict = json.loads(result)
        jsonArray = data_dict['headers']
        for data in jsonArray:
            searchIds.append(data['id'])
        return searchIds

    def ExportObject(self, client, searchIds):
        metadataController = client.metadata

        body = TspublicRestV2MetadataTmlExportRequest()
        body.id = searchIds
        body.export_associated = ExportAssociatedEnum.TRUE

        result = metadataController.export_object_tml(body)
        return result

    def ImportObject(self, client, p_Tmls):

        metadataController = client.metadata
        body = TspublicRestV2MetadataTmlImportRequest()
        body.import_policy = ImportPolicyEnum.PARTIAL
        body.object_tml = p_Tmls
        body.force_create = ForceCreateEnum.TRUE

        result = metadataController.import_object_tml(body)
        return result
