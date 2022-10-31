using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface ILocationDAL
    {
        public Boolean AddLocation(string locationName, string cityName, string countryName = Constants.Constants.UNKNWOWN);
        public Boolean UpdateLocation(Location location);
        public Boolean DeleteLocation(string locationName);
        public Location GetLocation(string locationName);
        public List<Location> FilterLocations(string filter);
        public List<Location> FilterLocationsByCity(string filter);
        public List<Location> FIlterLocationsByCountry(string filter); 
    }
}
