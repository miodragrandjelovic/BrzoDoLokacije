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
                locationsDTO.Add(new LocationDTO { Id = location.Id, Name = location.Name });
            }

            return locationsDTO;
        }

        public List<Location> GetNextSetOfLocations(int take = Constants.Constants.TAKE_ELEMENT)
        {
            return _iLocationDAL.GetNextSetOfLocations(take);
        }
    }
}
