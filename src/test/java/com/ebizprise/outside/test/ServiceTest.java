//package com.ebizprise.outside.test;
//
//import java.io.FileNotFoundException;
//import java.lang.reflect.InvocationTargetException;
//import java.util.List;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//
//import com.ebizprise.das.form.weather.ApiWeatherListForm;
//import com.ebizprise.das.utilsweb.form.NoDataForm;
//import com.ebizprise.outside.base.BaseSet;
//
///*
// *
// * @author maduar
// * @date 30/07/2018 3:46 PM
// * @email maduar@163.com
// *
// * */
//public class ServiceTest extends BaseSet {
//
//	@Autowired
//	private ErrorCodeService errorCodeService;
//
//	@Autowired
//	private ApiWeatherListService apiWeatherListService;
//
//	@Autowired
//	private FileWeatherService fileWeatherService;
//
//	@Autowired
//	private SmtpMailService smtpMailService;
//
//	@Autowired
//	private MsgCodeService msgCodeService;
//
//	@Value("${sqlIp}")
//	private String sqlIp;
//
//	@Test
//	public void test() throws FileNotFoundException, MessagingException {
//
//		String defaultStr = "OK";
//		System.out.println("-------begin-----");
//		String custId = "000231";
//		NoDataForm noDataForm = fileWeatherService.filecalendarData(custId);
//
//		Assert.assertEquals(noDataForm, null);
//		System.out.println("-------end-----");
//	}
//
//	@Test
//	public void test2() throws FileNotFoundException, MessagingException,
//			InvocationTargetException, NoSuchMethodException,
//			IllegalAccessException, NoSuchFieldException {
//
//		String defaultStr = "OK";
//		System.out.println("-------begin-----");
//
//		String custId = "000231";
//		String cityId = "101010100";
//		String month = "201808";
//
//		List<ApiWeatherListForm> listForms = apiWeatherListService
//				.listApiWeatherListForm(cityId, custId, month);
//
//		Assert.assertEquals(listForms.size(), null);
//		System.out.println("-------end-----");
//	}
//
//	@Test
//	public void testMail() throws MessagingException {
//
//		Assert.assertNotNull(smtpMailService);
//
//		String custId = "000231";
//		String mailStatus = "MAIL019";
//
//		smtpMailService.sendMail(custId, mailStatus, null, null);
//
//	}
//
//	@Test
//	public void testSysCodeMapper() {
//
//		String custId = "000231";
//		String mailStatus = "MAIL018";
//		System.out.println("sqlIp: " + sqlIp);
//		SysMsgCode sysMsgCode = msgCodeService.getMsgCode(mailStatus);
//
//		Assert.assertNotNull(sysMsgCode);
//
//	}
//}
