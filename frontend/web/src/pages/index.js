import React from 'react';
import { connect } from 'dva';
import {Row,Col,Switch,Radio,Select,Slider,Button,message} from 'antd'
import styles from './index.css';
import PerfectScrollbar from "react-perfect-scrollbar";
import "react-perfect-scrollbar/dist/css/styles.css";
import {map} from "lodash";
import _ from "lodash";
import { DragDropContext } from "react-beautiful-dnd";
import update from "immutability-helper";

import Container from "../components/board/Container.js";
import Lane from "../components/board/Lane.js";
import Card from "../components/board/Card.js";
import Header from "../components/Header"
import {submitCode} from "../services/api"
import cardIdUtil from "../utils/cardIdUtils"
const Option = Select.Option;
const RadioGroup=Radio.Group;


const emptyContent= {
  infrared: true,
  forward: true,
  action: {
    isActive: true,
    direction: "FORWARD",
    speed: 1,
    time: 0
  },
  ultral: {
    isActive: true,
    r: 2
  },
  voice: true
};

class IndexPage extends React.Component{

  count=5;

  state = {
    selectedItem:undefined,
    selectedItemLane:undefined,
    lanes: [{
        id: 0,
        title: "主小车",
        cards: []
      },
      {
        id: 1,
        title: "从小车",
        cards: []
      },
    ]
  };


  onDragEnd = result => {
    console.log("result", result);
    console.log("state",this.state);

    const { draggableId, source, destination } = result;

    if (!draggableId || !destination || !source) {
      return;
    }


    const sourceLane = this.state.lanes.find(
      l => l.id === parseInt(source.droppableId, 10)
    );

    const targetLane = this.state.lanes.find(
      l => l.id === parseInt(destination.droppableId, 10)
    );

    const movingCard = sourceLane.cards.find(
      c => c.id === parseInt(draggableId, 10)
    );

    const newLanes = map(this.state.lanes, lane => {
      // rearrange within same lane
      if (lane === sourceLane && sourceLane === targetLane) {
        return update(sourceLane, {
          cards: {
            $splice: [
              [source.index, 1],
              [destination.index, 0, { ...movingCard }]
            ]
          }
        });
      }

      // other cases
      if (lane === sourceLane) {
        return update(sourceLane, {
          cards: {
            $splice: [[source.index, 1]]
          }
        });
      }

      if (lane === targetLane) {
        return update(targetLane, {
          cards: {
            $splice: [[destination.index, 0, { ...movingCard }]]
          }
        });
      }

      // untouched lane
      return lane;
    });

    this.setState({ lanes: newLanes,selectedItem:draggableId,selectedItemLane:destination.droppableId });
  };

  onSubmit = async () =>{
    const hide = message.loading('Action in progress..', 0);
    const finalData=this.state.lanes.slice(0);

    const result={
      master:finalData[0].cards.map(item=>item.content),
      slave:finalData[1].cards.map(item=>item.content),
    };

    await submitCode(result);
    hide();
    message.success('Success');
  };

  onPlus=(id)=>{
    console.log("plus",id);
    const newCard={
      id: this.count++,
      title: "Card A",
      content:_.cloneDeep(emptyContent)
    };

    console.log("newCard",newCard);

    let newLanes=this.state.lanes.slice(0);
    newLanes[id].cards.push(newCard);
    this.setState({lanes:newLanes});
  };

  onDelete=(id,laneId)=>{
    const newLane=this.state.lanes.slice(0);
    newLane[laneId].cards=newLane[laneId].cards.filter(items=>items.id!=id);
    this.setState({lanes:newLane});

    if(id==this.state.selectedItem){
      this.setState({selectedItem:undefined})
    }
  };

  render(){
    const FormItem=({title,item})=>(
      <Row style={{marginTop:'20px',height:'30px'}}>
        <Col span={12}>
          <div style={{fontSize:'18px' ,fontWeight:'bold'}} >{title}</div>
        </Col>
        <Col span={12}>
          {item}
        </Col>
      </Row>
    );
    const FormMinItem=({title,item})=>(
      <Row style={{margin:'10px 0',height:'30px'}}>
        <Col span={12}>
          <div style={{fontSize:'18px',fontWeight:'bold' }} >{title}</div>
        </Col>
        <Col span={12}>
          {item}
        </Col>
      </Row>
    );
    // const radioStyle = {
    //   display: 'block',
    //   height: '30px',
    //   lineHeight: '30px',
    // };

    let selectedContent= undefined;
    let itemIndex=-1;
    let cloneLanes=this.state.lanes.slice(0);
    if(this.state.selectedItem!==undefined&&this.state.selectedItemLane!==undefined){
      const targetLane=cloneLanes[this.state.selectedItemLane];
      console.log("selectedItemLane",this.state.selectedItemLane);
      console.log("targetLane",targetLane);

      itemIndex=targetLane.cards.findIndex(item => {
        console.log("item.id",item.id);
        return item.id == this.state.selectedItem});
      console.log("itemIndex",itemIndex);
      selectedContent=targetLane.cards[itemIndex].content
    }
    console.log("selectedItemLane",this.state.selectedItemLane);
    console.log("itemIndex",itemIndex);
    console.log("selectedContent",selectedContent);

    const onFormChange=(detail)=>{
      console.log("onFormChange",detail);
      selectedContent={
        ...selectedContent,
        ...detail
      };
      console.log("final",selectedContent);
      cloneLanes[this.state.selectedItemLane].cards[itemIndex].content=selectedContent;



      this.setState({lanes:cloneLanes});
    };

    return (
      <div>
        <Row>
          <Col md={this.state.selectedItem?16:24}>
            <div style={{height:'85vh',width:'80%',margin:"10vh auto 5vh"}}>
              <DragDropContext onDragEnd={this.onDragEnd}>
                <Container>
                  <Row gutter={24} style={{width:'1000px',margin:"0 auto", height:'100%'}}>
                  {map(this.state.lanes, (lane, idx) => (
                    <Col span={12} style={{height:'85vh'}}>

                      <Lane
                      title={lane.title}
                      id={lane.id}
                      key={"lane-" + idx}
                      index={idx}
                      onPlus={this.onPlus}
                    >

                        {map(lane.cards, (card, idx) => (
                        <Card
                          title={card.title}
                          laneId={lane.id}
                          id={card.id}
                          content={card.content}
                          key={"card-" + idx}
                          index={idx}
                          onDelete={this.onDelete}
                          selectedId={this.state.selectedItem}
                          onClick={(id,laneId)=>this.setState({selectedItem:id,selectedItemLane:laneId})}
                        />
                      ))}

                    </Lane>

                    </Col>
                  ))}
                  </Row>
                </Container>
              </DragDropContext>
            </div>
          </Col>
          <Col md={8}>
            {this.state.selectedItem?(<div style={{background:"white",height:'100vh',boxShadow:'0 1px 50px rgba(0, 0, 0, 0.09)',paddingTop:'60px'}}>
              <PerfectScrollbar>
                <div style={{fontSize:'40px',fontWeight:'bold',textAlign:'center',paddingTop:'30px'}}>{`${cloneLanes[this.state.selectedItemLane].title} STEP ${itemIndex}`}</div>
                <div style={{margin:"0 auto", padding:'20px 40px'}}>
                  <FormItem title="红外线" item={<Switch defaultChecked={selectedContent.infrared} onChange={(checked)=>onFormChange({infrared:checked})} />}/>
                  <FormItem title="转发控制字" item={<Switch defaultChecked={selectedContent.forward} onChange={(checked)=>onFormChange({forward:checked})} />}/>
                  <FormItem title="驱动马达"/>
                  <div style={{padding:'10px 20px',borderLeft:'5px solid grey',margin:'10px 5px', background:'rgba(153, 153, 153, 0.14)'}}>
                    <FormMinItem title="是否启用" item={<Switch defaultChecked={selectedContent.action.isActive} onChange={(checked)=>onFormChange({action:{...selectedContent.action,isActive:checked}})} />}/>
                    <FormMinItem title="前进方向" item={
                      <Select  defaultValue={selectedContent.action.direction} onChange={(value)=>onFormChange({action:{...selectedContent.action,direction:value}})}  style={{ width: 120 }}>
                        <Option value="FORWARD">向前</Option>
                        <Option value="LEFT">向左</Option>
                        <Option value="RIGHT">向右</Option>
                        <Option value="RANDOM">随机</Option>
                      </Select>}/>
                    <FormMinItem title="前进速度" item={ <Slider min={0} max={3}  defaultValue={selectedContent.action.speed} onChange={(value)=>onFormChange({action:{...selectedContent.action,speed:value}})} />}/>
                    <FormMinItem title="持续时间" item={ <Slider min={0} max={3}  defaultValue={selectedContent.action.time} onChange={(value)=>onFormChange({action:{...selectedContent.action,time:value}})}  />}/>
                  </div>
                  <FormItem title="超声波智能避障" />
                  <div style={{padding:'10px 20px',borderLeft:'5px solid grey',margin:'10px 5px', background:'rgba(153, 153, 153, 0.14)'}}>
                    <FormMinItem title="是否启用" item={<Switch defaultChecked={selectedContent.ultral.isActive} onChange={(checked)=>onFormChange({ultral:{...selectedContent.ultral,isActive:checked}})}     />}/>
                    <FormMinItem title="半径范围" item={ <Slider min={0} max={3}  defaultValue={selectedContent.ultral.r} onChange={(value)=>onFormChange({ultral:{...selectedContent.ultral,r:value}})}  />}/>
                  </div>
                  <FormItem title="语音识别" item={<Switch  defaultChecked={selectedContent.voice} onChange={(checked)=>onFormChange({voice:checked})}  />}/>
                </div>
              </PerfectScrollbar>
            </div>):<div/>}
          </Col>
        </Row>
        <Header submitEvent={this.onSubmit} />

      </div>
    );
  }
}


export default connect()(IndexPage);


