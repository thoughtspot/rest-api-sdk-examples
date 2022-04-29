#! /usr/bin/env python3
# pylint: disable= no-name-in-module

import json

from restapisdk.models.export_object_tml_export_associated_enum import ExportObjectTMLExportAssociatedEnum
from restapisdk.models.import_object_tml_import_policy_enum import ImportObjectTMLImportPolicyEnum
from restapisdk.models.name_and_id_input import NameAndIdInput
from restapisdk.models.search_object_header_type_enum import SearchObjectHeaderTypeEnum
from restapisdk.models.tspublic_rest_v_2_metadata_header_search_request \
    import TspublicRestV2MetadataHeaderSearchRequest
from restapisdk.models.tspublic_rest_v_2_metadata_tml_export_request \
    import TspublicRestV2MetadataTmlExportRequest
from restapisdk.models.tspublic_rest_v_2_metadata_tml_import_request \
    import TspublicRestV2MetadataTmlImportRequest

from Python.ExportAndImportSample.ExportAndImport.ControllerBase \
    import ControllerBase


class MetadataMethods(ControllerBase):

    def SearchObject(self, client, p_Name):
        metadataController = client.metadata
        body = TspublicRestV2MetadataHeaderSearchRequest()
        body.output_fields = ['id']
        body.offset = 3
        body.created_by = []
        body.created_by.append(NameAndIdInput())
        body.created_by[0].name = p_Name
        body.mtype = SearchObjectHeaderTypeEnum.LIVEBOARD
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
        body.export_associated = ExportObjectTMLExportAssociatedEnum.TRUE

        result = metadataController.export_object_tml(body)
        return result

    def ImportObject(self, client, p_Tmls):

        metadataController = client.metadata
        body = TspublicRestV2MetadataTmlImportRequest()
        body.import_policy = ImportObjectTMLImportPolicyEnum.VALIDATE_ONLY
        body.object_tml = p_Tmls

        result = metadataController.import_object_tml(body)
        return result
