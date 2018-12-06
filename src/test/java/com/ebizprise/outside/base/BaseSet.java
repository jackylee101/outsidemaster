package com.ebizprise.outside.base;

//import com.ebizprise.das.WebApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.ebizprise.das.TestApplicationConfig;

/*
 *
 * @author maduar
 * @date 31/07/2018 4:28 PM
 * @email maduar@163.com
 *
 * */
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplicationConfig.class, webEnvironment = WebEnvironment.RANDOM_PORT)
// @WebAppConfiguration
public class BaseSet {
}
