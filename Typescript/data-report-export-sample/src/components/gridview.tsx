import React from "react";
import './gridview.css';

export interface gridProps {
  liveboards: Array<any>;
  pushSelectedLiveboard: (liveBoard: any) => void;
}

export const GridView: React.FC<gridProps> = ({ liveboards, pushSelectedLiveboard }: gridProps) => {
  return (
    <div>
      <div id="lgDemo">
      {liveboards.map(function (liveBoard, idx) {
        return (
          <div className="item" key={idx}>
            <input type="checkbox" value={liveBoard.id} onClick={pushSelectedLiveboard} /> {liveBoard.name}
          </div>
            );
        })}
        </div>
    </div>
  );
};
