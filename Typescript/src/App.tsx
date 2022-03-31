import {
  AccessLevelEnum,
  Client,
  DataController,
  DEFAULT_CONFIGURATION,
  FormatType1Enum,
  MetadataController,
  SessionController,
  Type10Enum,
} from "@thoughtspot/rest-api-sdk";
import { useEffect, useState } from "react";
import "./App.css";
import { GridView } from "./components/gridview";

const App = () => {
  const [liveBoards, setLiveBoards] = useState([]);
  const selectedLiveBoards: Array<any> = [];

  DEFAULT_CONFIGURATION.baseUrl = "https://10.79.142.102";
  DEFAULT_CONFIGURATION.acceptLanguage = "*";

  let client = new Client(DEFAULT_CONFIGURATION);
  const updateAcessToken = (token: string | undefined) => {
    if (token) {
      DEFAULT_CONFIGURATION.accessToken = token;
      client = new Client(DEFAULT_CONFIGURATION);
    }
  };

  const getLiveboards = async () => {
    const metadataController = new MetadataController(client);
    const liveBoards: any = await metadataController.searchObjectHeader({
      type: Type10Enum.LIVEBOARD,
    });
    setLiveBoards(liveBoards.result.headers);
  };

  const getAccessToken = async () => {
    const sessionController = new SessionController(client);
    const tokenRes = await sessionController.getToken({
      userName: "tsadmin",
      password: "admin",
      accessLevel: AccessLevelEnum.FULL,
    });
    updateAcessToken(tokenRes.result.token?.toString());
    getLiveboards();
  };

  useEffect(() => {
    getAccessToken();
  }, []);

  const getLiveBoardName = (): string => {
    const liveboards: any = liveBoards.filter(
      (val: any) => val.id === selectedLiveBoards[0]
    );
    if (liveboards.length === 0) return "Liveboard";
    return liveboards[0].name;
  };

  const exportLiveboard = async () => {
    const dataController = new DataController(client);
    const exportLiveBoardData: any = await dataController.liveboardData({
      id: selectedLiveBoards[0].toString(),
      formatType: FormatType1Enum.FULL,
    });
    const fileName = getLiveBoardName();
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

  const pushSelectedLiveboard = (event: any) => {
    selectedLiveBoards.splice(0, selectedLiveBoards.length);
    if (event.target.checked) selectedLiveBoards.push(event.target.value);
  };

  return (
    <div>
      <div className="headDownload">
        LIVEBOARDS
        <button onClick={exportLiveboard} className="runBtn">
          Download <i className="fa fa-download"></i>
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

export default App;
