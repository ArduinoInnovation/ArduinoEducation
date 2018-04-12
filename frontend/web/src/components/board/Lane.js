import React from "react";
import { Droppable } from "react-beautiful-dnd";
import {Row,Col,Icon,Button} from 'antd';
import PerfectScrollbar from "react-perfect-scrollbar";

const LaneStyle = {
  display: "flex",
  flexDirection: "column",
  width: "100%",
  padding: "2em",
  height: "100%",
  background:"#F1F1F1",
  borderRadius:'3px'
};

const OuterWrapperStyle = {
  height: "100%"
};

const InnerWrapperStyle = {
  height: "100%"
};

const HeaderStyle = {
  fontSize: "2em",
  textTransform: "uppercase",
  fontFamily: "sans-serif",
  color: "#888"
};

const plusStyle={
  background:"transparent",
  position:"absolute",
  right:"40px",
  top:"40px",
};

const Lane = ({ id, children, title, onPlus})  => (
  <div style={LaneStyle}>
    <h1 style={HeaderStyle}>{title}
    <Button onClick={()=>onPlus(id)} style={plusStyle} type="normal" shape="circle" icon="plus" /></h1>
    <PerfectScrollbar>

    <div style={OuterWrapperStyle}>
      <Droppable droppableId={`${id}`}>
        {(provided, snapshot) => (
          <div ref={provided.innerRef} style={InnerWrapperStyle}>
            {children}
            {provided.placeholder}
          </div>
        )}
      </Droppable>
    </div>
    </PerfectScrollbar>

  </div>
);

export default Lane;
