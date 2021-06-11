package it.drop.mcroni.dwt;

import it.drop.mcroni.dwt.data.ParkSlot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParkSlotManager {

  private static ParkSlotManager instance;

  // TODO come passare valore da properties
  private static int slotNumber;
  private static String slotLabel = "PS-";

  // with 2 collections it's possible to access slot to car and car to slot directly
  private HashMap<String, String> slots = new HashMap<>();
  private HashMap<String, String> cars = new HashMap<>();

  private ParkSlotManager() {
    for (int idx=1; idx<=slotNumber; idx++) {
      // this will be format number like 2 or 33 to string like 0002 or 0033
      slots.put(slotLabel + String.format("%04d", idx), null);
    }
  }

  public static ParkSlotManager init(int _slotNumber) {
    slotNumber = _slotNumber;
    return new ParkSlotManager();
  }

  public static ParkSlotManager getInstance() {
    if(instance == null) {
      instance = new ParkSlotManager();
    }
    return instance;
  }

  public List<String> getParkFreeSlotList() {
    Iterator it = slots.entrySet().iterator();
    List<String> freeSlots = new ArrayList<>();
    while (it.hasNext()) {
      Map.Entry<String, String> pair = (Map.Entry<String, String>)it.next();
      if (pair.getValue()==null)
        freeSlots.add(pair.getKey());
    }
    return freeSlots;
  }
  public List<String> getCarList() {
    List list = cars.keySet()
            .stream()
            .collect(Collectors.toList());
    return list;
  }

  public ParkSlot get(String slotId) {
    return new ParkSlot(slotId, slots.get(slotId));
  }

  public boolean isFull() {
    return cars.size()==slotNumber;
  }

  public ParkSlot addCar(String carId) throws Exception {
    if (cars.containsKey(carId))
      throw new Exception("car with id '"+carId+"' is already present");
    Iterator it = slots.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, String> pair = (Map.Entry<String, String>)it.next();
      if (pair.getValue()==null) {
        slots.put(pair.getKey(), carId);
        cars.put(carId, pair.getKey());
        return new ParkSlot(carId, pair.getKey());
      }
    }
    throw new Exception("no slot available");
  }

  public ParkSlot removeCar(String slotId) throws Exception {
    // TODO custom exc to manage different error
    if (!slots.containsKey(slotId))
      throw new Exception("slot not exists");
    String carId = slots.get(slotId);
    if (carId==null)
      throw new Exception("slot is already free");

    slots.put(slotId, null);
    cars.remove(carId);

    return new ParkSlot(slotId, carId);
  }



}
