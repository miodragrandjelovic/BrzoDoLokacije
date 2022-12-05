using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface ILocationDAL
    {
        public bool AddLocation(string locationName, string cityName, string countryName = Constants.Constants.UNKNWOWN);
        public bool AddLocation(Location location); 
        public bool UpdateLocation(Location location);
        public bool DeleteLocation(string locationName);
        public Location GetLocation(string locationName);
        public Location GetLocation(int locationID);
        //public List<Location> FilterLocations(string filter);
        public List<String> FilterLocations(string filter); 
        public List<Location> FilterLocationsByCity(string filter);
        public List<Location> FilterLocationsByCountry(string filter);
        public List<Location> FilterLocationsByName(string filter); 
        public List<Location> GetNextSetOfLocations(int take = Constants.Constants.TAKE_ELEMENT);
        public List<Location> GetAllAroundLocations(Location location, double distance = Constants.Constants.DISTANCE); 
    }
}
