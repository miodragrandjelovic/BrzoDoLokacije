using PyxisKapriBack.DTOComponents;

namespace PyxisKapriBack.UI.Interfaces
{
    public interface IPlaceUI
    {
        public List<LocationDTO> GetAllAroundLocations(SearchDTO search); 
    }
}
