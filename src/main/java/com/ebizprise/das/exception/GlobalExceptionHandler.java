package com.ebizprise.das.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.ebizprise.das.enums.SysMsgCodeType;
import com.ebizprise.das.form.system.StatusForm;
import com.ebizprise.das.utils.CommonUtils;
import com.ebizprise.das.utilsweb.common.CodeUtilsCommon;
import com.ebizprise.das.utilsweb.form.NoDataForm;

@CrossOrigin
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Autowired
	private MessageSource msgSource;

	@Autowired
	private CommonUtils commonUtils;

	@Autowired
	private CodeUtilsCommon codeUtilsCommon;

	@ExceptionHandler(value = BaseException.class)
	public StatusForm handleBaseException(BaseException e) { // HTMLException
		if (logger.isInfoEnabled()) {
			logger.info("handleBaseException(BaseException) - msg1_", e.getMessage()); //$NON-NLS-1$
		}
		logger.error("handleBaseException(Exception) - msg:", e.getLocalizedMessage());
		StatusForm statusForm = new StatusForm();
		 statusForm.setError("ERR9990");
//		statusForm.setError(e.getMessage());
		statusForm.setSuccess(false);
		return statusForm;
	}

	@ExceptionHandler(value = InsertRowException.class)
	public NoDataForm InsertRowException(InsertRowException e) { // 自訂Exception

		if (logger.isInfoEnabled()) {
			logger.info("InsertRowException(Exception) - msg2_", e.getMessage()); //$NON-NLS-1$
		}

		logger.error("InsertRowException(Exception) - msg:", e.getLocalizedMessage());

		return NoDataForm.error(null, e.getMessage());
	}

	@ExceptionHandler(value = Exception.class)
	public NoDataForm handleException(Exception e) { // 父類別Exception

		if (logger.isInfoEnabled()) {
//			logger.info("handleException(Exception) - msg3_", e.getMessage()); //$NON-NLS-1$
//			logger.info("handleException(Exception) - msg:", e.getLocalizedMessage());
			logger.info("handleException(Exception) - msg3->:", e);
		}

		String errorCode = SysMsgCodeType.ERR999.getCode();
		NoDataForm noDataForm = codeUtilsCommon.getNoDataErrorForm(errorCode);
		return noDataForm;
	}

	@ExceptionHandler(value = NumberFormatException.class)
	public StatusForm numHandleException(Exception e) { // 數值Exception

		if (logger.isInfoEnabled()) {
			logger.info("numHandleException(Exception) - msg4_", e.getMessage()); //$NON-NLS-1$
			logger.error("numHandleException(Exception) - msg:", e.getLocalizedMessage());
		}

		String errMsg = commonUtils.getErrMsg(SysMsgCodeType.ERR997.getCode());
		logger.info("handleException(Exception) - msg4->:" + errMsg);

		StatusForm statusForm = new StatusForm();
		 statusForm.setError(errMsg);
//		statusForm.setError(e.getMessage());
		statusForm.setSuccess(false);
		return statusForm;
	}

//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ResponseBody
//	public MessageDTO processValidationError(MethodArgumentNotValidException ex) {
//		if (logger.isInfoEnabled()) {
//			logger.info("processValidationError(MethodArgumentNotValidException) - validate"); //$NON-NLS-1$
//		}
//
//		BindingResult result = ex.getBindingResult();
//		FieldError error = result.getFieldError();
//
//		return processFieldError(error);
//	}
//
//	private MessageDTO processFieldError(FieldError error) {
//		MessageDTO message = null;
//		if (error != null) {
//			Locale currentLocale = LocaleContextHolder.getLocale();
//			String msg = msgSource.getMessage(error.getDefaultMessage(), null, currentLocale);
//			message = new MessageDTO(MessageType.ERROR, msg);
//		}
//		return message;
//	}
}
