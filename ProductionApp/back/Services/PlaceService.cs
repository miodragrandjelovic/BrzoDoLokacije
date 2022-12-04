using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
using PyxisKapriBack.Services.Interfaces;

namespace PyxisKapriBack.Services
{
    public class PlaceService : IPlaceService
    {
        private ILocationDAL _iLocationDAL; 

        public PlaceService(ILocationDAL iLocationDAL)
        {
            _iLocationDAL = iLocationDAL;
        }

        public List<LocationDTO> FilterLocations(string filter)
        {
            var locationsDTO = new List<LocationDTO>();
            var locations = _iLocationDAL.FilterLocations(filter);

            foreach (var location  in locations)
            {
                locationsDTO.Add(new LocationDTO { 
                    Id = location.Id, 
                    Name = location.Name });
            }

            return locationsDTO;
        }

        public List<LocationDTO> GetAllAroundLocationsByName(string location, double distance = Constants.Constants.DISTANCE)
        {
            var _location = _iLocationDAL.GetLocation(location);

            if (location == null)
                return null; 

            return createLocationDTOList(_iLocationDAL.GetAllAroundLocations(_location, distance)); 
        }

        public List<LocationDTO> GetAllAroundLocationsByCoordinates(double longitude, double latitude, double distance = 1500)
        {
            var location = new Location()
            {
                Longitude = longitude,
                Latitude = latitude
            }; 
            var locations = _iLocationDAL.GetAllAroundLocations(location, distance);

            var locationsDTO = new List<LocationDTO>();
            foreach (Location loc in locations)
            {
                locationsDTO.Add(new LocationDTO
                {
                    Id = loc.Id,
                    Name = loc.Name,
                    Distance = loc.Distance
                });
            }

            return locationsDTO; 
        }
        public List<Location> GetNextSetOfLocations(int take = Constants.Constants.TAKE_ELEMENT)
        {
            return _iLocationDAL.GetNextSetOfLocations(take);
        }

        public List<LocationDTO> createLocationDTOList(List<Location> locations)
        {
            var locationsDTO = new List<LocationDTO>();
            foreach (Location loc in locations)
            {
                locationsDTO.Add(new LocationDTO
                {
                    Id = loc.Id,
                    Name = loc.Name,
                    Distance = loc.Distance
                });
            }
            return locationsDTO; 
        }
    }
}
