import React, {Component, PropTypes} from 'react';
import Votelist1 from './votelist1';
import Votelist2 from './votelist2';

// 任务列表的UI组件
class Votelist extends Component {
    render() {
        if(this.props.voteChooseList[0].remark){
            return <Votelist2 {...this.props}/>;
        }else{
            return <Votelist1 {...this.props}/>;
        }
        
    }
}


export default Votelist;