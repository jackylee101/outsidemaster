package com.ebizprise.das.web.controller.v1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ebizprise.das.db.model.MyMap;
import com.ebizprise.das.db.repository.RedisRepository;
import com.ebizprise.das.enums.APIStatusCode;
import com.ebizprise.das.exception.InsertRowException;
import com.ebizprise.das.form.system.StatusForm;
import com.ebizprise.das.model.Account;
import com.ebizprise.das.utils.CommonUtils;
import com.ebizprise.das.utilsweb.form.jwt.TokenKeyForm;

@Controller
@RequestMapping("/")
public class WebController {
	private static final Logger logger = LoggerFactory
			.getLogger(WebController.class);

	@Autowired
	private RedisRepository redisRepository;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/keys")
	public @ResponseBody Map<Object, Object> keys() {
		return redisRepository.findAllMyMaps();
	}

	@RequestMapping("/values")
	public @ResponseBody Map<String, String> findAll() {
		Map<Object, Object> aa = redisRepository.findAllMyMaps();
		Map<String, String> map = new HashMap<String, String>();
		for (Map.Entry<Object, Object> entry : aa.entrySet()) {
			String key = (String) entry.getKey();
			map.put(key, aa.get(key).toString());
		}
		return map;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<String> add(@RequestParam String key,
			@RequestParam String value) {

		MyMap movie = new MyMap(key, value, "");

		redisRepository.add(movie);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<String> delete(@RequestParam String key) {
		redisRepository.delete(key, "");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * 請注意@RequestBody註解 添加@ModelAttribute，表示是預設form型態
	 * Form解析可以直接从Request对象中获取请求参数
	 * ，这样对象转换与处理相对容易，但在大片JSON数据需要提交时，可能会出现大量的数据拆分与处理工作，另外针对集合类型的处理，也是其比较孱弱的地方。
	 * 
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "/formPostTest1", method = RequestMethod.POST)
	public HttpEntity<?> formPostTest1(
			@RequestBody @ModelAttribute Account account) {
		String apiHeader = CommonUtils.PRIFIX + "/formPostTest1:";

		account.setVersion(new Date());

		StatusForm statusForm = new StatusForm();
		statusForm.setSuccess(true);
		logger.info("[API]" + apiHeader + "_" + APIStatusCode.END.getCode());
		return ResponseEntity.ok(statusForm);
	}

	/**
	 * 請注意@RequestBody註解 千萬不要畫蛇添足添加@ModelAttribute，否則會被其覆蓋，如下
	 * 而Payload的优势是一次可以提交大量JSON字符串(只能用post)
	 * ，但无法从Request从获取参数，也会受限于JSON解析的深度（尤其是有多层对象级联的情况，最底层的对象几乎无法转换为具体类型）。
	 * 
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "/formPostTest2", method = RequestMethod.POST, consumes = "application/json")
	public HttpEntity<?> formPostTest2(@RequestBody Account account) {
		String apiHeader = CommonUtils.PRIFIX + "/formPostTest2:";

		account.setVersion(new Date());

		StatusForm statusForm = new StatusForm();
		statusForm.setSuccess(true);
		logger.info("[API]" + apiHeader + "_" + APIStatusCode.END.getCode());
		return ResponseEntity.ok(statusForm);
	}

	@RequestMapping(value = "/formPostTest3", method = RequestMethod.POST, consumes = "application/json")
	public HttpEntity<?> formPostTest3(@RequestBody String json) {
		String apiHeader = CommonUtils.PRIFIX + "/formPostTest2:";

		logger.info(json);

		StatusForm statusForm = new StatusForm();
		statusForm.setSuccess(true);
		logger.info("[API]" + apiHeader + "_" + APIStatusCode.END.getCode());
		return ResponseEntity.ok(statusForm);
	}

	@ResponseBody
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public HttpEntity<?> upload(String fileName, MultipartFile jarFile) {
		String apiHeader = CommonUtils.PRIFIX + "/fileUpload:";
		// 下面是测试代码
		logger.info(fileName);
		String originalFilename = jarFile.getOriginalFilename();
		logger.info(originalFilename);
		try {
			String string = new String(jarFile.getBytes(), "UTF-8");
			System.out.println(string);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// TODO 处理文件内容...
		StatusForm statusForm = new StatusForm();
		statusForm.setSuccess(true);
		logger.info("[API]" + apiHeader + "_" + APIStatusCode.END.getCode());
		return ResponseEntity.ok(statusForm);
	}

}
