using PyxisKapriBack.Models;
using System.Device.Location;

namespace PyxisKapriBack.LocationManager.Interfaces
{
    public interface ILocationManager
    {
        public double GetDistance(double longitude1, double latitude1, double longitude2, double latitude2);
        public List<Location> GetAllAroundLocations(GeoCoordinate coordinate, List<Post> posts, double distance = Constants.Constants.DISTANCE); 
    }
}
