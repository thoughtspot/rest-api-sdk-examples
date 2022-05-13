import {
  Client,
  DEFAULT_CONFIGURATION,
  ExportObjectTMLExportAssociatedEnum,
  ExportObjectTMLFormatTypeEnum,
  ImportObjectTMLImportPolicyEnum,
  MetadataController,
  SearchObjectHeaderTypeEnum,
} from "@thoughtspot/rest-api-sdk";
import { useEffect, useState } from "react";
import "../App.css";
import { GridView } from "./gridview";
import { ControllerBase } from "./controller-base";
const ExportAndImport = () => {
  const [liveBoards, setLiveBoards] = useState([]);
  const selectedLiveBoards: Array<any> = [];
  const edocList: Array<any> = [];
  let client = new Client(DEFAULT_CONFIGURATION);
  let fileReader: any;

  /**
   * Get liveboard data.
   */
  const getLiveboards = async () => {
    const metadataController = new MetadataController(client);
    const liveBoards: any = await metadataController.searchObjectHeader({
      type: SearchObjectHeaderTypeEnum.LIVEBOARD,
    });
    setLiveBoards(liveBoards.result.headers);
  };

  /**
   * Get AccessToken.
   */
  const getAccessToken = async () => {
    let token: string | undefined = await ControllerBase();
    if (token) {
      DEFAULT_CONFIGURATION.accessToken = token;
      client = new Client(DEFAULT_CONFIGURATION);
    }
    getLiveboards();
  };

  useEffect(() => {
    getAccessToken();
  }, []);

  /**
   *  return Selected Liveboard name.
   */
  const getLiveBoardName = (): string => {
    const liveboards: any = liveBoards.filter(
      (val: any) => val.id === selectedLiveBoards[0]
    );
    if (liveboards.length === 0) return "Liveboard";
    return liveboards[0].name;
  };

  const exportTml = async () => {
    const metadataController = new MetadataController(client);
    let selectedLiveboard = selectedLiveBoards[0].toString();
    const exportLiveBoardData: any = await metadataController.exportObjectTML({
      id: [selectedLiveboard.toString()],
      formatType: ExportObjectTMLFormatTypeEnum.YAML,
      exportAssociated: ExportObjectTMLExportAssociatedEnum.True,
    });
    let fileName = getLiveBoardName();
    const element = document.createElement("a");
    element.setAttribute(
      "href",
      "data:text/plain;charset=utf-8, " +
        encodeURIComponent(JSON.stringify(exportLiveBoardData.result))
    );
    element.setAttribute("download", `${fileName}.json`);
    document.body.appendChild(element);
    element.click();

    document.body.removeChild(element);
  };
  const handleFileRead = () => {
    const contentRes = fileReader.result;
    const resObject = JSON.parse(contentRes);
    for (let i = 0; i < resObject.object.length; i++) {
      let obj = resObject.object[i];
      let edoc = obj["edoc"];
      if (edoc) {
        edocList.push(edoc);
      }
    }
    importTml();
  };

  const handleFileChosen = (event: any) => {
    let file = event.target.files[0];
    fileReader = new FileReader();
    fileReader.onloadend = handleFileRead;
    fileReader.readAsText(file);
  };

  const importTml = async () => {
    const metadataController = new MetadataController(client);
    const importLiveboardData: any = await metadataController.importObjectTML({
      objectTML: [edocList.toString()],
      importPolicy: ImportObjectTMLImportPolicyEnum.PARTIAL,
    });
    if (importLiveboardData.statusCode.toString() === '200') {
      alert("Successfully imported the objects in TML");
    }
  };

  const pushSelectedLiveboard = (event: any) => {
    selectedLiveBoards.splice(0, selectedLiveBoards.length);
    if (event.target.checked) selectedLiveBoards.push(event.target.value);
  };

  return (
    <div>
      <div className="headDownload">
        LIVEBOARDS
     
        <input
          type="file"
          className="dataBtn"
          name="Import Tml"
          onChange={handleFileChosen}
        />
        <button onClick={exportTml} className="runBtn">
          Export <i className="fa fa-download"></i>
        </button>
      </div>
      <div className="content">
        {liveBoards.length === 0 ? (
          "loading..."
        ) : (
          <GridView
            liveboards={liveBoards}
            pushSelectedLiveboard={pushSelectedLiveboard}
          ></GridView>
        )}
      </div>
    </div>
  );
};

export default ExportAndImport;
