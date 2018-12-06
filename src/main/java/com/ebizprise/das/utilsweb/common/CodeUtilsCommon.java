package com.ebizprise.das.utilsweb.common;

//import com.ebizprise.das.db.entity.SysMsgCode;
import com.ebizprise.das.utilsweb.form.HasDataForm;
import com.ebizprise.das.utilsweb.form.NoDataForm;
//import com.ebizprise.das.utilsweb.service.errorCode.ErrorCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 *
 * @author maduar
 * @date 24/07/2018 1:52 PM
 * @email maduar@163.com
 *
 * */
@Component
public class CodeUtilsCommon {

//    @Atowired
//    public ErrorCodeService errorCodeService;

    public HasDataForm getHasDataErrorForm(String errCode) {
        HasDataForm hasDataForm = new HasDataForm();
//        SysMsgCode sysMsgCode = errorCodeService.getSysMsgCodeByCode(errCode);
        String errMsg = "error";

        hasDataForm.setSuccess(false);
        hasDataForm.setError(errMsg);
        hasDataForm.setErrCode(errCode);
        return hasDataForm;
    }

    public NoDataForm getNoDataErrorForm(String errCode) {
        NoDataForm noDataForm = new NoDataForm();
//        SysMsgCode sysMsgCode = errorCodeService.getSysMsgCodeByCode(errCode);
        String errMsg = "error";

        noDataForm.setSuccess(false);
        noDataForm.setError(errMsg);
        noDataForm.setErrCode(errCode);
        return noDataForm;
    }
}

