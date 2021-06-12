package it.drop.mcroni.dwt.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "dwt")
public class ConfigProperties {

  private String appName;
  private int slotNumber;
  private int rateLimitNumer;
  private int rateLimitSec;

  public int getRateLimitNumer() {
    return rateLimitNumer;
  }

  public void setRateLimitNumer(int rateLimitNumer) {
    this.rateLimitNumer = rateLimitNumer;
  }

  public int getRateLimitSec() {
    return rateLimitSec;
  }

  public void setRateLimitSec(int rateLimitSec) {
    this.rateLimitSec = rateLimitSec;
  }

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public int getSlotNumber() {
    return slotNumber;
  }

  public void setSlotNumber(int slotNumber) {
    this.slotNumber = slotNumber;
  }
}
