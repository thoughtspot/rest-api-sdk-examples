#! /usr/bin/env python3
# pylint: disable= no-name-in-module, W1514,  R1732, R1702,  W1514

import glob
import json
import os

from ExportAndImportSample.ExportAndImport.ControllerBase \
    import ControllerBase
from ExportAndImportSample.ExportAndImport.MetadataMethods \
    import MetadataMethods

class StartHere:
    def __init__(self):
        # Provide the host and credentials for login
        self.v_BaseURL = "https://your-testcluster.thoughtspot.com"
        self.v_UserName = "testuser"
        self.v_Password = "testpassword"

    def TMLToFile(self, result, filePath):
        filename = ''

        # Read the filename from info.filename and edoc fields from the response
        data_dict = json.loads(result)
        jsonArray = data_dict['object']
        for data in jsonArray:
            searchInfo = []
            # dataload = json.loads(data)
            searchInfo.append(data['info'])

            if searchInfo != 0:
                for test in searchInfo:
                    try:
                        filename = ''

                        filename = test['filename']
                        filename = os.path.join(filePath, filename)
                    except:
                        print('')

                    if len(filename) != 0:
                        f = open(filename, "w")
                        try:
                            edoc = []
                            edoc.append(data['edoc'])
                        except:
                            print('')
                        if edoc != 0:
                            for element in edoc:
                                f.write(element)
                            f.close()

    def ImportAndExport(self):
        client = ControllerBase.getClient(
            self, self.v_BaseURL, self.v_UserName, self.v_Password)
        userFilter = 'tsadmin'

        # Search Liveboards created by a particular user and get the list of ids
        searchIds = MetadataMethods.SearchObject(self, client, userFilter)

        # Export TML for the list of object ids received from above call
        result = MetadataMethods.ExportObject(self, client, searchIds)

        # Write the TMLs into file on disk(Existing directory)
        filePath = 'tmp/test'
        self.TMLToFile(result, filePath)

        # Read the TMLs from a folder
        tmls = self.TMLFromFile(filePath)

        # Import TML received from above call
        importResult = MetadataMethods.ImportObject(self, client, tmls)
        print('import response::' + importResult)

    def TMLFromFile(self, filePath):
        tmls = []
        os.chdir(filePath)
        for file in glob.glob("*.tml"):
            with open(file) as f:
                content = ''
                content = f.read()
            tmls.append(content)
        return tmls


if __name__ == '__main__':
    s = StartHere()
    s.ImportAndExport()
