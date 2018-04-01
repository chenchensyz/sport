import React, {Component, PropTypes} from 'react';
import configPath from '../config/dev';
// 任务列表的UI组件
class votelist2 extends Component {
    render() {
      let {voteChooseList,handleChoose,handleOpen}=this.props;
        return (
          <ul className="votelist2 mui-table-view">
            {voteChooseList.map((list, index) => {
              return(<li key={index} className="voteItem mui-table-view-cell mui-media">
                      <span className="order">{list.orderNum}</span>
                      <img className='mui-media-object mui-pull-left' onClick={(msg)=>handleOpen(list.remark)} src={list.img} />
                      <div className="mui-media-body" onClick={(msg)=>handleOpen(list.remark)}>
                              <h3>{list.name}</h3>
                              <p className="vote">票数：<em>{list.count}</em></p>
                              <p className="vote-content">{list.remark}</p>
                      </div>
                      <button onClick={(id)=>handleChoose(list.id)} className={list.checked?'active':''}>{list.checked?'已投':'投票'}</button>
                </li>)  

            })}
          </ul>
        );
    }
}

export default votelist2;













