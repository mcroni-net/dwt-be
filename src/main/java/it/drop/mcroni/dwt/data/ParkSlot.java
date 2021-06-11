package it.drop.mcroni.dwt.data;


public class ParkSlot {

  private String parkSlotId;
  private String licensePlateId;

  public ParkSlot(String _parkSlotId) {
    this.parkSlotId = _parkSlotId;
  }
  public ParkSlot(String _parkSlotId, String _licensePlateId) {
    this.parkSlotId = _parkSlotId;
    this.licensePlateId = _licensePlateId;
  }

  public String getParkSlotId() {
    return parkSlotId;
  }

  public void setParkSlotId(String parkSlotId) {
    this.parkSlotId = parkSlotId;
  }

  public String getLicensePlateId() {
    return licensePlateId;
  }

  public void setLicensePlateId(String licensePlateId) {
    this.licensePlateId = licensePlateId;
  }

}
