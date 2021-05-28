package com.shadowlandsmc.javase.trees;

import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class Tests {

	
	@Test
	public void testRandom() {
		double test = 0;
		for(int i = 0; i < 5; i++) {
	      Random rand = new Random(); //instance of random class
	      double double_random=rand.nextDouble() * 100;
	      System.out.println(double_random);
	      test = double_random;
		}
		assertNotEquals(test, 0);
	}
	
	
	//test to make sure our values evenly distributed.
	@Test
	public void testRandomDistro() {
		int interations = 1000;
		List<Double> list = new ArrayList<Double>();
		for(int i = 0; i < interations; i++) {
	      Random rand = new Random(); //instance of random class
	      double double_random=rand.nextDouble() * 100;
	      list.add(double_random);
		}
		
		List<Double> one2ten = new ArrayList<Double>();
		List<Double> ten2twenty = new ArrayList<Double>();
		List<Double> twenty2thirty = new ArrayList<Double>();
		List<Double> thirty2fourty = new ArrayList<Double>();
		List<Double> fourty2fifty = new ArrayList<Double>();
		List<Double> fifty2sixty = new ArrayList<Double>();
		List<Double> sixty2seventy = new ArrayList<Double>();
		List<Double> seventy2eighty = new ArrayList<Double>();
		List<Double> eighty2ninty = new ArrayList<Double>();
		List<Double> ninty2hundred = new ArrayList<Double>();
		for(int i = 0; i < list.size(); i++) {
			double working = list.get(i);
			if(working < 10) {
			one2ten.add(working);
			}else
				if(working < 20) {
					ten2twenty.add(working);
				}else
					if(working < 30) {
						twenty2thirty.add(working);
						}else
							if(working < 40) {
								thirty2fourty.add(working);
								}else
									if(working < 50) {
										fourty2fifty.add(working);
										}else
											if(working < 60) {
												fifty2sixty.add(working);
												}else
													if(working < 70) {
														sixty2seventy.add(working);
														}else
															if(working < 80) {
																seventy2eighty.add(working);
																}else
																	if(working < 90) {
																		eighty2ninty.add(working);
																		}else
																			if(working < 100) {
																				ninty2hundred.add(working);
																				}
			
		}
		
		System.out.println("0-10: " + one2ten.size());
		System.out.println("10-20: " + ten2twenty.size());
		System.out.println("20-30: " + twenty2thirty.size());
		System.out.println("30-40: " + thirty2fourty.size());
		System.out.println("40-50: " + fourty2fifty.size());
		System.out.println("50-60: " + fifty2sixty.size());
		System.out.println("60-70: " + sixty2seventy.size());
		System.out.println("70-80: " + seventy2eighty.size());
		System.out.println("80-90: " + eighty2ninty.size());
		System.out.println("90-100: " + ninty2hundred.size());
		
		assertNotEquals(list.size(), 0);
	}
	
}
