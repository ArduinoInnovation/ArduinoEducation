import React from "react";
import { Tag,Col,Row,Icon,Button } from 'antd';
import { Draggable } from "react-beautiful-dnd";

const getCardStyle= isDragging => ({

});

const combinedStyles = (styles,selected ,isDragging)=> ({
  borderRight: selected?"5px solid rgb(140, 200, 255)":undefined,
  borderRadius: "4px",
  padding: selected?"10px 5px 10px 10px":"10px 10px",
  margin: "0px 0px 10px 0px",
  background:'white',
  boxShadow:isDragging?'0 10px 20px rgba(0, 0, 0, 0.2)':undefined,
  transition:'0.2s box-shadow',
  position:'relative',
  ...styles,
});

const StateLabel=({title,state,offset})=>(
  <Col xl={8} offset={offset}>
  <div>
    <span style={{position:'relative',paddingRight:'4px'}}>{title}
      <span style={{position:'absolute', right:'-5px',top:'5px', display:'inline-block',backgroundColor:state?'#00c500':'#ffa39e',width:'5px',height:'5px',borderRadius:'5px'}}/>
    </span>
  </div>
  </Col>
);

const Card = ({laneId, id, index, title,onClick,content,selectedId,onDelete }) => {
  return ( <Draggable draggableId={`${id}`} type="CARD" index={index}>
    {(provided, snapshot) => (
      <div>
        <div

          ref={provided.innerRef}
          {...provided.draggableProps}
          {...provided.dragHandleProps}
          style={combinedStyles(provided.draggableProps.style,selectedId==id,snapshot.isDragging)}
        >
          <Button type="normal" onClick={()=>onDelete(id,laneId)}  style={{border:"1px solid transparent", position:'absolute',right:'10px',top:'10px'}} shape="circle" icon="close" />
          <div onClick={()=>onClick(id,laneId)}>
            <div style={{fontSize:'20px'}}>{"STEP "}{index}</div>
            <Row justify="space-between" align="bottom">
              <StateLabel title="红外线" state={content.infrared}/>
              <StateLabel title="驱动马达" state={content.action.isActive}/>
              <StateLabel title="智能避障" state={content.ultral.isActive}/>
              <StateLabel title="语音识别" state={content.voice}/>
              <StateLabel title="转发控制字" state={content.forward}/>
            </Row>
          </div>

        </div>
        {provided.placeholder}
      </div>
    )}
  </Draggable>);
};


export default Card;
