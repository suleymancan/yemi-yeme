package com.suleymancanblog.yemiyeme.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created on April, 2019
 *
 * @author suleymancan
 */
@Component
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class PropertyConfig {

	private String name;

	private String master;

	private String modelPath;
}
