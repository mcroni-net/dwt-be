package it.drop.mcroni.dwt.exc;

public class RateLimitException extends Throwable{
  public RateLimitException(String msg) {
    super(msg);
  }
}
