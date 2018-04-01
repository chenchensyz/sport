import React, {Component, PropTypes} from 'react';
import TextItem from './TextItem';
import NumberItem from './NumberItem';
import ChooseList from './ChooseList';
import TextareaItem from './TextareaItem';

// 任务列表的UI组件
class FormList extends Component {
    render() {
        return (
            <div>
            		{this.props.formLists.map((list, index) => {
                        if(list.type =='text'){
                            return (<TextItem key={index} title ={list.title} name={list.id} onChange= {this.props.handleChg}/>);
                        }
                        if (list.type=='number'){
                            return (<NumberItem key={index} title ={list.title} name={list.id} onChange= {this.props.handleChg}/>);
                        }
                        if((list.type=='checkbox'||list.type=='radio')&&list.chooseList.length>1){
                            return (<ChooseList key={index} title ={list.title} handleChoose={this.props.handleChoose} handleRadio={this.props.handleRadio} lists={list.chooseList} type={list.type} name={list.id}/>);
                        }
                        if(list.type=='textarea'){
                            return (<TextareaItem key={index} title ={list.title} name={list.id} onChange= {this.props.handleChg}/>);
                        }
	                })}
            </div>
        );
    }
}
FormList.propTypes = {
    handleChg:PropTypes.func.isRequired
}

export default FormList;