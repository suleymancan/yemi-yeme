package com.suleymancanblog.yemiyeme.config;

import lombok.AllArgsConstructor;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created on April, 2019
 *
 * @author suleymancan
 */
@Configuration
@AllArgsConstructor
public class SparkConfig {

	private final PropertyConfig propertyConfig;


	@Bean
	public JavaSparkContext javaSparkContext(){
		final SparkConf sparkConf = new SparkConf().setAppName(propertyConfig.getName()).setMaster(propertyConfig.getMaster());
		return new JavaSparkContext(sparkConf);
	}

	@Bean
	public DecisionTreeModel decisionTreeModel(JavaSparkContext javaSparkContext){
		return DecisionTreeModel.load(javaSparkContext.sc(), propertyConfig.getModelPath());
	}

}
