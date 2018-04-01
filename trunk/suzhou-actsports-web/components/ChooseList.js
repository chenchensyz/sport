import React, {Component, PropTypes} from 'react';
import CheckboxCell from './CheckboxCell';
import RadioCell from './RadioCell';

// 任务列表的UI组件
class ChooseList extends Component {
	isRadio(){
		if(this.props.type=='radio'){
			return true;
		}else{
			return false;
		}
	}
    render() {
        return (
            <div>
            		<h5>{this.props.title+'：'}</h5>
            		<ul className='mui-input-group'>
	            		{this.props.lists.map((list, index) => {
	            			if(this.isRadio()){
		                    	return (<RadioCell key={index} id={list.id} text ={list.text} code={index+'radio'} onClick={(name,id)=>this.props.handleRadio(this.props.name,list.id)} executable={list.executable}/>);
		                	}else{
		                    	return (<CheckboxCell key={index} id={list.id} text ={list.text} code={index+'checkbox'} onClick={(name,id)=>this.props.handleChoose(this.props.name,list.id)} executable={list.executable}/>);
            				}
		                })}
	                </ul>      
            </div>
        );
    }
}

ChooseList.propTypes = {
	title:PropTypes.string.isRequired,
    type: PropTypes.string.isRequired
}

export default ChooseList;