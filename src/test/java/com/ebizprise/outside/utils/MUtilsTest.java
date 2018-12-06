package com.ebizprise.outside.utils;

//import com.ebizprise.das.utilsweb.common.MUtils;
//import com.ebizprise.das.utilsweb.form.date.FirstDateAndLastDateByMonth;
import org.junit.Assert;
import org.junit.Test;

/*
 *
 * @author maduar
 * @date 06/08/2018 3:28 PM
 * @email maduar@163.com
 *
 * */
public class MUtilsTest {

     String defaultDate = "201709";

    final String defaultDate1 = "20170901";
    final String defaultDate2 = "10170901";

    final String defaultDate3 = "20171190";
    final String defaultDate4 = "20172910";
    final String defaultDate5 = "20170940";

    final String defaultDate6 = null;
    final String defaultDate7 = "";


    @Test
    public void testIsDateYYYYMMDD() {
//        Assert.assertTrue(MUtils.isDateYYYYMMDD(defaultDate1));
//        Assert.assertTrue(MUtils.isDateYYYYMMDD(defaultDate2));
//
//        Assert.assertFalse(MUtils.isDateYYYYMMDD(defaultDate3));
//        Assert.assertFalse(MUtils.isDateYYYYMMDD(defaultDate4));
//        Assert.assertFalse(MUtils.isDateYYYYMMDD(defaultDate5));
//
//        Assert.assertFalse(MUtils.isDateYYYYMMDD(defaultDate6));
//        Assert.assertFalse(MUtils.isDateYYYYMMDD(defaultDate7));
    }

    @Test
    public void testGetFirstDateAndLastDateByMonth() {
//        FirstDateAndLastDateByMonth firstDateAndLastDateByMonth = MUtils.getFirstDateAndLastDateByMonth(defaultDate);
        String defaultFirstDate = "20170901";
        String defaultLastDate = "20170930";

//        Assert.assertEquals(defaultFirstDate, firstDateAndLastDateByMonth.getFirstDate());
//        Assert.assertEquals(defaultLastDate, firstDateAndLastDateByMonth.getLastDate());

    }


    @Test
    public void testStringChange() {
        String num = "27.000";

        num = num.split("\\.")[0];
        String result = "27";
        System.out.println(num);
        Assert.assertEquals(num, result);
    }
}
