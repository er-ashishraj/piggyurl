package org.dfm.piggyurl.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", strict = true, plugin = {
        "json:target/cucumber/piggyurl.json", "json:target/cucumber/piggyurl.xml"}, tags =
        "@Piggyurl", glue = "classpath:org.dfm.piggyurl.cucumber")
public class RunCucumberPiggyurlTest {

}
