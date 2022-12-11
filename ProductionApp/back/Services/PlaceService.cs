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
                locationsDTO.Add(new LocationDTO
                {
                    Name = location
                });
            }

            return locationsDTO;
        }

        public List<LocationDTO> GetAllAroundLocationsByName(string location, double distance = Constants.Constants.DISTANCE)
        {
            Location _location = _iLocationDAL.GetLocation(location);

            if (location == null)
                return null;
            if ((_location.Longitude == 0) || (_location.Latitude == 0))
                return null; 

            return createLocationDTOList(_iLocationDAL.GetAllAroundLocations(_location.Latitude, _location.Longitude, distance)); 
        }

        public List<LocationDTO> GetAllAroundLocationsByCoordinates(double longitude, double latitude, double distance = 1500)
        {
            var locations = _iLocationDAL.GetAllAroundLocations(latitude, longitude, distance);

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
