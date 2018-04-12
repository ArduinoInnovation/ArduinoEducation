import React from "react";

const WrapperStyle = {
  display: "flex",
  height: "100%",
  width: "100%"
};

const Container = ({ children }) => <div style={WrapperStyle}>{children}</div>;

export default Container;
