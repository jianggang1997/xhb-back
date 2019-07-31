package com.siki.xhb.user.service.imp;

import com.siki.xhb.user.config.AppProperties;
import com.siki.xhb.user.dao.*;
import com.siki.xhb.user.model.*;
import com.siki.xhb.user.model.UserDynamicInfo;
import com.siki.xhb.user.service.UserOperationService;
import com.siki.xhb.userI.utils.Md5Util;
import com.siki.xhb.userI.utils.SidUtil;
import com.siki.xhb.userI.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
@Service
public class UserOperationServiceImp implements UserOperationService {

    @Autowired
    private RegisterInfoDao registerInfoDao;

    @Autowired
    private UserDynamicDao userDynamicDao;

    @Autowired
    private UserImgsDao userImgsDao;

    @Autowired
    private StampInfoDao stampInfoDao;

    @Autowired
    private DynamicShareInfoDao dynamicShareInfoDao;

    @Autowired
    private DynamicCommentDao dynamicCommentDao;

    @Autowired
    private AppProperties appProperties;

    @Override
    public boolean userRegister(UserRaegisterReq userRaegisterReq,String ip) {
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setAccount(userRaegisterReq.getAccount());
        registerInfo.setPwd_md5(Md5Util.encrypt32(userRaegisterReq.getPassword()));
        registerInfo.setSid(SidUtil.generator());
        registerInfo.setReg_ip(ip);
        String regMail = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";
        if(Pattern.matches(regMail,userRaegisterReq.getAccount())){
            registerInfo.setReg_type(1);
        }else {
            registerInfo.setReg_type(2);
        }
        registerInfo.setInfo_status(0);

        return registerInfoDao.insertRegisterInfo(registerInfo)>0;
    }

    @Override
    public String validateUserLogin(UserLoginReq userLoginReq) {
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setAccount(userLoginReq.getAccount());
        List<RegisterInfo> infos = registerInfoDao.selectRegisterInfo(registerInfo,0,1);
        if(infos.size() == 1){
            if(Md5Util.encrypt32(userLoginReq.getPassword()).equals(infos.get(0).getPwd_md5())
                    && userLoginReq.getAccount().equals(infos.get(0).getAccount())){
                return infos.get(0).getSid();
            }else{
                return "2";
            }
        }else {
            return "1";
        }
    }

    @Override
    public boolean userMidifyPass(ModifyPassReq modifyPassReq) {
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setAccount(modifyPassReq.getAccount());
        registerInfo.setPwd_md5(Md5Util.encrypt32(modifyPassReq.getNewPass()));
        return  registerInfoDao.updateRegisterInfo(registerInfo)>0;
    }

    @Override
    public boolean checkisExistAccout(String accout) {
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setAccount(accout);
        List<RegisterInfo> registerInfos = registerInfoDao.selectRegisterInfo(registerInfo, 0, 1);
        if(registerInfos.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean publishDynamic(PublishDynamicReq publishDynamicReq,String userId) {
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setSid(userId);
        List<RegisterInfo> registerInfos = registerInfoDao.selectRegisterInfo(registerInfo, 0, 1);
        List<PublishDynamicReq.DynamicImg> dynamicImgs = publishDynamicReq.getDynamicImgs();
        List<String> sids = new ArrayList<>(9);
        String imgIds = "";
        for(PublishDynamicReq.DynamicImg dynamicImg : dynamicImgs){
            UserImgInfo imgInfo = new UserImgInfo();
            String sid = SidUtil.generator();
            sids.add(sid);
            imgInfo.setSid(sid);
            imgInfo.setReg_id(registerInfos.get(0).getId());
            imgInfo.setName(dynamicImg.getName());
            imgInfo.setSize(Integer.valueOf(dynamicImg.getSize()));
            imgInfo.setType(dynamicImg.getMimeType());
            imgInfo.setUrl(appProperties.getOssBaseUrl()+dynamicImg.getUrl());
            userImgsDao.insertUserImg(imgInfo);
        }
        StringBuffer sb = new StringBuffer();
        if(sids.size()>0){
            List<Integer> ids = userImgsDao.selectImgIdBySids(sids);
            for(Integer integer : ids){
                sb.append(integer+";");
            }
        }
        UserDynamicInfo userDynamicInfo = new UserDynamicInfo();
        userDynamicInfo.setSid(SidUtil.generator());
        userDynamicInfo.setContent(publishDynamicReq.getContent());
        userDynamicInfo.setReg_id(registerInfos.get(0).getId());
        userDynamicInfo.setImgs(sb.toString());
        int res = userDynamicDao.insertUserDynamic(userDynamicInfo);
        return res>0;
    }

    @Override
    public boolean stampDynamic(StampDynamicReq stampDynamicReq, String userId) {
        DynamicStampInfo dynamicStampInfo = new DynamicStampInfo();
        UserDynamicInfo userDynamicInfo = new UserDynamicInfo();
        userDynamicInfo.setSid(stampDynamicReq.getDynamicSid());
        List<UserDynamicInfo> userDynamicInfos = userDynamicDao.selectUserDynamic(userDynamicInfo, 0, 1);
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setSid(userId);
        List<RegisterInfo> registerInfos = registerInfoDao.selectRegisterInfo(registerInfo, 0, 1);
        dynamicStampInfo.setSid(SidUtil.generator());
        dynamicStampInfo.setDyn_id(userDynamicInfos.get(0).getId());
        dynamicStampInfo.setReg_id(registerInfos.get(0).getId());
        return stampInfoDao.insertStampInfo(dynamicStampInfo)>0;
    }

    @Override
    public boolean shareDynamic(ShareDynamicReq shareDynamicReq, String userId) {
        DynamicShareInfo dynamicShareInfo = new DynamicShareInfo();
        UserDynamicInfo userDynamicInfo = new UserDynamicInfo();
        userDynamicInfo.setSid(shareDynamicReq.getDynamicSid());
        List<UserDynamicInfo> userDynamicInfos = userDynamicDao.selectUserDynamic(userDynamicInfo, 0, 1);
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setSid(userId);
        List<RegisterInfo> registerInfos = registerInfoDao.selectRegisterInfo(registerInfo, 0, 1);
        dynamicShareInfo.setSid(SidUtil.generator());
        dynamicShareInfo.setDyn_id(userDynamicInfos.get(0).getId());
        dynamicShareInfo.setReg_id(registerInfos.get(0).getId());
        dynamicShareInfo.setContent(shareDynamicReq.getContent());
        return dynamicShareInfoDao.insertShareInfo(dynamicShareInfo)>0;
    }

    @Override
    public boolean CommentDynamic(CommentDynamicReq commentDynamicReq, String userId) {
        DynamicCommentInfo dynamicCommentInfo = new DynamicCommentInfo();
        UserDynamicInfo userDynamicInfo = new UserDynamicInfo();
        userDynamicInfo.setSid(commentDynamicReq.getDynamicSid());
        List<UserDynamicInfo> userDynamicInfos = userDynamicDao.selectUserDynamic(userDynamicInfo, 0, 1);
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setSid(userId);
        List<RegisterInfo> registerInfos = registerInfoDao.selectRegisterInfo(registerInfo, 0, 1);
        dynamicCommentInfo.setSid(SidUtil.generator());
        dynamicCommentInfo.setContent(commentDynamicReq.getContent());
        dynamicCommentInfo.setReg_id(registerInfos.get(0).getId());
        dynamicCommentInfo.setDyn_id(userDynamicInfos.get(0).getId());
        return dynamicCommentDao.insertComment(dynamicCommentInfo)>0;
    }

    @Override
    public boolean ReplyDynamicComment(ReplyCommentReq replyCommentReq, String userId) {
        CommentReplyInfo commentReplyInfo = new CommentReplyInfo();
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setSid(userId);
        List<RegisterInfo> registerInfos = registerInfoDao.selectRegisterInfo(registerInfo, 0, 1);

        return false;
    }

    @Override
    public List<PlaygroundUserDynRes> queryPlaygroundDynList(QueryDynamicReq queryDynamicReq, String userId) {
        List<PlaygroundUserDynRes> res = new ArrayList<>();
        UserDynamicInfo userDynamicInfo = new UserDynamicInfo();
//        userDynamicDao.selectUserDynamic();
        return null;
    }
}
