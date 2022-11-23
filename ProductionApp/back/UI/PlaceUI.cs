using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.UI.Interfaces;

namespace PyxisKapriBack.UI
{
    public class PlaceUI : IPlaceUI
    {
        private readonly IPlaceService iPlaceService; 

        public PlaceUI(IPlaceService iPlaceService)
        {
            this.iPlaceService = iPlaceService;
        }

        public List<LocationDTO> GetAllAroundLocations(SearchDTO search, SearchType searchType)
        {
            List <LocationDTO> locationsDTO = null;  
            List<Location> locations = null;
            switch (searchType)
            {
                case SearchType.LOCATION:
                    locations = iPlaceService.GetAllAroundLocationsByName(search.Name, search.Distance);
                    break;
                case SearchType.COORDINATES: 
                    locations = iPlaceService.GetAllAroundLocationsByCoordinates(search.Longitude, search.Latitude, search.Distance);
                    break;
                case SearchType.CITY:
                    locations = null;
                    break;
                case SearchType.COUNTRY:
                    locations = null;
                    break;
                default:
                    locations = null;
                    break; 
            }

            return locationsDTO; 
        }
    }
}
