package it.drop.mcroni.dwt;

import it.drop.mcroni.dwt.exc.RateLimitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;

public class ParkRateLimiter {

  private static final Logger LOGGER = LoggerFactory.getLogger(ParkRateLimiter.class);

  private static ParkRateLimiter instance;

  private ArrayList<Long> requests = new ArrayList<>();

  private static int rateLimitNumber;
  private static int rateLimitSecond;

  private ParkRateLimiter() {
  }

  public static ParkRateLimiter init(int _rateLimitNumber, int _rateLimitSecond) {
    rateLimitNumber = _rateLimitNumber;
    rateLimitSecond = _rateLimitSecond;
    return new ParkRateLimiter();
  }

  public static ParkRateLimiter getInstance() {
    if(instance == null) {
      instance = new ParkRateLimiter();
    }
    return instance;
  }

  public void checkRateLimit() throws RateLimitException {
    long currentTime = System.currentTimeMillis();
    long limitTime = currentTime - (rateLimitSecond*1000);
    requests.add(currentTime);
    // check how request are in time rate interval, if more than limitNumber throw exc
    int nReq=0;

    Iterator<Long> i = requests.iterator();
    while (i.hasNext()) {
      Long requestTime = i.next();
      if (requestTime<limitTime) {
        i.remove();
        continue;
      }
      nReq++;
      if (nReq>rateLimitNumber)
        throw new RateLimitException("too many ("+nReq+") request in past '"+rateLimitSecond+"' sec");

    }

  }



}
