package com.ebizprise.das;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ebizprise.das.scheduled.service.weather.WeatherSync;

@Component
public class ScheduledTasks {

	// private final AtomicInteger counter = new AtomicInteger();
	private static final Logger logger = LoggerFactory
			.getLogger(ScheduledTasks.class);

	@Value("${scheduledtasks.trigtime}")
	private String trigtime;

	@Autowired
	private WeatherSync weatherSync;

	/**
	 * cron - 固定的時間或週期，週期式行為同 fixedRate 固定六個值：秒(0-59) 分(0-59) 時(0-23) 日(1-31)
	 * 月(1-12) 週(1,日-7,六) 日與週互斥，其中之一必須為 ?
	 * 可使用的值有：單一數值（26）、範圍（50-55）、清單（9,10）、不指定（*）與週期（* 3）
	 */
	@Scheduled(cron = "${scheduledtasks.trigtime}")
	public void tasks() {
		logger.warn("排程1執行中:　" + trigtime);
		weatherSync.takeWeatherDate2DB();
	}

	// @Scheduled(fixedRate = 3000, initialDelay = 1 * 1000)
	// public void report2() {
	// for (int i = 0; i < 10; i++) {
	// worker.work("reportCurrentTime2 - " + counter.incrementAndGet());
	// try {
	// Thread.sleep(300);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	//
	// e.printStackTrace();
	// }
	// }
	// }
}