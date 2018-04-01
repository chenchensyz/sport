
import React, {Component, PropTypes} from 'react';

// 上传图片组件
class FormList extends Component {
    render() {
        return (
                <div className="mui-card img-card">
                  <div className="mui-card-content">
                        <div className="mui-card-content-inner">
                           <h5>上传图片</h5>
                            <ul>                            
                            {this.props.uploadList.map((list, index) => {
                              return(
                                <li key={'img'+index}>
                                  <img src={list.src} />
                                  <span className='del' onClick={()=>this.props.handleDelImg(list.id)}><i className="mui-icon mui-icon-closeempty"></i></span>
                                </li>  
                                )
                            })}
                              <li className='upload uploadImg' onClick={()=>{this.props.handleAddImg()}}>
                                +
                              </li>
                            </ul>
                        </div>
                      </div>
                </div>
        );
    }
}

export default FormList;