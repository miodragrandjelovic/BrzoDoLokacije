using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.Services.Interfaces
{
    public interface IPlaceService
    {
        public List<LocationDTO> FilterLocations(string filter); 
        public List<Location> GetNextSetOfLocations(int take = Constants.Constants.TAKE_ELEMENT);
    }
}
