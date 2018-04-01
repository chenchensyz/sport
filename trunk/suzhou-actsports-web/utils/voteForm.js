import validator from 'validator';
import {toastShowOff} from '../actions/toDoAction';

validator.isPhone = function(value) {
      if(/^1[34578]\d{9}$/.test(value)){
            return true;
      }else{
          return false;
      }
};
validator.isCard = function(value) {
      if(/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|x)$/.test(value)){
            return true;
      }else{
          return false;
      }
};

export default (parent, inputs) => {

  let data={};

  // for (let input of inputs ) {
    for (var j=0;j<inputs.length;j++) {
      let input = inputs[j];
      let value = input.value,chooseList=input.chooseList,title = input.title; 
      let {dispatch} = parent.props;
      if(value!=undefined){
        if(input.rules){
          for(let i=0;i<input.rules.length;i++){
            if(input.rules[i]=='required' && validator.isEmpty(value)){
                toastShowOff(dispatch,title+'不能为空');
                return;
            }
            if(input.rules[i].split('-').length>1){
                let L = value.length;
                if(L>input.rules[i].split('-')[1] || L<input.rules[i].split('-')[0]){
                    toastShowOff(dispatch,title+'的长度要在'+input.rules[i].split('-')[0]+'到'+input.rules[i].split('-')[1]+'之间');
                    return;
                }
            }
            if(input.rules[0]=='required' && input.rules[i]!='required' && !(input.rules[i].split('-').length>1) && !validator[toFunc(input.rules[i])](value)){
                toastShowOff(dispatch,title+'格式不正确');
                return;
            }

            if(input.rules[0]!='required' && input.rules[i]!='required' && !(input.rules[i].split('-').length>1) &&value!='' && !validator[toFunc(input.rules[i])](value)){
                toastShowOff(dispatch,title+'格式不正确');
                return;
            }


            data[title] = value;
            toastShowOff(dispatch,null);
          }
        }
      }
  }
  return data;
}

function toFunc(value){
  if(typeof value =='string'){
    var newValue = value.replace(/(^|\s+)\w/g,function(s){
            return s.toUpperCase();
         });
  }
  
  return 'is' + newValue;
}