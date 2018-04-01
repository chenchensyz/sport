import React, {Component, PropTypes} from 'react';
import configPath from '../config/dev';
// import {Flex,FlexItem} from 'react-weui';

// 任务列表的UI组件
class FlexFigure extends Component {
    render() {
        return (
            <div className="figureList">
            	{this.props.lists.map((list, index) => {
            		return (<div className="figureItem" key={index}>
				                <img src={list.img} />
				                <p><em>{list.orderNum}</em>号</p>
				           </div>)
            	})}
              
            </div>
        );
    }
}

export default FlexFigure;