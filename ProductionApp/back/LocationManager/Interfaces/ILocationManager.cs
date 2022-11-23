using PyxisKapriBack.Models;

namespace PyxisKapriBack.LocationManager.Interfaces
{
    public interface ILocationManager
    {
        public double GetDistance(double longitude1, double latitude1, double longitude2, double latitude2);
        public List<Location> GetAllAroundLocations(Location location, List<Location> locations, double distance = Constants.Constants.DISTANCE); 
    }
}
