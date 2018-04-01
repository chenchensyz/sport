import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import validator from 'validator';
import 'whatwg-fetch';
import {doValue,updateNum,toastShowOff,postData,updateStatus,updateMsg,buttonFlag} from '../actions/toDoAction';
import {textValue,addImg,delImg,isCountOver,isImg,isFile} from '../actions/collectAction';
import voteForm from '../utils/voteForm';
import Toast from '../components/Toast';
import FormList from '../components/FormList';
import CardImg from '../components/CardImg';
import CardFile from '../components/CardFile';
import configPath from '../config/dev';

require('../static/style/collectPage.css');

// var countMax=1000,countMin=20;



class CollectForm extends Component{

    handleArea(e){
      let {dispatch,areaValue,textCount,textMin,textMax}=this.props;
      dispatch(textValue(e.target.value));
      dispatch(updateNum(e.target.value.length));
      if(e.target.value.length>textMax){
        dispatch(isCountOver(true));
      }else{
        dispatch(isCountOver(false));
      }
    }

    handleClick() {
      let {dispatch,areaValue,textCount,isImgShow,isFileShow,uploadImg,enterID,sessionID,textMin,textMax}=this.props;
      let dataMsg=null;
      if(textCount==0){
        toastShowOff(dispatch,'您还没有发表您的高见');
        return;
      }
      if(textCount<textMin){
        toastShowOff(dispatch,'亲，字数太少啦');
        return;
      }
      if(textCount>textMax){
        toastShowOff(dispatch,'亲，意见输入长度不能超过一千字哦');
        return;
      }

      if(this.props.FormList.length){
        let formMsg=voteForm(this,this.props.FormList);
        dataMsg=formMsg?voteForm(this,this.props.FormList):null;
      }

      if(this.props.FormList.length&&dataMsg || !this.props.FormList.length){
          dataMsg=this.props.FormList.length?dataMsg:{};
          if(isImgShow&&uploadImg.length){
            let list=[];
            for(let i=0;i<uploadImg.length;i++){
              list.push(uploadImg[i].id);
            }
            dataMsg.imgList=list;
          }

          dataMsg.collectText=areaValue;
          dataMsg.collectId=enterID;
          console.log(dataMsg)
          dispatch(buttonFlag(true));
          //发送后台数据
          let url = sessionID?configPath.path + 'collect/index?sessionId='+sessionID:configPath.path + 'collect/index';
          postData(url,dataMsg).then((data) => {
            if(data.status=='success'){
                dispatch(updateMsg(data.message));            
                dispatch(updateNum(data.currentNum));
              }
              if(data.status=='end'){
                dispatch(updateMsg(data.message));
                toastShowOff(dispatch,'活动已经结束了');
              }
              dispatch(buttonFlag(false));
              dispatch(updateStatus(data.status));
          });
          
      }  
    }

    handleAddImg(){
      let {dispatch,weixinConfig}=this.props;

      wx.chooseImage({
              count: 1, // 默认9
              sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
              sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
              success: function (data) {console.log(data)
                var localIds = data.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                console.log("图片地址：" + localIds);
                //选完图片后上传至服务器
                wx.uploadImage({
                  localId: localIds[0], 
                  isShowProgressTips: 1, 
                  success: function (data) {
                      var serverId = data.serverId; 
                      dispatch(addImg(serverId,localIds));
                  }
                });
              }
      });

      
    }
    render(){
            const{dispatch,textCount,isOver,uploadImg,isImgShow,isFileShow,buttonToggle,textMax,textMin} = this.props;
 
            return(
                <div id="collectPage">
                        <div className="textarea-card">
                            <h5>期待您的高见</h5>
                            <div className="row mui-input-row">
                                <textarea placeholder="您的宝贵意见，就是我们进步的源泉" onChange={(e)=>this.handleArea(e)}></textarea>
                                <p className={isOver?'red':''}> <em>{textCount}</em>&frasl;<em>{textMax}</em>字 </p>
                            </div>
                        </div>

                        {isImgShow?<CardImg uploadList={uploadImg} handleAddImg={()=>this.handleAddImg()} handleDelImg={(id)=>dispatch(delImg(id))}/>:null}
                        {this.props.FormList.length?
                          <div className="mui-card">
                              <div className="mui-card-content">
                                    <div className="mui-card-content-inner">
                                          <FormList formLists={this.props.FormList} handleChg={(id,text)=>dispatch(doValue(id,text))}/>
                                    </div>
                              </div>
                        </div>:null}
                        <div className="btn-margin">
                        {buttonToggle?
                          <button disabled className="mui-btn mui-btn-block mui-btn-blue">意见提交</button>:
                          <button onClick={() => this.handleClick()} className="mui-btn mui-btn-block mui-btn-blue">意见提交</button>
                        }
                        </div>
                          <Toast showToast = {this.props.showToast} validateMsg={this.props.validateMsg} />
                </div>
                  )
              }
}


function select(state) {
      return{
            FormList:state.formList,
            validateMsg:state.validateMsg,
            showToast:state.showToasts,
            enterID:state.enterID,
            sessionID:state.sessionID,
            areaValue:state.areaValue,
            textCount:state.textCount,
            isOver:state.isOver,
            uploadImg:state.uploadImg,
            isImgShow:state.isImgShow,
            isFileShow:state.isFileShow,
            weixinConfig:state.weixinConfig,
            buttonToggle:state.buttonToggle,
            textMin:state.textMin,
            textMax:state.textMax
      }
}

export default connect(select)(CollectForm);
