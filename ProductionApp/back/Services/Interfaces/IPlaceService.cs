using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.Services.Interfaces
{
    public interface IPlaceService
    {
        public List<LocationDTO> FilterLocations(string filter); 
        public List<Location> GetNextSetOfLocations(int take = Constants.Constants.TAKE_ELEMENT);
        public List<LocationDTO> GetAllAroundLocations(string location, double distance = Constants.Constants.DISTANCE);
        public List<Location> GetAllAroundLocationsByCoordinates(double longitude, double latitude, double distance = Constants.Constants.DISTANCE); 
        public List<Location> GetAllAroundLocationsByName(string locationName, double distance = Constants.Constants.DISTANCE);
    }
}
