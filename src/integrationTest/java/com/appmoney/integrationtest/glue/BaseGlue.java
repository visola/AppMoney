package com.appmoney.integrationtest.glue;

import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;

import com.appmoney.integrationtest.IntegrationTestConfiguration;

@ContextConfiguration(classes={IntegrationTestConfiguration.class}, initializers = ConfigFileApplicationContextInitializer.class)
public class BaseGlue {

}
