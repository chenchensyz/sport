import React, {Component, PropTypes} from 'react';
import configPath from '../config/dev';
//configPath.staticPath+"static/img/vote_item.png"
// 任务列表的UI组件
class votelist1 extends Component {
    render() {
      let {voteChooseList,handleChoose}=this.props;
        return (
            <ul className="votelist1 mui-table-view">
            {voteChooseList.map((list, index) => {
                return(<li key={index} className="voteItem mui-table-view-cell mui-media">
                      <span className="order">{list.orderNum}</span>
                      <img className='mui-media-object mui-pull-left' src={list.img} />
                      <div className="mui-media-body">
                              <h3>{list.name}</h3>
                              <p className="vote">票数：<em>{list.count}</em></p>
                      </div>
                      <button onClick={(id)=>handleChoose(list.id)} className={list.checked?'active':''}>{list.checked?'已投':'投票'}</button>
                </li>)
            })}
            </ul>
        );
    }
}


export default votelist1;