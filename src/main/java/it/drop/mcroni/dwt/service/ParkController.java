package it.drop.mcroni.dwt.service;

import it.drop.mcroni.dwt.ParkSlotManager;
import it.drop.mcroni.dwt.data.ParkSlot;
import it.drop.mcroni.dwt.util.ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("parkservice")
public class ParkController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ParkController.class);

  @Autowired
  private ConfigProperties prop;

  @PostConstruct
  public void init() {
    LOGGER.info("init Park controller for app '"+prop.getAppName()+"'");
    ParkSlotManager.init(prop.getSlotNumber());
  }

  @RequestMapping(value = "/parks/{licensePlate}", method= RequestMethod.POST)
  public ResponseEntity parks(@PathVariable("licensePlate") String licensePlate) {
    try {
      ParkSlot resp = ParkSlotManager.getInstance().addCar(licensePlate);
      return ResponseEntity
              .status(HttpStatus.CREATED)
              .body(resp);
    } catch (Exception ex) {
      LOGGER.error("", ex);
      return ResponseEntity
              .status(HttpStatus.FORBIDDEN)
              .body(ex.getMessage());
    }
  }

  @RequestMapping(value = "/slot/{slotId}", method= RequestMethod.GET)
  public ResponseEntity slot(@PathVariable("slotId") String slotId) {
    try {
      ParkSlot resp = ParkSlotManager.getInstance().get(slotId);
      return ResponseEntity
              .status(HttpStatus.CREATED)
              .body(resp);
    } catch (Exception ex) {
      LOGGER.error("", ex);
      return ResponseEntity
              .status(HttpStatus.FORBIDDEN)
              .body(ex.getMessage());
    }
  }

  @RequestMapping(value = "/unpark/{slotId}", method= RequestMethod.PUT)
  public ResponseEntity unpark(@PathVariable("slotId") String slotId) {
    try {
      ParkSlot resp = ParkSlotManager.getInstance().removeCar(slotId);
      return ResponseEntity
              .status(HttpStatus.CREATED)
              .body(resp);
    } catch (Exception ex) {
      LOGGER.error("", ex);
      return ResponseEntity
              .status(HttpStatus.FORBIDDEN)
              .body(ex.getMessage());
    }
  }

  @RequestMapping(value = "/freeSlot", method= RequestMethod.GET)
  public List<String> slotInfo() {
    try {
      return ParkSlotManager.getInstance().getParkFreeSlotList();
    } catch (Exception ex) {
      LOGGER.error("", ex);
      // TODO error response
      return null;
    }
  }
  @RequestMapping(value = "/carInfo", method= RequestMethod.GET)
  public List<String> carInfo() {
    try {
      return ParkSlotManager.getInstance().getCarList();
    } catch (Exception ex) {
      LOGGER.error("", ex);
      // TODO error response
      return null;
    }
  }



}
