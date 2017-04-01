package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SpringBootApplication
@MapperScan("com.example.dao")
public class DemoApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(DemoApplication.class, args);
//	}

	public static void main(String[] args) {


		Long start2 = getNowTimeLong();
		List arrayList = new ArrayList();
//		for (int i = 0;i < 10000000;i++) {
//			arrayList.add(new Integer(2));
//		}
		List arrayListForSize = new ArrayList(10000000);
		for (int i = 0;i < 10000000;i++) {
			arrayListForSize.add(new Integer(1));
		}
		Long end2 = getNowTimeLong();
		System.out.println(end2 - start2);

//		Long start = getNowTimeLong();
//		List arrayListForSize = new ArrayList(10000000);
//		for (int i = 0;i < 10000000;i++) {
//			arrayListForSize.add(new Integer(1));
//		}
//		Long end = getNowTimeLong();
//		System.out.println(end - start);
	}
	private static Long getNowTimeLong() {
		return System.currentTimeMillis();
	}
}
