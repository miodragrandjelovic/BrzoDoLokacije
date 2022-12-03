using PyxisKapriBack.DTOComponents;

namespace PyxisKapriBack.UI.Interfaces
{
    public interface IPlaceUI
    {
        public List<LocationDTO> GetAllAroundLocations(SearchDTO search);
        public List<LocationDTO> FilterLocations(String location);
        public List<LocationDTO> GetNextSetOfLocations(int amountOfLocations); 
    }
}
