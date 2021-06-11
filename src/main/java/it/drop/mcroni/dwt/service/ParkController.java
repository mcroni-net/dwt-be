package it.drop.mcroni.dwt.service;

import it.drop.mcroni.dwt.ParkRateLimiter;
import it.drop.mcroni.dwt.ParkSlotManager;
import it.drop.mcroni.dwt.data.ParkSlot;
import it.drop.mcroni.dwt.exc.CarAlreadyPresentException;
import it.drop.mcroni.dwt.exc.NoSlotAvailableException;
import it.drop.mcroni.dwt.exc.RateLimitException;
import it.drop.mcroni.dwt.exc.SlotAlreadyFreeException;
import it.drop.mcroni.dwt.exc.SlotNotExistsException;
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

    } catch (NoSlotAvailableException ex) {
      LOGGER.error("", ex);
      return ResponseEntity
              .status(HttpStatus.CONFLICT)
              .body(ex.getMessage());
    } catch (CarAlreadyPresentException ex) {
      LOGGER.error("", ex);
      return ResponseEntity
              .status(HttpStatus.CONFLICT)
              .body(ex.getMessage());
    } catch (Exception ex) {
      LOGGER.error("", ex);
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("no more slots available");
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
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
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
    } catch (SlotNotExistsException ex) {
      LOGGER.error("", ex);
      return ResponseEntity
              .status(HttpStatus.NOT_FOUND)
              .body("slot not exists");
    } catch (SlotAlreadyFreeException ex) {
      LOGGER.error("", ex);
      return ResponseEntity
              .status(HttpStatus.PARTIAL_CONTENT)
              .body("slot already freee");
    } catch (Exception ex) {
    LOGGER.error("", ex);
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ex.getMessage());
  }
  }

  @RequestMapping(value = "/freeSlot", method= RequestMethod.GET)
  public ResponseEntity slotInfo() {
    try {
      List<String> slotInfos = ParkSlotManager.getInstance().getParkFreeSlotList();
      return ResponseEntity
              .status(HttpStatus.CREATED)
              .body(slotInfos);
    } catch (Exception ex) {
      LOGGER.error("", ex);
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(ex.getMessage());
    }
  }
  @RequestMapping(value = "/carInfo", method= RequestMethod.GET)
  public ResponseEntity carInfo() {
    try {
      List<String> carInfos = ParkSlotManager.getInstance().getCarList();
      return ResponseEntity
              .status(HttpStatus.CREATED)
              .body(carInfos);
    } catch (Exception ex) {
      LOGGER.error("", ex);
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(ex.getMessage());
    }
  }



}
